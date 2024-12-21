package com.example.btnhom7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addsp.ApiService.ManagerUserServiceApi;
import com.example.addsp.Model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhoneNumber, edtPassword;
    private TextView txtCreatedAt;
    private ImageView imgAvatar;
    private Button btnSaveUser;
    String token;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Ánh xạ view
        edtFullName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhoneNumber = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnSaveUser = findViewById(R.id.btn_save);
        token = "Bearer " + getTokenFromPrefs();
        Log.e("token",token);
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (UserModel) intent.getSerializableExtra("user");
            populateUserDetails();
        }

        // Xử lý sự kiện lưu
        btnSaveUser.setOnClickListener(v -> saveUser());
    }

    // Hiển thị thông tin người dùng
    private void populateUserDetails() {
        if (user != null) {
            edtFullName.setText(user.getName());
            edtEmail.setText(user.getEmail());
            edtPhoneNumber.setText(user.getPhoneNumber());
            edtPassword.setText(user.getPassword());
            txtCreatedAt.setText("Created At: " + user.getCreatedAt());


        }
    }

    // Lưu thông tin người dùng
    private void saveUser() {
        if (user != null) {
            // Kiểm tra đầu vào
            if (edtFullName.getText().toString().trim().isEmpty() ||
                    edtEmail.getText().toString().trim().isEmpty() ||
                    edtPhoneNumber.getText().toString().trim().isEmpty() ||
                    edtPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật thông tin người dùng
            user.setName(edtFullName.getText().toString());
            user.setEmail(edtEmail.getText().toString());
            user.setPhoneNumber(edtPhoneNumber.getText().toString());
            user.setPassword(edtPassword.getText().toString());
            updateUser(user);
            // Gửi lại kết quả
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedUser", user);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser(UserModel user){
        ManagerUserServiceApi.ManagerUserServiceApi.updateUser(token,user).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    Toast.makeText(EditUserActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditUserActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
            }
        });
    }

    private String getTokenFromPrefs() {
        return getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
                .getString("token", "");
    }
}

package com.example.btnhom7;

//import static android.app.Activity.RESULT_OK;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addsp.Adapter.UserAdapter;
import com.example.addsp.ApiResponse.DeleteResponse;
import com.example.addsp.ApiService.AuthServiceApi;
import com.example.addsp.ApiService.ManagerUserServiceApi;
import com.example.addsp.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Main_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Main_Fragment extends Fragment implements UserAdapter.OnUserActionListener  {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<UserModel> userList;
    private int selectedPosition = -1; // Vị trí đã chọn để chỉnh sửa
    private ActivityResultLauncher<Intent> editUserLauncher;
    String token;
    Button btn_logout_user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Main_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Main_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Main_Fragment newInstance(String param1, String param2) {
        User_Main_Fragment fragment = new User_Main_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_user__main_, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        token = "Bearer " + getTokenFromPrefs();
        btn_logout_user = view.findViewById(R.id.btn_logout_user);
        userList = new ArrayList<>();
        btn_logout_user.setOnClickListener(v->{
            SharedPreferences sharedPreferences =requireContext().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("token");
            editor.apply();

            Intent intent = new Intent(requireActivity(), SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
        adapter = new UserAdapter(requireContext(), userList, this);
        recyclerView.setAdapter(adapter);

        // Nút thêm người dùng
        view.findViewById(R.id.btn_add_user).setOnClickListener(v -> showEditDialog(true, -1));


        getUser(token);
        // Đăng ký ActivityResultLauncher
        editUserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            UserModel updatedUser = (UserModel) data.getSerializableExtra("updatedUser");
                            if (updatedUser != null) {
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).getUserId() == updatedUser.getUserId()) {
                                        userList.set(i, updatedUser);
                                        adapter.notifyItemChanged(i);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
        );
        return view;
    }

    private void showEditDialog(boolean isAdding, int position) {
        View dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.activity_edit_user, null);

        // Khai báo các trường thông tin trong dialog
        EditText edtName = dialogView.findViewById(R.id.edt_name);
        EditText edtEmail = dialogView.findViewById(R.id.edt_email);
        EditText edtPhone = dialogView.findViewById(R.id.edt_phone);
        EditText edtPassword = dialogView.findViewById(R.id.edt_password);
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        // Nếu là chỉnh sửa, đổ dữ liệu người dùng vào các trường
        if (!isAdding && position >= 0) {
            UserModel user = userList.get(position);
            edtName.setText(user.getName());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhoneNumber());
            edtPassword.setText(user.getPassword());
        }

        // Hiển thị dialog
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setCancelable(true)
                .create();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isAdding) {
                // Thêm người dùng mới
//                Long newUserId = userList.size() + 1; // Tạo ID mới tự động
//                String createdAt = "2024-11-24"; // Hoặc lấy ngày hiện tại
//                userList.add(new UserModel(
//                        newUserId,
//                        name,
//                        email,
//                        phone,
//                        password,
//                        R.drawable.profile_icon, // Avatar mặc định
//                        createdAt
//                ));
                UserModel userModel = new UserModel();
                userModel.setEmail(email);
                userModel.setName(name);
                userModel.setPassword(password);
                userModel.setPhoneNumber(phone);
                insertUser(userModel);




            } else {
                // Cập nhật thông tin người dùng hiện có
                UserModel user = userList.get(position);
                user.setName(name);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setPassword(password);
                adapter.notifyItemChanged(position);
                Toast.makeText(requireActivity(), "Đã chỉnh sửa: " + user.getName(), Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(requireActivity(), EditUserActivity.class);
        UserModel usserModel = userList.get(position);
        usserModel.setPassword(userList.get(position).getPassword().substring(0,7));
        intent.putExtra("user", usserModel);
        editUserLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int position) {

        deleteUser(userList.get(position), position);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            UserModel updatedUser = (UserModel) data.getSerializableExtra("updatedUser");
            if (updatedUser != null) {
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserId() == updatedUser.getUserId()) {
                        userList.set(i, updatedUser);
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }

    private String getTokenFromPrefs() {
        return requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
                .getString("token", "");
    }
    private void getUser(String token){
        ManagerUserServiceApi.ManagerUserServiceApi.getUser(token).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    for(UserModel user : response.body()){
                        userList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Log.e("API Error", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
            }
        });
    }

    private void insertUser(UserModel registerRequest){
        AuthServiceApi.authServiceApi.register(registerRequest, token).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    UserModel newUser = response.body();
                    userList.add(newUser);
                    adapter.notifyItemInserted(userList.size() - 1);
                    Toast.makeText(requireActivity(), "Đã thêm người dùng mới!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireActivity(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
            }
        });
    }
    private void deleteUser(UserModel userModel, int position){
        ManagerUserServiceApi.ManagerUserServiceApi.deleteUser(token,userModel.getUserId()).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if(response.isSuccessful()){
                    userList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(requireActivity(), "Đã xóa người dùng!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
            }
        });
    }


}
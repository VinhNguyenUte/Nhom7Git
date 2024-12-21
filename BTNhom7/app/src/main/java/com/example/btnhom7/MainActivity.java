package com.example.btnhom7;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

//import com.example.addsp.adapters.ViewPagerAdapter;

import com.example.btnhom7.Adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ViewPager và BottomNavigationView
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Tạo adapter cho ViewPager
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // Đặt sự kiện khi người dùng chọn mục trong BottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_user)
                    viewPager.setCurrentItem(0);
                else {
                    viewPager.setCurrentItem(1);
                }


                return true;
            }
        });

        // Đồng bộ hóa với ViewPager khi người dùng chuyển tab
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);

                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_carpost).setChecked(true);

                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Không cần xử lý gì trong trường hợp này
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Không cần xử lý gì trong trường hợp này
            }
        });

        // Mặc định khi vào ứng dụng chọn mục đầu tiên
        bottomNavigationView.setSelectedItemId(R.id.menu_user);
    }
}

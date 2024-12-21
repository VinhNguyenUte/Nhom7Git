package com.example.btnhom7.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btnhom7.User_Main_Fragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new User_Main_Fragment(); // Fragment quản lý người dùng
            case 1:
                return new CarPostApproval_Fragment(); // Fragment duyệt bài đăng bán xe
            default:
                return new User_Main_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2; // Chúng ta chỉ có 2 fragment, quản lý người dùng và duyệt bán xe
    }
}

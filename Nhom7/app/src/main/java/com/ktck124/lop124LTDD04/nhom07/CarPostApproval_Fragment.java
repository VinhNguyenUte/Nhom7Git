package com.ktck124.lop124LTDD04.nhom07;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CarPostApproval_Fragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout chứa TabLayout và ViewPager2
        return inflater.inflate(R.layout.fragment_ds_sanpham, container, false);
    }

}

package com.example.eclothes.ui.setting.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.eclothes.R;
import com.example.eclothes.ui.contactus.ContactusViewModel;

public class SettingAdminFragment extends Fragment {

    private SettingAdminViewModel settingAdminViewModel;

    public View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        settingAdminViewModel = new ViewModelProvider(this).get(SettingAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting_admin, container, false);
        final TextView textView = root.findViewById(R.id.text_setting_admin);
        settingAdminViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
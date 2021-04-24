package com.example.eclothes.ui.setting.user;

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

public class SettingUserFragment extends Fragment {

    private SettingUserViewModel settingUserViewModel;

    public View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        settingUserViewModel = new ViewModelProvider(this).get(SettingUserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting_user, container, false);
        final TextView textView = root.findViewById(R.id.text_setting_user);
        settingUserViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}


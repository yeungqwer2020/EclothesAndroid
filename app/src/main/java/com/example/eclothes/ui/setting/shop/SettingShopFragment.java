package com.example.eclothes.ui.setting.shop;
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

public class SettingShopFragment extends Fragment {

    private SettingShopViewModel settingShopViewModel;

    public View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        settingShopViewModel = new ViewModelProvider(this).get(SettingShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting_shop, container, false);
        final TextView textView = root.findViewById(R.id.text_setting_shop);
        settingShopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}

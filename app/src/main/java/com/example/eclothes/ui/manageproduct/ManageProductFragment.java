package com.example.eclothes.ui.manageproduct;

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

public class ManageProductFragment extends Fragment {

    private ManageProductViewModel manageProductViewModel;

    public View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        manageProductViewModel = new ViewModelProvider(this).get(ManageProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manageproduct, container, false);
        final TextView textView = root.findViewById(R.id.text_manageproduct);
        manageProductViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
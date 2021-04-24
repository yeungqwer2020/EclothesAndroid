package com.example.eclothes.ui.contactus;

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

public class ContactusFragment extends Fragment {

    private ContactusViewModel contactusViewModel;

    public View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        contactusViewModel = new ViewModelProvider(this).get(ContactusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        final TextView textView = root.findViewById(R.id.text_contactus);
        contactusViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}

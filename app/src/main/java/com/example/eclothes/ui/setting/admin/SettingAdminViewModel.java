package com.example.eclothes.ui.setting.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingAdminViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public SettingAdminViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is admin setting Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}
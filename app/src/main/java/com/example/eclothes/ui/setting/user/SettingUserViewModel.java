package com.example.eclothes.ui.setting.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingUserViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public SettingUserViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is user setting Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}
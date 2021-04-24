package com.example.eclothes.ui.setting.shop;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingShopViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public SettingShopViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is merchant setting Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}
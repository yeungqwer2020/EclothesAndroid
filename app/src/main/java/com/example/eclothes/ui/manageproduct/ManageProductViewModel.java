package com.example.eclothes.ui.manageproduct;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageProductViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public ManageProductViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is Manage Product Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}
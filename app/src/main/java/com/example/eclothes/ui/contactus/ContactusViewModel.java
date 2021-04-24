package com.example.eclothes.ui.contactus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactusViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public ContactusViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is Contact US Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}

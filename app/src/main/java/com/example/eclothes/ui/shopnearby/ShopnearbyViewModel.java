package com.example.eclothes.ui.shopnearby;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopnearbyViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public ShopnearbyViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is ShopNearBy Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}

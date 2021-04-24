package com.example.eclothes.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public FavoriteViewModel(){
        text = new MutableLiveData<>();
        text.setValue("This is Favorite Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}
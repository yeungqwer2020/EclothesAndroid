package com.example.eclothes.ui.recommendation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecommendationViewModel extends ViewModel {
    private static final String TAG = "RecommendationViewModel";
    private MutableLiveData<String> text;

    public RecommendationViewModel(){
        Log.d(TAG, "RecommendationViewModel: construct");
        text = new MutableLiveData<>();
        text.setValue("This is Recommendation Fragment");
    }

    public LiveData<String> getText(){
        return text;
    }

}

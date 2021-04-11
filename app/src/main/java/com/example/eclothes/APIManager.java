package com.example.eclothes;

import com.example.eclothes.API.APIService;
import com.example.eclothes.Config.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static com.example.eclothes.APIManager apiManager;
    private Retrofit retrofit;
    private APIService apiService;

    private APIManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static com.example.eclothes.APIManager getInstance() {
        if (apiManager == null) {
            apiManager = new com.example.eclothes.APIManager();
        }
        return apiManager;
    }

    public APIService getAPIService() {
        return apiService;
    }
}

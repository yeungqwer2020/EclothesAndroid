package com.example.eclothes;

import com.example.eclothes.API.APIService;
import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Config.Constant;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static APIManager apiManager;
    private OkHttpClient client;
    private Retrofit retrofit;
    private APIService apiService;

    private APIManager() {
        client = new OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor.getInstance()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static APIManager getInstance() {
        if (apiManager == null) {
            apiManager = new APIManager();
        }
        return apiManager;
    }

    public APIService getAPIService() {
        return apiService;
    }
}

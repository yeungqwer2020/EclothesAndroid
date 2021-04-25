package com.example.eclothes.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private static String token;
    private static AuthorizationInterceptor authorizationInterceptor;

    public AuthorizationInterceptor() {

    }

    public static void setToken(String t) {
        token = t;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.header("No-Authorization") == null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }

        return chain.proceed(request);
    }

    public static AuthorizationInterceptor getInstance() {
        if (authorizationInterceptor == null) {
            authorizationInterceptor = new AuthorizationInterceptor();
        }
        return authorizationInterceptor;
    }
}

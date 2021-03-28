package com.example.nhl.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://89.223.26.208:2222";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONUserApi getJSONApi(){
        return mRetrofit.create(JSONUserApi.class);
    }

    public JSONScoreApi getJSONScoreApi(){
        return  mRetrofit.create(JSONScoreApi.class);
    }
}
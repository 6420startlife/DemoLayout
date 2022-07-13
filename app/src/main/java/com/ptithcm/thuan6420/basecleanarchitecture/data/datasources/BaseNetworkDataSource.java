package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.BaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseNetworkDataSource {
    private Gson gson;
    public static Retrofit retrofit;
    public static BaseApi api;

    public BaseNetworkDataSource(){
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(BaseApi.class);
    }

    public static BaseApi GetApi() {
        return api;
    }
}

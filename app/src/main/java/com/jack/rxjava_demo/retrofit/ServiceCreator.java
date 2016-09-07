package com.jack.rxjava_demo.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by jack on 2016/7/16.
 */

public class ServiceCreator {

    /**
     * 1. "https://api.github.com"
     * 2.
     */
    private static final String BASE_URL_GITHUB = "https://api.github.com";
    private static final String BASE_URL_XIAOHEIBAN = "http://pingjia.xiaoheiban.cn";

    public static GithubApi createGithubService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_GITHUB);

        return builder.build().create(GithubApi.class);
    }

    public static BlackBoardApi createBlackBoardService(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_XIAOHEIBAN);

        return  builder.build().create(BlackBoardApi.class);
    }
}

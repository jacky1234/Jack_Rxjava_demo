package com.jack.rxjava_demo.retrofit;


import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.plugins.RxJavaErrorHandler;

/**
 * Created by jack on 2016/7/16.
 */

public class GithubService {

    /**
     * 1. "https://api.github.com"
     * 2.
     */
    private static final String BASE_URL = "http://pingjia.xiaoheiban.cn";

    public static GithubApi createGithubService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

        return builder.build().create(GithubApi.class);
    }
}

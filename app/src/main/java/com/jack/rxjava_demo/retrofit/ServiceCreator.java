package com.jack.rxjava_demo.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jack.rxjava_demo.retrofit.base.IApiMark;
import com.jack.rxjava_demo.tools.ContentUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by jack on 2016/7/16.
 */

public class ServiceCreator {
    public HashMap<Class<?>, Object> caches = new HashMap<>();
    private static ServiceCreator mInstance;
    private static String[] illegal = {"GithubApi","BlackBoardApi"};

    /**
     * 1. "https://api.github.com"
     * 2.
     */
    private static final String BASE_URL_GITHUB = "https://api.github.com";
    private static final String BASE_URL_XIAOHEIBAN = "http://pingjia.xiaoheiban.cn";

    private ServiceCreator() {
    }

    public static ServiceCreator getInstance() {
        if (mInstance == null) {
            synchronized (ServiceCreator.class) {
                if (mInstance == null) {
                    mInstance = new ServiceCreator();
                }
            }
        }
        return mInstance;
    }

    public <T> T getService(Class<T> clz) {
        //检查参数是否合法
        if (!ContentUtils.isInclude(illegal,clz.getSimpleName())){
            throw new IllegalArgumentException("参数不合法："+clz.getSimpleName()+"does not include in array of illegal!");
        }

         Object o = caches.get(clz);
        if (o == null) {
            if (clz == GithubApi.class) {
                caches.put(clz, createGitHubApi());
            } else if (clz == BlackBoardApi.class) {
                caches.put(clz, createBlackBoardService());
            }

            o = caches.get(clz);
        }


        return (T) o;
    }

    private GithubApi createGitHubApi() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_GITHUB);

        return builder.build().create(GithubApi.class);
    }

    private BlackBoardApi createBlackBoardService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_XIAOHEIBAN);

        return builder.build().create(BlackBoardApi.class);
    }
}

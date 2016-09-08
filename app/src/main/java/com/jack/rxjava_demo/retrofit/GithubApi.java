package com.jack.rxjava_demo.retrofit;

import com.jack.rxjava_demo.bean.Constructor;
import com.jack.rxjava_demo.retrofit.base.IApiMark;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jack on 2016/7/16.
 */

public interface GithubApi {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Constructor>> getGroupList(
            @Path("owner") String owner,
            @Path("repo") String repo);

    //如果你想要使用 RxJava 来代替 call, 你需要一个 Call Adapter Factory
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Constructor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);



}

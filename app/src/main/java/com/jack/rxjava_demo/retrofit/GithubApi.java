package com.jack.rxjava_demo.retrofit;

import com.jack.rxjava_demo.bean.BaseEntity;
import com.jack.rxjava_demo.bean.Constructor;
import com.jack.rxjava_demo.bean.School;

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
    Call<List<Constructor>> getGroupList(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Constructor>> contributorsRx(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/api-index-schoolList.html")
    Call<BaseEntity<List<School>>> getStudentList();
}

package com.jack.rxjava_demo.retrofit;

import com.jack.rxjava_demo.bean.BaseEntity;
import com.jack.rxjava_demo.bean.School;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jacky on 2016/9/7.
 */

public interface BlackBoardApi {

    @GET("/api-index-schoolList")
    Call<BaseEntity<List<School>>> getSchoolInfo();
}

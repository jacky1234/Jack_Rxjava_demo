package com.jack.rxjava_demo.retrofit;

import com.jack.rxjava_demo.bean.BaseEntity;
import com.jack.rxjava_demo.bean.School;
import com.jack.rxjava_demo.retrofit.base.IApiMark;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jacky on 2016/9/7.
 */

public interface BlackBoardApi {

    @GET("/api-index-schoolList")
    Call<BaseEntity<List<School>>> getSchoolInfo();

    //返回的是请求的 json 字符串
    @GET("/api-index-schoolList")
    Call<ResponseBody> getSchoolJson();
}

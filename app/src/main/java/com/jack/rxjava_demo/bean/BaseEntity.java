package com.jack.rxjava_demo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jacky on 2016/9/6.
 */

public class BaseEntity<E> implements Serializable {
    @SerializedName("code")
    private int code;

    @SerializedName("info")
    private E data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}

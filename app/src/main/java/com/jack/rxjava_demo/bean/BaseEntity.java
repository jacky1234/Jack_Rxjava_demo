package com.jack.rxjava_demo.bean;

import java.io.Serializable;

/**
 * Created by Jacky on 2016/9/6.
 */

public class BaseEntity<E> implements Serializable {
    private int code;
    private String info;
    private E data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}

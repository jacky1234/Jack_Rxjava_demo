package com.jack.rxjava_demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: jack
 * Created by: ModelGenerator on 2016/7/16
 */
public class Constructor {
    public String login;
    public int id;
    public String avatar_url;
    public int contributions;

    @Override
    public String toString() {
        return "Constructor{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", contributions=" + contributions +
                '}';
    }
}
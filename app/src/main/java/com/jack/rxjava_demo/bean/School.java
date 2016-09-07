package com.jack.rxjava_demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jack on 2016/6/20.
 */

public class School {
    private String schoolId;
    private String schoolName;

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

}

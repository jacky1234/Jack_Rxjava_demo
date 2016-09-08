package com.jack.rxjava_demo.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jacky on 2016/9/7.
 */

public class ContentUtils {
    public static void showToast(Context context,String info){
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }

    public static  boolean isInclude(String[] arr,String s){
        boolean res = false;
        for (String s1 : arr) {
            if (s.equals(s1)) {
                res = true;
                break;
            }
        }

        return res;
    }
}

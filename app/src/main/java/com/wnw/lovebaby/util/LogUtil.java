package com.wnw.lovebaby.util;

import android.util.Log;

/**
 * Created by wnw on 2016/12/14.
 *
 * you can print your log use LogUtil when you set the level is VERBOSE
 * you also stop print your log use LogUtil when you set the level is :NOTHING
 */

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    public static int level = VERBOSE;

    public static void v(String tag, String msg){
        if(level <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(level <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(level <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(level <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (level <= ERROR){
            Log.e(tag, msg);
        }
    }
}
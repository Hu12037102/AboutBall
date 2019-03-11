package com.work.guaishouxingqiu.aboutball.util;

import android.util.Log;

import com.work.guaishouxingqiu.aboutball.BuildConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:38
 * 更新时间: 2019/3/11 11:38
 * 描述:
 */
public class LogUtils {
    public static void w(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }
}

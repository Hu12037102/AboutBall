package com.ifeell.app.aboutball.util;

import android.util.Log;

import com.ifeell.app.aboutball.BuildConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:38
 * 更新时间: 2019/3/11 11:38
 * 描述:
 */
public class LogUtils {
    private static final int MAX_LOG_LENGTH = 4000;

    public static void w(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            if (msg == null){
                return;
            }
            if ( msg.length() > MAX_LOG_LENGTH) {
                for (int i = 0; i < msg.length(); i += MAX_LOG_LENGTH) {
                    if (i + MAX_LOG_LENGTH < msg.length())
                        Log.w(tag, msg.substring(i, i + MAX_LOG_LENGTH));
                    else {
                        Log.w(tag, msg.substring(i, msg.length()));
                    }
                }
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            if (msg == null){
                return;
            }
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            if (msg == null){
                return;
            }
            Log.e(tag, msg);
        }
    }

    /*public static void w(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.w(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.w(tag, msg);
    }*/
}

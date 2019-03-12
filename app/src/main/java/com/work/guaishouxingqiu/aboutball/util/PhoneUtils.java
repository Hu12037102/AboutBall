package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 16:40
 * 更新时间: 2019/3/12 16:40
 * 描述:手机相关
 */
public class PhoneUtils {
    public static Locale getPhoneLoca(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }
}

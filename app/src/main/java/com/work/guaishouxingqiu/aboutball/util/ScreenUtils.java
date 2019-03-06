package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/5 14:44
 * 更新时间: 2019/3/5 14:44
 * 描述: 屏幕相关的工具类
 */
public class ScreenUtils {
    /**
     * 设置状态栏字体颜色
     *
     * @param decorView
     * @param dark
     */
    public static void setStatusTextColor(@NonNull View decorView, boolean dark) {
        if (dark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int  getScreenWidth(@NonNull Context context){
       return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int  getScreenHeight(@NonNull Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}

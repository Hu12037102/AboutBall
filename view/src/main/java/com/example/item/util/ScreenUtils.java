package com.example.item.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

public class ScreenUtils {
    public static int screenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int screenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setDefaultRootViewSize(@NonNull Context context,@NonNull ViewGroup rootView){
        ViewGroup.LayoutParams rootParams = rootView.getLayoutParams();
        rootParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rootParams.height = ScreenUtils.dp2px(context,45);
        rootView.setLayoutParams(rootParams);

    }

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

package com.work.guaishouxingqiu.aboutball.util;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 界面相关的工具类
 */
public class UIUtils {
    private static Context mContext;
    public static void init(@NonNull Context context){
        UIUtils.mContext = context;
    }
    public static Context getContext(){
        return mContext;
    }
}

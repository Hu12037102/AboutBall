package com.work.guaishouxingqiu.aboutball.router;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:56
 * 更新时间: 2019/3/15 10:56
 * 描述: 路由页面跳转
 */
public class ARouterIntent {
    public static void startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path) {
        return (T) ARouter.getInstance().build(path).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, int values) {
        return (T) ARouter.getInstance().build(path).withInt(key, values).navigation();
    }
}

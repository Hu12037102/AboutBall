package com.work.guaishouxingqiu.aboutball.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;

import retrofit2.http.PUT;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:56
 * 更新时间: 2019/3/15 10:56
 * 描述: 路由页面跳转
 */
public class ARouterIntent {
    public static final int REQUEST_CODE = 110;

    public static void startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull String key, int values) {
        ARouter.getInstance().build(path).withInt(key, values).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull String key, long values) {
        ARouter.getInstance().build(path).withLong(key, values).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull String key, @NonNull Object obj) {
        ARouter.getInstance().build(path).withObject(key, obj).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path) {
        return (T) ARouter.getInstance().build(path).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, int values) {
        return (T) ARouter.getInstance().build(path).withInt(key, values).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, @NonNull Parcelable parcelable) {
        return (T) ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation();
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, int requestCode) {
        ARouter.getInstance().build(path).navigation(activity, requestCode);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity) {
        ARouter.getInstance().build(path).navigation(activity, REQUEST_CODE);
    }
}

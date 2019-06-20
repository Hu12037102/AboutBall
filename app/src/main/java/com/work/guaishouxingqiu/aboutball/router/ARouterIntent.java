package com.work.guaishouxingqiu.aboutball.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public static void startActivity(@NonNull String path, @NonNull String key, int values, Activity activity, int requestCode) {
        ARouter.getInstance().build(path).withInt(key, values).navigation(activity, requestCode);
    }


    public static void startActivity(@NonNull String path, @NonNull String key, long values) {
        ARouter.getInstance().build(path).withLong(key, values).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull String key, long values, String stringKey, String stringValues) {
        ARouter.getInstance().build(path).withLong(key, values).withString(stringKey, stringValues).navigation();
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

    public static <T extends Fragment> T getFragment(@NonNull String path, Bundle bundle) {
        return (T) ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, int values) {
        return (T) ARouter.getInstance().build(path).withInt(key, values).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, long values) {
        return (T) ARouter.getInstance().build(path).withLong(key, values).navigation();
    }

    public static <T extends Fragment> T getFragment(@NonNull String path, @NonNull String key, @NonNull Parcelable parcelable) {
        return (T) ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation();
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, int requestCode) {
        ARouter.getInstance().build(path).navigation(activity, requestCode);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, String key, long values) {
        ARouter.getInstance().build(path).withLong(key, values).navigation(activity, REQUEST_CODE);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, String key, long values, int requestCode) {
        ARouter.getInstance().build(path).withLong(key, values).navigation(activity, requestCode);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity) {
        ARouter.getInstance().build(path).navigation(activity, REQUEST_CODE);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path).with(bundle).navigation(activity, requestCode);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation(activity, REQUEST_CODE);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, @NonNull String key, @NonNull Parcelable parcelable) {
        ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation(activity, REQUEST_CODE);
    }

    public static void startActivityForResult(@NonNull String path, @NonNull Activity activity, @NonNull String key, @NonNull Parcelable parcelable, int requestCode) {
        ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation(activity, requestCode);
    }

    public static void startActivity(@NonNull String path, @NonNull String key, @NonNull Parcelable parcelable) {
        ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation();
    }

    public static void startActivity(@NonNull String path, @NonNull String key, @NonNull ArrayList<Parcelable> data) {
        ARouter.getInstance().build(path).withParcelableArrayList(key, data).navigation();
    }

    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), clazz), REQUEST_CODE);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), clazz), requestCode);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz, String key, long values, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        intent.putExtra(key, values);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        intent.putExtras(bundle);
        fragment.startActivityForResult(intent, requestCode);
    }
    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz,String key,Parcelable parcelable) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        intent.putExtra(key,parcelable);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }
    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz,String key,Parcelable parcelable,int requestCode) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        intent.putExtra(key,parcelable);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(@NonNull Fragment fragment, Class clazz, Bundle bundle) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        intent.putExtras(bundle);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }
}

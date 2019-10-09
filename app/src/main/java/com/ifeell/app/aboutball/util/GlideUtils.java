package com.ifeell.app.aboutball.util;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 11:30
 * 更新时间: 2019/3/6 11:30
 * 描述: 加载图片相关的工具类
 */
public class GlideUtils {
    public static void loadImage(@NonNull Context context, @DrawableRes int resId, @DrawableRes int resPlaceholderId,
                          @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }
    public static void loadImage(@NonNull Context context, @DrawableRes int resId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }
}

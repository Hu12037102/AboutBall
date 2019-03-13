package com.work.guaishouxingqiu.aboutball.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;


/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 17:56
 * 更新时间: 2019/3/13 17:56
 * 描述: 加载图片管理器
 */
public class GlideManger {
    private GlideManger mGlideManger = new GlideManger();

    private GlideManger() {
        initGlide(UIUtils.getContext());
    }

    @SuppressLint("VisibleForTests")
    private void initGlide(Context context) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .skipMemoryCache(false);

        DiskCache.Factory factory = new DiskLruCacheFactory(FileUtils.getImageCacheFile().getAbsolutePath(),
                200 * 1024 * 1024);
        GlideBuilder mGlideBuilder = new GlideBuilder()
                .setDefaultRequestOptions(options)
                .setDiskCache(factory);
        Glide.init(context,mGlideBuilder);
    }
    public static void loadImage(@NonNull Context context, @DrawableRes int resId, @DrawableRes int resPlaceholderId,
                                 @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }
    public static void loadImage(@NonNull Context context, @DrawableRes int resId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }

    public static void loadImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }

    public static void loadImage(@NonNull Context context, @NonNull String imagePath, @DrawableRes int resPlaceholderId,
                                 @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }
}

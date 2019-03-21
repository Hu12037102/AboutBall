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
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;


/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 17:56
 * 更新时间: 2019/3/13 17:56
 * 描述: 加载图片管理器
 */
public class GlideManger {
    private static GlideManger mGlideManger = new GlideManger();

    private GlideManger() {
        initGlide(UIUtils.getContext());
    }

    public static synchronized GlideManger get() {
        return mGlideManger;
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
        Glide.init(context, mGlideBuilder);
    }

    public void loadImage(@NonNull Context context, @DrawableRes int resId, @DrawableRes int resPlaceholderId,
                          @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }

    public void loadMediaImage(@NonNull Context context, @NonNull String path, @NonNull ImageView imageView, boolean isCrop) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_image_background).error(R.mipmap.icon_image_background);
        if (isCrop) {
            requestOptions = requestOptions.centerCrop();
        } else {
            requestOptions = requestOptions.centerInside();
        }
        Glide.with(context).asDrawable().apply(requestOptions).load(path).into(imageView);
    }

    public void loadImage(@NonNull Context context, @DrawableRes int resId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();

        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String imagePath, @DrawableRes int resPlaceholderId,
                          @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }

    public void loadDefaultImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        loadImage(context, imagePath, R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_banner_view, imageView);
    }
}

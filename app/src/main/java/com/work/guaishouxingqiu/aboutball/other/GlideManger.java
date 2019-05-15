package com.work.guaishouxingqiu.aboutball.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.io.File;


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
        GlideBuilder glideBuilder = new GlideBuilder()
                .setDefaultRequestOptions(options)
                .setDiskCache(factory);
        Glide.init(context, glideBuilder);
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
    public void loadHeadImage(Context context, @NonNull String path, @NonNull ImageView imageView){
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_my_default_head).error(R.mipmap.icon_my_default_head)
                .centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(path).into(imageView);
    }

    public void loadLogoImage(Context context,@NonNull String path,@NonNull ImageView imageView){
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_default_logo).error(R.mipmap.icon_default_logo)
                .centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(path).into(imageView);
    }
    public void loadImage(@NonNull Context context, @DrawableRes int resId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();

        Glide.with(context).asDrawable().apply(requestOptions).load(resId).into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.icon_default_banner).error(R.mipmap.icon_default_banner);
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }
    public void loadImage(@NonNull Context context, @NonNull File file, @NonNull ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.icon_default_banner).error(R.mipmap.icon_default_banner);
        Glide.with(context).asBitmap().apply(requestOptions).load(file).into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String imagePath, @DrawableRes int resPlaceholderId,
                          @DrawableRes int resErrorId, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(resPlaceholderId).error(resErrorId);
        Glide.with(context).asDrawable().apply(requestOptions).load(imagePath).into(imageView);
    }

    public void loadDefaultImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        loadImage(context, imagePath, R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_banner_view, imageView);
    }
    public void loadImageDrawable(@NonNull String imagePath,@NonNull CustomTarget<Drawable> target){
        Glide.with(UIUtils.getContext()).asDrawable().load(imagePath).into(target);
    }

    public void loadBannerImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView){
        loadImage(context, imagePath, R.mipmap.icon_default_banner, R.mipmap.icon_default_banner, imageView);
    }
}

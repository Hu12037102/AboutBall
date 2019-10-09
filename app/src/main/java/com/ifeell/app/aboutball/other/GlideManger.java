package com.ifeell.app.aboutball.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.FileUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    public void loadHeadImage(Context context, @NonNull String path, @NonNull ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.icon_my_default_head).error(R.mipmap.icon_my_default_head)
                .centerCrop();
        Glide.with(context).asDrawable().apply(requestOptions).load(path).into(imageView);
    }

    public void loadLogoImage(Context context, @NonNull String path, @NonNull ImageView imageView) {
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

    public void loadImageDrawable(@NonNull String imagePath, @NonNull CustomTarget<Drawable> target) {
        Glide.with(UIUtils.getContext()).asDrawable().load(imagePath).into(target);
    }
    public void loadImageBitmap(@NonNull String imagePath, @NonNull CustomTarget<Bitmap> target) {
        Glide.with(UIUtils.getContext()).asBitmap().load(imagePath).into(target);
    }

    public void loadBannerImage(@NonNull Context context, @NonNull String imagePath, @NonNull ImageView imageView) {
        loadImage(context, imagePath, R.mipmap.icon_default_banner, R.mipmap.icon_default_banner, imageView);
    }

    public void downloadImage(@NonNull String imagePath) {
        Glide.with(UIUtils.getContext()).asBitmap().load(imagePath).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                downloadImage(resource, null);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

    }

    public File bitmapToFile(Bitmap bitmap, @Nullable String imagePath) {
        if (bitmap == null || bitmap.getHeight() <= 0 || bitmap.getWidth() <= 0) {
            UIUtils.showToast(R.string.failed_to_download_picture);
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        if (DataUtils.isEmpty(imagePath)) {
            imagePath = FileUtils.getDownloadImageFile().getAbsolutePath();
        }
        File file = new File(imagePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray(), 0, bos.toByteArray().length);
            fos.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void downloadImage(final Bitmap bitmap, @Nullable final String imagePath) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        if (executors.isShutdown()) {
            return;
        }
        executors.execute(() -> {
            File file = bitmapToFile(bitmap, imagePath);
            if (file != null && FileUtils.existsFile(file.getAbsolutePath())) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    FileUtils.scanImage(UIUtils.getContext(), file);
                    UIUtils.showToast(R.string.succeed_to_download_picture);
                    LogUtils.w("downloadImage--", file.getAbsolutePath());
                });
            }
        });
    }

    public void bitmapToImage(final Bitmap bitmap, @Nullable final String imagePath) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        if (executors.isShutdown()) {
            return;
        }
        executors.execute(() -> {
            File file = bitmapToFile(bitmap, imagePath);
            if (file != null && FileUtils.existsFile(file.getAbsolutePath())) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    FileUtils.scanImage(UIUtils.getContext(), file);
                });
            }
        });
    }

    public void bitmapToImage(final Bitmap bitmap, @Nullable final String imagePath, @Nullable OnDownLoadImageListener onDownLoadImageListener) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        if (executors.isShutdown()) {
            return;
        }
        executors.execute(() -> {
            File file = bitmapToFile(bitmap, imagePath);
            new Handler(Looper.getMainLooper()).post(() -> {
                if (file != null && FileUtils.existsFile(file.getAbsolutePath())) {
                    FileUtils.scanImage(UIUtils.getContext(), file);
                    if (onDownLoadImageListener != null) {
                        onDownLoadImageListener.onDownLoadSucceed(file);
                    }
                } else {
                    if (onDownLoadImageListener != null) {
                        onDownLoadImageListener.onDownLoadFailed();
                    }
                }
            });
        });
    }

    public interface OnDownLoadImageListener {
        void onDownLoadSucceed(File file);

        void onDownLoadFailed();
    }
}

package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.work.guaishouxingqiu.aboutball.media.weight.MediaScanner;

import java.io.File;
import java.util.List;

import io.reactivex.internal.operators.flowable.FlowableOnErrorNext;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 15:13
 * 更新时间: 2019/3/4 15:13
 * 描述: 文件工具类
 */
public class FileUtils {
    public static final String ROOT_FILE_NAME = "AboutBall";
    private static final String CACHE_FILE_NAME = "Cache";
    private static final String NET_CACHE_NAME = "NetCache";
    private static final String IMAGE_CACHE_NAME = "ImageCache";
    private static final String CAMERA = "Camera";


    private static File getRootFolder() {
        File rootFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath().concat("/AboutBall"));
        } else {
            rootFile = new File(UIUtils.getContext().getFilesDir().getAbsolutePath().concat("/AboutBall"));
        }
        createFolder(rootFile);
        return rootFile;
    }

    /**
     * 获取缓存目录
     *
     * @return 返回文件夹
     */
    public static File getCacheFile() {
        File cacheFile = new File(getRootFolder().getAbsolutePath(), FileUtils.CACHE_FILE_NAME);
        createFolder(cacheFile);
        return cacheFile;
    }

    /**
     * 返回图片缓存目录
     *
     * @return
     */
    public static File getImageCacheFile() {
        File imageCacheFile = new File(getRootFolder().getAbsolutePath(), FileUtils.IMAGE_CACHE_NAME);
        createFolder(imageCacheFile);
        return imageCacheFile;
    }

    public static File resultImageCameraFile() {
        File imageCameraFile = new File(getRootFolder().getAbsolutePath(), FileUtils.CAMERA);
        createFolder(imageCameraFile);
        return new File(imageCameraFile.getAbsolutePath(), "zhty_" + System.currentTimeMillis() + ".jpg");
    }

    public static File resultImageCacheFile() {
        File imageCameraFile = new File(getRootFolder().getAbsolutePath(), FileUtils.IMAGE_CACHE_NAME);
        createFolder(imageCameraFile);
        return new File(imageCameraFile.getAbsolutePath(), "crop" + System.currentTimeMillis() + ".jpg");
    }

    public static Uri fileToUri(@NonNull Context context, @NonNull File file, @NonNull Intent intent) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = context.getPackageName() + ".provider";
            uri = FileProvider.getUriForFile(context, authority, file);
            List<ResolveInfo> resolveInfoData = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfoData != null && resolveInfoData.size() > 0)
                for (ResolveInfo resolveInfo : resolveInfoData) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static void scanImage(@NonNull Context context, @NonNull File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MediaScanner ms = new MediaScanner(context, file);
            ms.refresh();
        } else {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
        }
    }

    /**
     * 获取缓存目录下的网络缓存
     *
     * @return
     */
    public static File getNetCacheFile() {
        File netCacheFile = new File(getCacheFile().getAbsolutePath(), FileUtils.NET_CACHE_NAME);
        createFolder(netCacheFile);
        return netCacheFile;
    }

    private static void createFolder(@NonNull File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
    }


    public static boolean existsFile(@NonNull String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile())
            return true;
        return false;
    }

    /**
     * 获取父文件夹名字
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getParentFileName(@NonNull String filePath) {
        return getParentFile(filePath).getName();
    }

    private static File getParentFile(@NonNull String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.getParentFile();
        }
        throw new NullPointerException("file must exists or isFile");
    }

    /**
     * 获取父文件夹绝对路径
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getParentFilePath(@NonNull String filePath) {
        return getParentFile(filePath).getAbsolutePath();
    }

    public static int getFileWidth(@NonNull String path) {
        return getBitmapOptions(path).outWidth;
    }

    public static int getFileHeight(@NonNull String path) {
        return getBitmapOptions(path).outHeight;
    }

    private static BitmapFactory.Options getBitmapOptions(@NonNull String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return options;
    }

    public static void removeFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            if (listFile != null && listFile.length > 0) {
                for (int i = 0; i < listFile.length; i++) {
                    removeFile(listFile[i]);
                }
            }
        }
    }

    /**
     * 获取单个文件夹里面文件大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long fileSize = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File childFile : files) {
                if (childFile.isFile()) {
                    fileSize += childFile.length();
                }
            }
        } else if (file.isFile()) {
            fileSize += file.length();
        }
        return fileSize;
    }

}

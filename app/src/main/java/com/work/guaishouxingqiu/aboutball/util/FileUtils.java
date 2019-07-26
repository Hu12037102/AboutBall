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

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.work.guaishouxingqiu.aboutball.media.weight.MediaScanner;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

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
    private static final String VIDEO = "Video";
    private static final String CAMERA = "Camera";
    private static final String DOWNLOAD = "Download";
    private static final int MAX_VIDEO_LENGTH = 5;


    public static File getRootFolder() {
        File rootFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath().concat("/AboutBall"));
        } else {
            rootFile = new File(UIUtils.getContext().getFilesDir().getAbsolutePath().concat("/AboutBall"));
        }
        createFolder(rootFile);
        return rootFile;
    }

    public static File createFolder(String folderName) {
        File rootFolder = getRootFolder();
        File childFolder = new File(rootFolder, folderName);
        createFolder(childFolder);
        return childFolder;
    }

    public static File getDownloadImageFile() {
        File folder = createFolder(FileUtils.DOWNLOAD);
        return new File(folder.getAbsolutePath(), "about_ball" + System.currentTimeMillis() + ".jpg");

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

    public static File createCacheVideoFile() {
        File cacheVideoFile = getCacheFile();
        cacheVideoFile = new File(cacheVideoFile.getAbsolutePath(), "video_" + System.currentTimeMillis() + ".mp4");
        return cacheVideoFile;
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

    public static File createVideoFile() {
        File videoFile = createVideoFolder();
        videoFile = new File(videoFile.getAbsolutePath(), "video_" + System.currentTimeMillis() + ".mp4");
        return videoFile;
    }


    public static File createVideoFolder() {
        File videoFolder = new File(getRootFolder().getAbsolutePath(), FileUtils.VIDEO);
        createFolder(videoFolder);
        return videoFolder;
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
     * @throws
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
     * @throws
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
        if (file == null || !file.exists()) {
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
    public static void removeOSSCacheFile(@NonNull File file){
        if (file.exists()){
            File parentFile = file.getParentFile();
            if (parentFile.getName().equalsIgnoreCase(FileUtils.CACHE_FILE_NAME)){
               file.delete();
            }
        }
    }

    public static void removeFileCache() {
        removeFile(FileUtils.getCacheFile());
        removeFile(FileUtils.getImageCacheFile());
        removeFile(UIUtils.getContext().getCacheDir());
    }

    /**
     * 获取单个文件夹里面文件大小
     *
     * @param file
     * @return
     */
    public static long getFilesSize(File file) {
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

    /**
     * 获取文件的大小M，保留两位小数
     *
     * @return
     */
    public static String getFileSize2M(@NonNull File file) {
        if (file.exists() && file.isFile()) {
            long length = file.length();
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(2);
            numberFormat.setMinimumFractionDigits(2);
            return numberFormat.format((double) length / 1000d / 1000d);
        }
        return "0";
    }

    /**
     * 判断文件大小是否大于视频约定大小压缩值
     * @param file 文件
     * @return
     */
    public static boolean isCanCompressVideo(@NonNull File file) {
        return Double.valueOf(getFileSize2M(file)) > FileUtils.MAX_VIDEO_LENGTH;
    }
}

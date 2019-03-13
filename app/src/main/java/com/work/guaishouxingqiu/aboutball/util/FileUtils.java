package com.work.guaishouxingqiu.aboutball.util;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

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
     * @return
     */
    public static File getImageCacheFile(){
        File imageCacheFile = new File(getRootFolder().getAbsolutePath(), FileUtils.IMAGE_CACHE_NAME);
        createFolder(imageCacheFile);
        return imageCacheFile;
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
}

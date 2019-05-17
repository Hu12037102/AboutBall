package com.work.guaishouxingqiu.aboutball.other;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.receiver.DownloadMangerReceiver;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.io.File;

import utils.UiUtils;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.other
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/4/6
 * 描  述 :  ${TODO}下载APK帮助类
 *
 * @author ：
 */
public class DownloadApkHelp {

    public static void loadApk(@NonNull Context context, @NonNull String apkUrl) {
        DownloadManager loadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (loadManager == null) {
            return;
        }
        String http = "http://";
        if (apkUrl.startsWith(http)) {
            apkUrl = apkUrl.replace(http, "https://");
        }
        Uri uri = Uri.parse(apkUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        File apkFile = new File(FileUtils.createFolder("APK").getAbsolutePath(), "/AboutBall_" + System.currentTimeMillis() + ".apk");
        request = request.setDestinationUri(Uri.fromFile(apkFile));
        request = request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request = request.setTitle(UIUtils.getString(R.string.update_apk));
        request = request.setVisibleInDownloadsUi(true);
        request = request.setMimeType("application/vnd.android.package-archive");
        loadManager.enqueue(request);
    }

    private static void openWebViewLoadApk(Context context, String apkUrl) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl)));
    }

    public static void installApk(@NonNull File apkFile, @NonNull Context context) {
        try {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
            } else {
                contentUri = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static String getVersionName(Context context) {
        try {
            return getPackInfo(context).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static PackageInfo getPackInfo(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    }

    public static int getVersionCode(Context context) {
        try {
            return getPackInfo(context).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

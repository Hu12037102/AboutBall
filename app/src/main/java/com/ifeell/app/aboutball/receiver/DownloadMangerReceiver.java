package com.ifeell.app.aboutball.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.ifeell.app.aboutball.other.DownloadApkHelp;
import com.ifeell.app.aboutball.util.LogUtils;

import java.io.File;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.receiver
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/4/20
 * 描  述 :  ${TODO}
 *
 * @author ：downloadmanger下载广播
 */
public class DownloadMangerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager == null) {
            return;
        }
        long queryId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        String action = intent.getAction();
        if (action == null || queryId == -1) {
            return;
        }
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(queryId);
        switch (action) {
            //下载完成
            case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                Cursor cursor = manager.query(query);
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToNext()) {
                    String filePath = null;
                    //7.0以上
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        String filePathUri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                        if (filePathUri != null) {
                            filePath = Uri.parse(filePathUri).getPath();
                        }
                        LogUtils.w("DownloadManager--",filePath);
                    } else {
                        //5.0;
                        filePath = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_FILENAME));
                    }
                    if (filePath != null) {
                        File apkFile = new File(filePath);
                        if (apkFile.exists() && apkFile.isFile()) {
                            DownloadApkHelp.installApk(apkFile, context);
                        }
                    }
                }

                break;
            case DownloadManager.ACTION_NOTIFICATION_CLICKED:
                break;
            case DownloadManager.ACTION_VIEW_DOWNLOADS:
                break;
            default:
                break;
        }


    }
}

package com.ifeell.app.aboutball.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 15:51
 * 更新时间: 2019/3/4 15:51
 * 描述:
 */
public class NetWorkUtils {
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetWrokConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            NetworkInfo networkInfo = netManger.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 判断手机wifi是否连接可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileWifiConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            NetworkInfo networkInfo = netManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机移动网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetConnected(@NonNull Context context) {
        ConnectivityManager netManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManger != null) {
            NetworkInfo networkInfo = netManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetCanUse() {
        return NetWorkUtils.isNetWrokConnected(UIUtils.getContext()) && (NetWorkUtils.isMobileNetConnected(UIUtils.getContext()) ||
                NetWorkUtils.isMobileWifiConnected(UIUtils.getContext()));
    }
}

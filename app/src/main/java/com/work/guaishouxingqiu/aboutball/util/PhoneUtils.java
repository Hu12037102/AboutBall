package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import java.util.Locale;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 16:40
 * 更新时间: 2019/3/12 16:40
 * 描述:手机相关
 */
public class PhoneUtils {
    public static Locale getPhoneLoca(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public static boolean isOpenGPS(@NonNull Context context) {
        LocationManager locationManager
                = (LocationManager) DataUtils.checkData(context).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean networkGPS = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || networkGPS;
    }

    public static void openGPSDialog(@NonNull Fragment fragment) {
        HintDialog hintDialog = new HintDialog.Builder(fragment.getContext())
                .setTitle(R.string.hint)
                .setBody(R.string.gprs_not_open)
                .setSure(R.string.go_open)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            fragment.startActivityForResult(intent, Contast.REQUEST_CODE);
            hintDialog.dismiss();
        });
    }

    public static void checkoutGPS(@NonNull Fragment fragment) {
        if (!isOpenGPS(DataUtils.checkData(fragment.getContext()))) {
            openGPSDialog(fragment);
        }
    }
}
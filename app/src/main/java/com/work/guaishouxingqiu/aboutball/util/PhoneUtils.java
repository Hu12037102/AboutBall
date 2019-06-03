package com.work.guaishouxingqiu.aboutball.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.ActivityManger;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.List;
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

    public static void openGPSDialog(@NonNull Activity activity) {
        HintDialog hintDialog = new HintDialog.Builder(activity)
                .setTitle(R.string.hint)
                .setBody(R.string.gprs_not_open)
                .setSure(R.string.go_open)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(intent, Contast.REQUEST_CODE);
            hintDialog.dismiss();
        });
    }

    public static void checkoutGPS(@NonNull Activity activity) {
        if (!isOpenGPS(DataUtils.checkData(activity))) {
            openGPSDialog(activity);
        }
    }


    public static void checkoutGPS(@NonNull Fragment fragment) {
        if (!isOpenGPS(DataUtils.checkData(fragment.getContext()))) {
            openGPSDialog(fragment);
        }
    }

    public static Location getGPSLocation(@NonNull Fragment fragment) {
        PhoneUtils.checkoutGPS(fragment);
        Context context = fragment.getContext();
        LocationManager locationManager = (LocationManager) DataUtils.checkData(context).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return null;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toasts.with().showToast(R.string.please_open_location);
            return null;
        }

        List<String> providers = locationManager.getProviders(true);
        String provider = null;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        if (provider == null) {
            return null;
        }
        return locationManager.getLastKnownLocation(provider);
    }


    public static Location getGPSLocation(@NonNull Activity activity) {
        PhoneUtils.checkoutGPS(activity);
        LocationManager locationManager = (LocationManager) DataUtils.checkData(activity).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return null;
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toasts.with().showToast(R.string.please_open_location);
            return null;
        }

        List<String> providers = locationManager.getProviders(true);
        String provider = null;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        if (provider == null) {
            return null;
        }
       /* locationManager.requestLocationUpdates(provider, 3000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LogUtils.w("requestLocationUpdates--", "我的位置发生改变了" + "--" + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LogUtils.w("requestLocationUpdates--", "onStatusChanged--" );
            }

            @Override
            public void onProviderEnabled(String provider) {
                LogUtils.w("requestLocationUpdates--", "onProviderEnabled--" );
            }

            @Override
            public void onProviderDisabled(String provider) {
                LogUtils.w("requestLocationUpdates--", "onProviderDisabled--" );
            }
        });*/
        return locationManager.getLastKnownLocation(provider);
    }

    public static void callPhone(Context context, String phoneNumber) {
        //跳转到拨号界面，同时传递电话号码
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(phoneIntent);
    }

    public static void launchBackgroundApp(@NonNull Context context) {
        boolean isRunApp = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        if (am == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<ActivityManager.AppTask> taskData = am.getAppTasks();
            for (ActivityManager.AppTask task : taskData) {
                ActivityManager.RecentTaskInfo taskInfo = task.getTaskInfo();
                if (taskInfo != null && taskInfo.baseActivity != null && taskInfo.baseActivity.getPackageName().equals(packageName)) {
                    context.startActivity(task.getTaskInfo().baseIntent);
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskData = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskData) {
                if (taskInfo.baseActivity.getPackageName().equals(packageName)) {
                    context.startActivity(new Intent(context, taskInfo.baseActivity.getClass()));
                }
            }
        }
    }
}

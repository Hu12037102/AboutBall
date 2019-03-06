package com.work.guaishouxingqiu.aboutball.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @ClassName: IntentUtils
 * @Author: 胡庆岭
 * @CreateTime: 2019/3/4 13:14
 * @UpdateTime: 2019/3/4 13:14
 * @Description:
 */

public class IntentUtils {
    public static final int OPEN_APPLY_CENTER_CODE = 5501;

    /**
     * Activity打开应用中心
     *
     * @param activity 页面
     */
    public static void openActivityApplyCenter(@NonNull Activity activity) {
        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        activity.startActivityForResult(intent, OPEN_APPLY_CENTER_CODE);
    }

    /**
     * fragemnt打开应用中心
     *
     * @param fragment fragment
     * @param activity activity
     */
    public static void openFragmentApplyCenter(@NonNull Fragment fragment, @NonNull Activity activity) {
        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        fragment.startActivityForResult(intent, OPEN_APPLY_CENTER_CODE);
    }
}

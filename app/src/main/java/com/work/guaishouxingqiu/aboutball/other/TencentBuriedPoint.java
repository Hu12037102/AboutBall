package com.work.guaishouxingqiu.aboutball.other;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.Properties;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/19 17:08
 * 更新时间: 2019/8/19 17:08
 * 描述:腾讯埋点信息
 */
public class TencentBuriedPoint {
    public static void init(@NonNull Context context) {
        try {
            boolean b = StatService.startStatService(context, Contast.SecretKey.TENCENT_SELLING_POINTS_KEY, StatConstants.VERSION);
            LogUtils.w("TencentBuriedPoint--", "MTA初始化成功" + "--" + b);
        } catch (MtaSDkException e) {
            LogUtils.w("TencentBuriedPoint---", "MTA初始化失败");
            e.printStackTrace();
        }
    }

    public void clickBuriedPoint(@NonNull Context context, @NonNull String key) {
        Properties prop = new Properties();
        prop.setProperty(key, key + "_" + System.currentTimeMillis());
        StatService.trackCustomKVEvent(context, key, prop);
    }

    public static void defaultBuriedPoint(@NonNull Context context, @NonNull String key) {
        TencentBuriedPoint point = new TencentBuriedPoint();
        point.clickBuriedPoint(context, key);
    }
}

package com.work.guaishouxingqiu.aboutball.other;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;


/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:56
 * 更新时间: 2019/3/6 16:56
 * 描述: SP相关的工具
 */
public class SharedPreferencesHelp {
    private static final String DEFAULT_SP_NAME = "About_Ball_SP";
    private static final String DEFAULT_STRING_VALUES = "";
    public static final String KEY_GUIDE_OPEN = "key_guide_open";
    //裁判列表key
    public static final String KEY_REFEREE_CACHE_LIST = "key_referee_cache_list";
    //退款原因key
    public static final String KEY_REFUND_CAUSE_LIST = "key_refund_cause_list";

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesHelp() {
        initSharedPreferences(DEFAULT_SP_NAME);
    }

    public SharedPreferencesHelp(String spName) {
        initSharedPreferences(spName);
    }

    private void initSharedPreferences(String spName) {
        mSharedPreferences = UIUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);

    }

    public void clearData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
    public void removeValues(@NonNull String key){
        if (!DataUtils.isEmpty(key)&&isContainsKey(key)){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.apply();
            editor.commit();
        }
    }

    public boolean isContainsKey(String key) {
        return mSharedPreferences.contains(key);
    }

    public void putObject(String key, Object obj) {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            if (obj instanceof String) {
                editor.putString(key, (String) obj);
            } else if (obj instanceof Float) {
                editor.putFloat(key, (Float) obj);
            } else if (obj instanceof Integer) {
                editor.putInt(key, (Integer) obj);
            } else if (obj instanceof Long) {
                editor.putLong(key, (Long) obj);
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, (Boolean) obj);
            }
            editor.apply();
            editor.commit();
        }
    }

    public String getString(@NonNull String key) {
        return mSharedPreferences.getString(key, SharedPreferencesHelp.DEFAULT_STRING_VALUES);
    }

    public String getString(@NonNull String key, @NonNull String defaultValues) {
        return mSharedPreferences.getString(key, defaultValues);
    }

    public int getInt(@NonNull String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public int getInt(@NonNull String key, int defaultValues) {
        return mSharedPreferences.getInt(key, defaultValues);
    }

    public float getFloat(@NonNull String key) {
        return mSharedPreferences.getFloat(key, 0.0f);
    }

    public float getFloat(@NonNull String key, float defaultValues) {
        return mSharedPreferences.getFloat(key, defaultValues);
    }

    public long getLong(@NonNull String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public long getLong(@NonNull String key, long defaultValues) {
        return mSharedPreferences.getLong(key, defaultValues);
    }

    public boolean getBoolean(@NonNull String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValues) {
        return mSharedPreferences.getBoolean(key, defaultValues);
    }

   public interface KEY {
        //经度key
        String LOCATION_LONGITUDE = "location_longitude";
        //纬度
        String LOCATION_LATITUDE = "location_latitude";
        //定位城市
        String LOCATION_CITY = "location_city";
    }
}

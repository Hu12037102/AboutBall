package com.work.guaishouxingqiu.aboutball.other;

import android.app.Activity;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 14:55
 * 更新时间: 2019/3/6 14:55
 * 描述: Activity管理器
 */
public class ActivityManger {
    private static ActivityManger mActivityManger;
    private final Map<String, WeakReference<Activity>> mWeakMap;

    private ActivityManger() {
        mWeakMap = new HashMap<>();
    }

    public static ActivityManger get() {
        synchronized (ActivityManger.class) {
            if (mActivityManger == null) {
                mActivityManger = new ActivityManger();
            }
        }
        return mActivityManger;
    }

    public void addActivity(@NonNull Activity activity) {
        mWeakMap.put(activity.getClass().getSimpleName(), new WeakReference<>(activity));
    }

    public void removeActivity(@NonNull Class activityClass) {
        String key = activityClass.getSimpleName();
        if (mWeakMap.containsKey(key)) {
            if (mWeakMap.get(key) != null && mWeakMap.get(key).get() != null && !mWeakMap.get(key).get().isFinishing()) {
                mWeakMap.get(key).get().finish();
            }
            mWeakMap.remove(key);
        }
    }

    public int getActivitySize() {
        return mWeakMap.size();
    }

    public void clearActivity() {
        Set<Map.Entry<String, WeakReference<Activity>>> entries = mWeakMap.entrySet();
        Iterator<Map.Entry<String, WeakReference<Activity>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WeakReference<Activity>> next = iterator.next();
            if (next.getValue() != null && next.getValue().get() != null) {
                next.getValue().get().finish();
            }
            iterator.remove();
        }
    }

}

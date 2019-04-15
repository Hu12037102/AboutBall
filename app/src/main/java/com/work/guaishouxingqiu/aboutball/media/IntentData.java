package com.work.guaishouxingqiu.aboutball.media;

import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/15 11:26
 * 更新时间: 2019/4/15 11:26
 * 描述:
 */
public class IntentData {

    private WeakReference mWeakData;

    private static IntentData mIntentData;

    private IntentData() {

    }

    public static IntentData get() {
        synchronized (IntentData.class) {
            if (mIntentData == null) {
                synchronized (IntentData.class) {
                    mIntentData = new IntentData();
                }
            }
        }
        return mIntentData;
    }


    public  <T>void putData(T data){
        mWeakData = new WeakReference<>(data);

    }

    public <T> T getData() {
        if (mWeakData != null && mWeakData.get() != null) {
            return (T) mWeakData.get();
        }
        return null;
    }
}

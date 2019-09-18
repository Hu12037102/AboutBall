package com.work.guaishouxingqiu.aboutball.other;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.EmojiBean;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/16 17:11
 * 更新时间: 2019/9/16 17:11
 * 描述:表情管理器
 */
public final class EmojiManger {
    private static SoftReference<ArrayMap<String, EmojiBean>> mSoftMap;
    private static EmojiManger mManger;
    private List<String> mEmojiData;

    private EmojiManger() {
        init();
    }

    public static EmojiManger get() {
        synchronized (EmojiManger.class) {
            if (mManger == null) {
                synchronized (EmojiManger.class) {
                    mManger = new EmojiManger();
                }
            }
        }
        return mManger;
    }

    private void init() {
        Context context = UIUtils.getContext();
        String emojiContent = context.getResources().getString(R.string.emoji_data).trim();
        mEmojiData = Arrays.asList(emojiContent.split(","));
        ArrayMap<String, EmojiBean> emojiMap = new ArrayMap<>();
        mSoftMap = new SoftReference<>(emojiMap);
        for (int i = 0; i < mEmojiData.size(); i++) {
            int resId = context.getResources().getIdentifier("emoji_" + (i + 1), "drawable", context.getPackageName());
            EmojiBean bean = new EmojiBean(resId, i, mEmojiData.get(i));
            emojiMap.put(mEmojiData.get(i), bean);
           // Log.w("EmojiManger--", (R.drawable.emoji_1 == resId) + "--" + mEmojiData.size() + "--" + i);
        }
    }
    public List<EmojiBean> getAllDrawable() {
        List<EmojiBean> data = new ArrayList<>();
        Log.w("EmojiManger---", mSoftMap.get() + "--");
        if (mSoftMap != null || mSoftMap.get() != null) {
            init();
        }
        // Log.w("EmojiManger---", mSoftMap.get().size() + "--");
        if (mSoftMap != null && mSoftMap.get() != null) {
            ArrayMap<String, EmojiBean> arrayMap = mSoftMap.get();
            data.addAll(arrayMap.values());
            Collections.sort(data, (o1, o2) -> o1.index - o2.index);
        }
        return data;
    }



    public List<String> getEmojiKeyData() {
        return mEmojiData;
    }



    public EmojiBean getEmoji(@NonNull String key) {
        if (mSoftMap == null || mSoftMap.get() == null) {
            init();
        }
        if (key != null && mSoftMap.get().containsKey(key)) {
            return mSoftMap.get().get(key);
        }
        return null;
    }

    public static EmojiBean getDefautDrawableId(@NonNull String key) {
        return EmojiManger.get().getEmoji(key);
    }

}

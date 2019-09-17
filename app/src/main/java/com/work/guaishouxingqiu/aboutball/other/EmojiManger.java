package com.work.guaishouxingqiu.aboutball.other;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/16 17:11
 * 更新时间: 2019/9/16 17:11
 * 描述:表情管理器
 */
public final class EmojiManger {
    private static SoftReference<ArrayMap<String, Integer>> mSoftMap;
    private static EmojiManger mManger;

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
        String[] emojiArray = emojiContent.split(",");
        ArrayMap<String, Integer> emojiMap = new ArrayMap<>();
        mSoftMap = new SoftReference<>(emojiMap);
        for (int i = 0; i < emojiArray.length; i++) {
            int resId = context.getResources().getIdentifier("emoji_" + (i + 1), "drawable", context.getPackageName());
            emojiMap.put(emojiArray[i], resId);
            Log.w("EmojiManger--", (R.drawable.emoji_1 == resId) + "--" + emojiArray.length + "--" + i);
        }
    }

    public List<Integer> getAllDrawable() {
        List<Integer> data = new ArrayList<>();
        if (mSoftMap != null && mSoftMap.get() != null) {
            ArrayMap<String, Integer> arrayMap = mSoftMap.get();
            data.addAll(arrayMap.values());
          /*  for (int i = 0; i < arrayMap.size(); i++) {
                Log.w("EmojiManger---", (arrayMap.get(arrayMap.keyAt(i)) == R.drawable.emoji_1) + "--");
                data.add(arrayMap.get(arrayMap.keyAt(i)));
            }*/
        }
        return data;
    }

    public Integer getDrawableRes(@NonNull String key) {
        if (key != null && mSoftMap != null && mSoftMap.get() != null && mSoftMap.get().containsKey(key)) {
            return mSoftMap.get().get(key);
        }
        return -1;
    }

    public static Integer getDefautDrawableId(@NonNull String key) {
        return EmojiManger.get().getDrawableRes(key);
    }

}

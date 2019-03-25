package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 14:37
 * 更新时间: 2019/3/25 14:37
 * 描述:
 */
public class FocusableTextView  extends AppCompatTextView {
    public FocusableTextView(Context context) {
        super(context);
    }

    public FocusableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

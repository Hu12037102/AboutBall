package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:23
 * 更新时间: 2019/3/7 11:23
 * 描述: 默认的ViewPager
 */
public class BaseViewPager extends ViewPager {
    private boolean isScroll =true;

    public void setViewPagerScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public BaseViewPager(@NonNull Context context) {
        super(context);
    }

    public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseViewPager);
        if (typedArray != null && typedArray.getIndexCount() > 0) {
            isScroll = typedArray.getBoolean(R.styleable.BaseViewPager_is_scroll, true);
            typedArray.recycle();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return isScroll ? super.onInterceptTouchEvent(ev) : false;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return isScroll ? super.onTouchEvent(ev) : true;
        } catch (Exception e) {
            return !isScroll;
        }
    }

}

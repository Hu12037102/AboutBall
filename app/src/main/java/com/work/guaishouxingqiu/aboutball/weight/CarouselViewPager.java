package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 9:27
 * 更新时间: 2019/3/13 9:27
 * 描述:自动轮播ViewPager
 */
public class CarouselViewPager extends BaseViewPager {
    public static final int WHAT = 100;

    public void setmDefaultTime(int mDefaultTime) {
        this.mDefaultTime = mDefaultTime;
    }

    private int mDefaultTime = 2000;
    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case CarouselViewPager.WHAT:
                this.setCurrentItem(this.getCurrentItem() + 1);
                break;
            default:
                break;
        }
        return true;
    });

    public CarouselViewPager(@NonNull Context context) {
        super(context);

    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getChildCount() > 0) {
                    mHandler.removeMessages(CarouselViewPager.WHAT, mDefaultTime);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (this.getChildCount() > 0) {
                    mHandler.sendEmptyMessageDelayed(CarouselViewPager.WHAT, mDefaultTime);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}

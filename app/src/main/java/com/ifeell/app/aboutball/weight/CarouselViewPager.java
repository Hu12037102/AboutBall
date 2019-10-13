package com.ifeell.app.aboutball.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 9:27
 * 更新时间: 2019/3/13 9:27
 * 描述:自动轮播ViewPager
 */
public class CarouselViewPager extends BaseViewPager {
   /* public static final int WHAT = 100;

    private int mDefaultTime = 2000;
    private Handler mHandler;
    public void setmDefaultTime(int mDefaultTime) {
        this.mDefaultTime = mDefaultTime;
    }

    public void autoCarouselPager() {
        if (mHandler == null) {
            mHandler = new Handler(msg -> {
                switch (msg.what) {
                    case CarouselViewPager.WHAT:
                        this.setCurrentItem(this.getCurrentItem() + 1,true);
                        mHandler.sendEmptyMessageDelayed(CarouselViewPager.WHAT, mDefaultTime);
                        break;
                    default:
                        break;
                }
                return true;
            });
        }
        mHandler.sendEmptyMessageDelayed(CarouselViewPager.WHAT, mDefaultTime);
    }*/


    public CarouselViewPager(@NonNull Context context) {
        super(context);

    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public static class Transformer implements ViewPager.PageTransformer {
        private static final float MAX_ALPHA = 0.5f;
        private static final float MAX_SCALE = 0.9f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position < -1 || position > 1) {
                //不可见区域
                page.setAlpha(MAX_ALPHA);
                page.setScaleX(MAX_SCALE);
                page.setScaleY(MAX_SCALE);
            } else {
                //可见区域，透明度效果
                if (position <= 0) {
                    //pos区域[-1,0)
                    page.setAlpha(MAX_ALPHA + MAX_ALPHA * (1 + position));
                } else {
                    //pos区域[0,1]
                    page.setAlpha(MAX_ALPHA + MAX_ALPHA * (1 - position));
                }
                //可见区域，缩放效果
                float scale = Math.max(MAX_SCALE, 1 - Math.abs(position));
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        }
    }
}

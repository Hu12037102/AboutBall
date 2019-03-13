package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 9:39
 * 更新时间: 2019/3/13 9:39
 * 描述: 自动轮播Adapter
 */
public class CarouseAdapter extends PagerAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}

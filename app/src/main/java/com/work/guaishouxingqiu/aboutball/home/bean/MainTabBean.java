package com.work.guaishouxingqiu.aboutball.home.bean;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 10:36
 * 更新时间: 2019/3/6 10:36
 * 描述: 主页面底部Tab
 */
public class MainTabBean {
    public MainTabBean(@NonNull String tabName) {
        this.mTabName = tabName;
    }

    public String mTabName;
    public boolean isChecked;
    public @DrawableRes int mCheckResIcon;
    public @DrawableRes int mDefaultResIcon;
    public @ColorRes int mCheckResText = R.color.color_2;
    public @ColorRes int mDefaultheckResText= R.color.color_3;
    public boolean showRedPoint;
}

package com.ifeell.app.aboutball.my.bean;

import androidx.annotation.DrawableRes;

import com.ifeell.app.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/13 13:55
 * 更新时间: 2019/5/13 13:55
 * 描述:
 */
public class AddImageBean {
    public AddImageBean() {
    }

    public AddImageBean(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isAdd;
    public String path;
    public @DrawableRes
    int resIcon = R.mipmap.icon_add_image;
    public boolean isVideo;

}

package com.work.guaishouxingqiu.aboutball.base.bean;

import androidx.annotation.DrawableRes;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/17 10:26
 * 更新时间: 2019/9/17 10:26
 * 描述:表情符号bean
 */
public class EmojiBean {
    public EmojiBean(@DrawableRes Integer drawableResId) {
        this.drawableResId = drawableResId;
    }

    public EmojiBean() {
    }

    public Integer drawableResId;

}

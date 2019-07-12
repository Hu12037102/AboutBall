package com.work.guaishouxingqiu.aboutball.commonality.bean;

import androidx.annotation.DrawableRes;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 14:51
 * 更新时间: 2019/5/9 14:51
 * 描述: 用于分享的bean
 */
public class ShareWebBean {
    public String webUrl;
    //标题
    public String title;
    //描述
    public String description;
    public @DrawableRes
    int iconRes = R.mipmap.app_launcher;
    public int scene;//聊天： SendMessageToWX.Req.WXSceneSession  朋友圈： SendMessageToWX.Req.WXSceneTimeline
}

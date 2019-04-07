package com.work.guaishouxingqiu.aboutball.weichat.bean;

import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.weichat.activity.LoginOrShareActivity;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:25
 * 更新时间: 2019/4/4 13:25
 * 描述:
 */
public class RequestWeiChatTokenBean {
    public String appid = Contast.SECRET_KEY.WEICHAT_APP_ID;
    public String secret = Contast.SECRET_KEY.WEICHAT_APP_SECRET;
    public String code;
    public String grant_type = "authorization_code";
}

package com.ifeell.app.aboutball.commonality.bean;

import com.ifeell.app.aboutball.Contast;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:25
 * 更新时间: 2019/4/4 13:25
 * 描述:
 */
public class RequestWeiChatTokenBean {
    public String appid = Contast.SecretKey.WEICHAT_APP_ID;
    public String secret = Contast.SecretKey.WEICHAT_APP_SECRET;
    public String code;
    public String grant_type = "authorization_code";
}

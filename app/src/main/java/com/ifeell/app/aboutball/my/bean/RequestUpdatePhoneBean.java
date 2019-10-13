package com.ifeell.app.aboutball.my.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 13:11
 * 更新时间: 2019/4/16 13:11
 * 描述:
 */
public class RequestUpdatePhoneBean {
    public String phone;
    public String verificationCode;
    public String signCode;
    /**
     * 1.微信
     * 2.qq
     * 3.微博
     */
    public int type;
}

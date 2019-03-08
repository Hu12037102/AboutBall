package com.work.guaishouxingqiu.aboutball.login.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 14:10
 * 更新时间: 2019/3/8 14:10
 * 描述: 请求登录bean
 */
public class RequestLoginBean {
    public String password;
    public String phone;
    //第三方登录时候带过来的唯一标识
    public String signCode;
    //类型
    public int  type;
    //验证码
    public String verificationCode;
}

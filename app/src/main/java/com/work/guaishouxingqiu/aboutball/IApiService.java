package com.work.guaishouxingqiu.aboutball;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 13:54
 * 更新时间: 2019/3/8 13:54
 * 描述: 接口API
 */
public interface IApiService {
    //登录
    String LOGIN = "/api/account/login";
    //发送验证码
    String VERIFICATION_CODE = "/api/commons/sms/verificationCode";
    //获取用户信息
    String USER_ACCOUNT = "/api/account";
    //用户注册
    String REGISTER = "/api/account/register";
    //首页-推荐头部信息列表
    String RECOMMEND_HEAD = "/api/home/getHomePage";
}

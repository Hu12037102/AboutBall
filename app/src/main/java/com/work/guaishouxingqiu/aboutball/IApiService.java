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
}

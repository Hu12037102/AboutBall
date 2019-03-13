package com.work.guaishouxingqiu.aboutball;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 11:12
 * 更新时间: 2019/3/8 11:12
 * 描述: 一些常量
 */
public interface Contast {
    //手机号码验证正则
    // public static final String REGEX_PHONE_NUMBER = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$";
    String REGEX_PHONE_NUMBER = "^[1][3,4,5,7,8][0-9]{9}$";
    //判断密码长度
    int MIN_PASSWORD_LENGTH = 6;
    //手机验证码长度
    int MESSAGE_CODE_LENGTH = 4;
    String PHONE = "phone";
    String TYPE = "type";
    //验证码类型: 1 注册,2 登录 3 重置密码
    int TYPE_MESSAGE_CODE_REGISTER = 1;
    int TYPE_MESSAGE_CODE_LOGIN = 2;
    int TYPE_MESSAGE_CODE_RESET_PASSWORD = 3;
    int MESSAGE_COUNT_DOWN_LENGTH = 60;
    int REQUEST_CODE = 0;
    String LONGITUDE = "longitude";
    String LATITUDE = "latitude";

}

package com.work.guaishouxingqiu.aboutball.http;

import com.work.guaishouxingqiu.aboutball.BuildConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 16:45
 * 更新时间: 2019/3/4 16:45
 * 描述:
 */
public interface IApi {
    String BASE_URL = BuildConfig.HOST_URL;

    interface Code {
        //用户已存在
        int USER_EXIST = -1;
        //发送短信失败
        int SEND_MESSAGES_FAILURE = -10;
        //验证码错误
        int MESSAGES_CODE_ERROR = -11;
        //该账户已禁用
        int ACCOUNT_DISABLED = -13;
        //获取存储服务OSS访问凭证出错
        int OSS_VOUCHER_FAILURE = -14;
        //获取APP访问视频VOD服务凭证出错
        int VOD_VOUCHER_FAILURE = 15;
        //用戶不存在
        int USER_NO_EXIST = -2;
        //账号或密码错误
        int PASSWORD_OR_ACCOUNT_ERROR = -3;
        //用戶未登录
        int USER_NOT_LOGIN = -4;
        //输入参数错误
        int INPUT_KEY_ERROR = -5;
        //无权限访问
        int NOT_PERMISSION = -6;
        //接口未实现
        int INTERFACE_NOT_REALIZE = -7;
        //对象不存在
        int OBJECT_NO_EXIST = -8;
        //对象已存在
        int OBJECT_EXIST = -9;
        //服务器内部错误
        int SERVICE_IN_ERROR = -99;
        //成功
        int SUCCEED = 0;

    }
}

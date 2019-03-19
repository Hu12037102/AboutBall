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
    String PAGE_NUM = "pageNum";
    String PAGE_SIZE = "pageSize";
    String OFFICIAL = "official";
    String DATE = "date";
    String TYPE_ID = "typeId";
    String TOKEN = "Authorization";
    //比赛未开始
    int GAME_STATUS_NO_START = 1;
    //比赛进行中
    int GAME_STATUS_STARTING = 2;
    //比赛已结束
    int GAME_STATUS_FINISH = 3;
    //比赛已取消
    int GAME_STATUS_CANCEL = 4;
    //默认页面
    int DEFAULT_PAGE_NUM = 1;
    //默认页面长度
    int DEFAULT_PAGE_SIZE = 10;

    //1：官方赛事； 2: 民间赛事；
    int TYPE_GAME_OFFICIAL = 1;
    int TYPE_GAME_FOLK = 2;

    String HAS_REFEREE = "1";
    String HAS_RIVAL = "2";
}

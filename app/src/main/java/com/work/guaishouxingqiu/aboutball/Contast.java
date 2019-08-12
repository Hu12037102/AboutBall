package com.work.guaishouxingqiu.aboutball;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 11:12
 * 更新时间: 2019/3/8 11:12
 * 描述: 一些常量
 */
public interface Contast {

    String SPLIT_IMAGE = ",";
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
    String NEW_ID = "newsId";
    String MATCH_ID = "matchId";
    String CODE = "code";
    String APPID = "appid";
    String SECRET = "secret";
    String GRANT_TYPE = "grant_type";
    String ACCESS_TOKEN = "access_token";
    String OPENID = "openid";
    String LANG = "lang";
    String PHONE_MODEL = "phoneModel";
    String VERSION = "version";
    String ANDROID = "android";
    String KEYWORD = "keyword";
    String ACTION_ID = "actionId";
    String STATS = "state";
    String STADIUM_ID = "stadiumId";
    String AREA_ID = "areaId";
    String ORDER_ID = "orderId";
    String TEAM_ID = "teamId";
    String PLAYER_ID = "playerId";
    String ORDER_STATE = "orderState";
    String TOKEN = "Authorization";
    String SEX = "gender";
    String OFFER_ID = "offerId";
    String TRADE_TYPE = "tradeType";
    String FLAG = "flag";
    String REFEREE_ID = "refereeId";
    String AGREE_ID = "agreeId";
    String SHARE_ID = "shareId";
    String SHARE_TYPE = "shareType";
    String OUTS_ID = "outsId";
    String TWEET_ID = "tweetId";
    String CONCERN_ID = "concernId";
    String TIPS_REASON = "tipsReason";
    String TOPIC_ID = "topicId";
    String GAME_ID = "gameId";
    String GROUP_ID = "groupId";
    String USER_ID = "userId";
    String NOTICE_TYPE = "noticeType";
    //默认经度
    double DEFAULT_LONGITUDE = 115.770000;
    //默认纬度
    double DEFAULT_LATITUDE = 23.912030;

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
    //性别类型0：其他1：男；2：女
    int SEX_TYPE_MAN = 1;
    int SEX_TYPE_WOMAN = 2;
    int SEX_OTHER = 0;
    //队长
    int LEADER = 1;
    //队员
    int MEMBER = 0;

    //奖品带兑换
    int PRIZE_WAIT = 0;
    //奖品已兑换
    int PRIZE_HAS_CHANGE = 1;
    //奖品已过期
    int PRIZE_TIME_OUT = 2;
    int HAS_REFEREE = 0;//却裁判
    int HAS_NO_REFEREE = 1;//不缺裁判
    int HAS_RIVAL = 0;//缺对手
    int HAS_NO_RIVAL = 1;//不缺对手

    String VIDEO_RECOMMENDED_TYPE = "3";


    String KEY_PREVIEW_DATA_MEDIA = "key_preview_data_media";
    String KEY_PREVIEW_CHECK_MEDIA = "key_preview_check_media";
    String KEY_PREVIEW_POSITION = "key_preview_position";
    String KEY_CLEAR_MEDIA_DATA = "key_clear_media_data";
    int REQUEST_CODE_MEDIA_TO_PREVIEW = 101;
    int MAX_CHOOSE_MEDIA = 9;
    String KEY_OPEN_MEDIA = "key_open_media";

    String KEY_REQUEST_MEDIA_DATA = "key_request_media_data";
    int CODE_REQUEST_MEDIA = 1011;
    int CODE_RESULT_MEDIA = 1012;
    int CODE_REQUEST_PRIVIEW_VIDEO = 1013;
    String ALL_FILE = "全部文件";
    String ALL_VIDEO = "全部视频";
    int REQUEST_CAMERA_CODE = 2000;

    interface Topic {
        int RECOMMENDED = 0;//话题推荐
        int NEW = 1;//话题最新
    }

    interface SecretKey {
        String WEICHAT_APP_ID = "wx41e9ee2ffa7b327e";
        String WEICHAT_APP_SECRET = "40a967eb50ce478246b63d8d78525893";
        String WEI_CHAT_BUSINESS_ID = "1524077161";//微信支付商户ID
        String Bugtag_ID = "96481d2c6099fa3e827b8c04d036d566";
        String UMENG_ID = "5c773884b465f59b44000a29";

    }

    interface VenueType {
        //创建发起约球
        int CREATE_BALL = 1;
        int FORMER_BALL = 2;//选择已发起的约球
        int NEW_BALL = 3;//选择新发起的约球
    }

    interface LoginStatus {
        int REGISTER = 1;
        int FORGET_PASSWORD = 2;
        int LOGIN_BING_PHONE = 1;
        int LOGIN_UNBING_PHONE = 0;
        int BAND_PHONE = 1;//绑定手机号
        int UPDATE_PHONE = 2;//更换手机号
    }

    interface DynamicType {
        int IMAGE = 1;//图片
        int VIDEO_PORTRAIT = 2;//视频竖屏
        int VIDEO_LANDSCAPE = 3;//视频横屏
    }

    //订单状态
    interface OrderStatus {
        //全部
        int ALL = 0;
        //待付款
        int WAIT_PAY = 1;
        //已取消
        int CANCELED = 2;
        //待使用
        int WAIT_USER = 3;
        //待评价
        int WAIT_EVALUATE = 4;
        //已完成
        int COMPLETING = 5;
        //退款中
        int REFUNDING = 6;
        //已退款
        int REFUNDED = 7;
        int ALL_REFUNDED = 10;

        int ORDER_STATUS_NOT_CANCEL = 2;//不能取消订单状态
    }

    interface PayOrderFlag {
        //0：我的订单页面；1：包场订单；2：发起约球订单；3：待约球订单
        int PAY_MY_ORDER = 0;
        int PAY_VENUE_BOOK = 1;
        int PAY_LAUNCHER_ORDER = 2;
        int PAY_WAIT_ORDER = 3;
    }

    /**
     * 审核裁判状态
     * 0：审核中;1：已通过（用户确认);2：未通过;3：已通过（后台审核）
     */
    interface RefereeStatus {
        int REFEREE_0 = 0;
        int REFEREE_1 = 1;
        int REFEREE_2 = 2;
        int REFEREE_3 = 3;
    }

    interface AboutBallFlag {
        int PUBLISH = 1;//我发布的
        int PARTICIPATION = 2;//我参与的
    }

    interface InputEvaluationType {
        int REFEREE = 1;//评论裁判
        int OPPONENT = 2;//评论对手
        int TEAMMATE = 3;//队友评论
        int MY_REFEREE = 0;//我的裁判评论
    }


}

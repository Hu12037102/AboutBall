package com.ifeell.app.aboutball;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 13:54
 * 更新时间: 2019/3/8 13:54
 * 描述: 接口API
 */
public interface IApiService {
    //登录
    String LOGIN = "api/account/login";
    //发送验证码
    String VERIFICATION_CODE = "api/commons/sms/verificationCode";
    //获取用户信息
    String USER_ACCOUNT = "api/account";

    //获取账户用户信息
    String USER_ACCOUNT_INFO = "api/accountManagement/getAccountInfo";
    //用户注册
    String REGISTER = "api/account/register";
    //忘记密码
    String FORGET_PASSWORD = "api/account/resetPassword";
    //判断验证码是否有效
    String JUDGE_MESSAGE_CODE = "api/account/verificationCodeIsPass";
    //首页-推荐头部信息列表
    String RECOMMEND_HEAD = "api/home/getHomePage";
    //资讯列表
    String NEWS_LIST = "api/news/getNewsList";
    //首页信息相关接口
    String GET_HOME_TAB = "api/home/getHomeLabel";
    String GET_NEWS_CONTENT = "api/news/getNewsDetail";
    String GET_NEWS_MESSAGE_CONTENT_LIST = "api/news/getNewsComment";
    String POST_SEND_NEWS_MESSAGE_CONTENT = "api/news/newsCommented";
    String GET_SEARCH_NEWS_DATA = "api/news/searchNews";
    String GET_TICKET_MALL_LIST = "api/mall/getTicketSpuList";
    String GET_GAME_TICKET_DETAILS = "api/mall/getMatchTicketSpuDetail";
    String GET_DOOR_TICKET_DETAILS = "api/mall/getSwimTicketSkuDetail";

    //赛事-列表
    String GAME_LIST = "api/match/getMatchList";

    String GAME_REFRESH_LIST = "api/match/pullRefresh";
    String GAME_MORE_LIST = "api/match/upRefresh";
    String GET_MATCH_SIMPLE = "api/match/getMatchSimple";
    String GET_MATCH_RESULT_DATA = "api/match/getMatchOuts";
    String GET_MATCH_COMMENT_LIST = "api/match/getMatchCommentList";
    String POST_SEND_COMMENT_CONTENT = "api/match/commented";
    String GET_MATCH_DETAILS = "api/match/getMatchDataDetail";
    String GET_COLLECTION_LIST = "api/match/getMatchPhoto";
    String GET_COLLECTION_VIDEO_LIST = "api/match/getMatchVideo";
    String GET_MATCH_RESULT_DETAILS = "api/match/getMatchSituationDetail";
    String GET_MATCH_INTO_FILTRATE = "api/matchStatistic/getMatchGameList";
    String GET_MATCH_INFO_GROUP = "api/matchStatistic/getMatchGameGroupList";
    String GET_MATCH_INFO_SCOREBOARD = "api/matchStatistic/getMatchScoreboard";
    String GET_MATCH_INFO_OTHER_LIST = "api/matchStatistic/getMatchGoalScoreboard";
    String GET_MATCH_LOOK_BACK_DETAILS = "api/match/getMatchReview";
    String GET_MATCH_REVIEW_DATA = "api/match/getMatchReviewList";
    String GET_MATCH_SCHEDULE_DATA = "api/match/getMatchSchedule";
    //场馆球类类别
    String VENUE_TYPE_LIST = "api/stadium/getType";
    //分页获取场馆列表
    String VENUE_LIST_DATA = "api/stadium/getStadiumList";
    //分页获取约球列表
    String ABOUT_BALL_LIST_DATA = "api/agreeBall/getAgreeBallList";
    //获取场馆详情
    String GET_VENUE_DETAILS = "api/stadium/getStadiumDetail";
    //获取推荐场馆
    String GET_VENUE_RECOMMEND_DATA = "api/stadium/getRecommendStadium";
    //获取场馆场次包场
    String GET_VENUE_BOOK = "api/stadium/getAreaCalendar";
    //获取待约球的场次
    String GET_VENUE_WAIT_BOOK = "api/stadium/getAgreeCalendar";
    String POST_CREATE_VENUE_ORDER_ID = "api/order/generateOrder";

    //获取订单详情
    String GET_ORDER_DETAILS = "api/order/getOrderDetail";
    //绑定手机订单号
    String GET_BAND_PHONE_PHONE_NUMBER = "api/order/orderBingPhone";
    //裁判列表
    String GET_REFEREE_LIST = "api/referee/getRefereeList";

    //获取社区我关注的动态
    String COMMUNITY_NEW_DATA = "api/tweet/getLastestTweet";
    String COMMUNITY_ATTENTION_DATA = "api/tweet/MyConcernTweet";
    String COMMUNITY_HOT_TOPIC = "api/topic/getHotTopic";
    String COMMUNITY_RECOMMEND_DATA = "api/tweet/getRecommendTweet";
    String COMMUNITY_COMMENT_DATA = "api/tweet/getTweetComment";
    String COMMUNITY_DYNAMIC_COMMENT = "api/tweet/tweetComment";
    String GET_COMMUNITY_DYNAMIC_TOPIC = "api/topic/getTopicList";
    String POST_PUBLISH_DYNAMIC = "api/tweet/releaseTweet";
    String GET_ATTENTION_TWEET = "api/tweet/concernAt";
    String GET_CANCEL_ATTENTION_TWEET = "api/tweet/cancelConcern";
    String GET_DYNAMIC_DIAN_ZAN = "api/tweet/tweetPraise";
    String GET_CANCEL_DYNAMIC_DIAN_ZAN = "api/tweet/cancelPraise";
    String GET_REPORT_COMMUNITY = "api/tweet/tweetTips";
    String GET_DELETE_DYNAMIC = "api/tweet/removeTweet";
    String GET_USER_DYNAMIC = "api/tweet/getMyTweetHomePage";
    String GET_BAND_THREE_ACCOUNT = "api/accountManagement/bandingThreeAccount";
    String GET_RECOMMEND_TOPIC = "api/topic/getRecommendTweetForTopic";
    String GET_NEW_TOPIC = "api/topic/getTweetForTopic";
    String GET_FANS_AND_FOCUS = "api/tweet/getMyFansAndFollow";
    String GET_SHARE_COMMUNITY_DYNAMIC = "api/tweet/tweetShare";
    String GET_SURE_USER_ORDER = "api/order/confirmOrderUsed";
    String GET_RED_POINT = "api/notice/getNoticeCenterRedPoint";
    String GET_MY_MESSAGE_LIST = "api/notice/getUserNoticeCenterList";
    String GET_DYNAMIC_NOTIFICATION_LIST = "api/notice/getSocialNoticeList";
    String GET_SYSTEM_NOTIFICATION_LIST = "api/notice/getSysTemNoticeList";
    String GET_CLEAR_RED_POINT = "api/notice/saveNoticeCenterReadByType";
    String GET_SETTING_PASSWORD = "api/accountManagement/settingPassword";
    String GET_CAN_USER_STADIUM_LIST = "api/stadium/getStadiumListForAgree";
    String GET_NEWS_SELLING_POINTS = "api/news/saveNewsShareCount";
    String GET_SURE_ORDER_DIALOG = "api/mall/userChoseSpuSpecifications";
    String GET_CHECK_OUT_ORDER_STATUS = "api/transaction/getCurrentOrderStatus";
    //获取微信登录token
    String GET_WEICHAT_TOKEN = "sns/oauth2/access_token";
    //获取登录成功微信用户信息
    String GET_WEICHAT_USET_INFO = "sns/userinfo";
    //传第三方数据给后台
    String OTHER_WEICHAT_LOGIN = "api/account/threeLogin";

    String UPDATE_APK_INFO = "api/commons/isLastestVersion";
    //获取我的奖品
    String GET_MY_PRIZE = "api/prize/getMyPrize";

    //添加新地址
    String POST_EDIT_ADDRESS = "api/prize/editAddressForPrize";
    //获取我的地址
    String GET_MY_ADDRESS = "api/prize/getMyPrizeAddress";
    String GET_HAS_ADDRESS = "api/prize/hasAddress";
    //更绑定手机号码
    String POST_BAND_PHONE_NUMBER = "api/accountManagement/bandingPhone";
    String POST_UPDATE_PASSWORD = "api/account/modifyPassword";
    String GET_MY_BALL_TEAM = "api/myTeam/getMyTeam";
    String POST_LAUNCHER_BALL = "api/agreeBall/initiateAgreeBall";
    String GET_OSS_TOKEN = "api/commons/static/ossCredentials";
    String GET_NOT_BOOK = "api/agreeBall/agreeBallWithoutCalendar";

    String POST_MANAGE_TEAM = "api/myTeam/createTeam";
    String GET_BALL_TEAM_DETAILS = "api/myTeam/getTeamInfo";
    String GET_BALL_TEAM_MEMBER_DETAILS = "api/myTeam/getTeamPlayer";
    String POST_EDIT_TEAM_INFO = "api/myTeam/editTeamInfo";
    String GET_EXIT_TEAM_BALL = "api/myTeam/dropOutTeam";
    String GET_DISSOLUTION_BALL_TEAM = "api/myTeam/dissolveTeam";
    String POST_EDIT_PLAYER_INFO = "api/myTeam/editPlayerInfo";
    String GET_DELETE_MEMBER = "api/myTeam/leaveTeam";
    String GET_REFEREE_LEVEL_LIST = "api/referee/getCertificateLevel";
    String POST_APPLY_REFEREE_CERTIFICATE = "api/referee/applyForReferee";
    String GET_JUDGE_REFEREE_STATUS = "api/referee/isReferee";
    String GET_SURE_REFEREE_STATUS = "api/referee/refereeConfirm";
    String GET_MY_REFEREE_RECORD = "api/referee/myRefereeRecord";
    String GET_REFEREE_RECORD = "api/referee/getRefereeRecord";
    String GET_REFEREE_DETAILS = "api/referee/getRefereeDetail";
    String POST_FEEDBACK = "api/commons/feedback";
    String GET_MY_ORDER = "api/order/getMyOrder";
    String GET_CANCEL_ORDER = "api/order/cancelOrder";
    String POST_EVALUATE_ORDER = "api/order/commentOrder";
    String GET_ABOUT_BALL_DETAILS = "api/agreeBall/getAgreeBallDetail";
    String GET_PLAY_REFEREE = "api/referee/serveAsReferee";
    String GET_PAY_WEI_CHAT_SING = "api/order/weChatPay";
    String GET_PAY_TIAKETS_WEI_CHAT_SING = "api/pay/weChatPay";
    String GET_MY_ABOUT_BALL = "api/agreeBall/MyAgreeBall";
    String GET_REFUND_CAUSE = "api/order/getOrderRefundReason";
    String POST_REFUND_ORDER = "api/order/refundOrder";
    String GET_GOOD_CHECK_REFUND = "api/transaction/getRefundProgress";
    String GET_ORDER_CHECK_REFUND = "api/order/weChatRefundQuery";
    String GET_REFEREE_EVALUATION = "api/referee/getRefereeComment";
    String GET_TEAM_EVALUATION = "api/myTeam/getTeamComment";
    String POST_EVALUATE_OPPONENT = "api/myTeam/commentOpponent";
    String POST_EVALUATE_REFEREE = "api/referee/commentReferee";
    String POST_INVITATION_BALL = "api/agreeBall/applyBall";
    String GET_CANCEL_ABOUT_BALL = "api/agreeBall/cancelAgreeBall";
    String GET_MY_REFEREE_EVALUATION = "api/referee/myRefereeComment";
    String GET_JOIN_TEAM = "api/myTeam/confirmJoin";
    String GET_VENUE_EVALUATE = "api/stadium/getCommentForArea";
    String GET_REFEREE_MATCH_RECORD = "api/referee/getAgreeSituation";
    String POST_SAVE_REFEREE_RECORD = "api/referee/insertSituation";
    String POST_EDIT_REFEREE_RECORD = "api/referee/editSituation";
    String POST_NOTE_SITUATION = "api/referee/noteSituation";
    String POST_UPDATE_PHONE_NUMBER = "api/accountManagement/changePhone";
    String GET_REFEREE_RECORD_DETAILS = "api/referee/getAgreeOuts";
    String GET_DELETE_REFEREE_MATCH_RECORD = "api/referee/removeAgreeOuts";
    String GET_MY_TEAM_MATCH_RECORD = "api/myTeam/getMyAgreeOuts";
    String GET_VERSION_HISTORY_DETAILS = "api/commons/getVersionExplain";
    String GET_MY_ATTENTION_DATA = "api/tweet/getMyFollowList";
    String GET_MY_FANS_DATA = "api/tweet/getMyFansList";
    String POST_EDIT_ABOUT_BALL = "api/agreeBall/editAgreeBall";
    String GET_USER_CONFIRM_ORDER = "api/mall/userSpecificationsConfirm";
    String GET_MY_TICKETS_LIST = "api/ticket/getCommonTicketList";
    String POST_ORDER_DETAILS = "api/transaction/CommonCreateOrder";
    String GET_MY_GOOD_LIST = "api/transaction/getCommonUserOrderList";
    String GET_MY_GOOD_DETAILS = "api/transaction/getOrderDetail";
    String GET_GOOD_REFUND_DETAILS = "api/transaction/getApprovalRefundInfo";
    String GET_APPLY_REFUND = "api/transaction/confirmApprovalRefund";
    String GET_CHECK_OUT_GOOD_STATUS = "api/transaction/userCreateOrderBeforeCheckStore";

    interface H5 {
        // String USER_AGREEMENT = "https://ifi.bmece.com/getAgreement";
        //用户协议
        String USER_AGREEMENT = "https://www.ifeell.com.cn/protocol";
        //资讯详情分享，后缀加上id;
        String SHARE_NEWS_DETAILS = "http://ifeell.com.cn/share/index.html?id=";
        //分享球队链接，后面跟上teamId
        //https://ifi.bmece.com/download?teamId=1&typeId=1
        String SHARE_BALL_TEAM = "https://www.ifeell.com.cn/download";
        //APK下载路径
        String DOWNLOAD_APK = "https://www.ifeell.com.cn/download";
        //动态详情
        String DYNAMIC_DETAILS = "https://www.ifeell.com.cn/community";
        String ABOUT_BALL_DETAILS = "https://www.ifeell.com.cn/aboutball";
        //抽奖
        String PRIZE = "https://www.ifeell.com.cn/prize";
    }


    interface TypeId {
        //球队邀请
        int OPEN_BALL_INVITE = 1;
        //约球详情
        int ABOUT_DETAILS = 2;
        //视频分享
        int OPEN_GAME_DETAILS_VIDEO = 3;
        //动态详情
        int DYNAMIC_DETAILS = 4;
    }

}

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

    //获取账户用户信息
    String USER_ACCOUNT_INFO = "/api/accountManagement/getAccountInfo";
    //用户注册
    String REGISTER = "/api/account/register";
    //判断验证码是否有效
    String JUDGE_MESSAGE_CODE = "/api/account/verificationCodeIsPass";
    //首页-推荐头部信息列表
    String RECOMMEND_HEAD = "/api/home/getHomePage";
    //资讯列表
    String NEWS_LIST = "/api/news/getNewsList";
    //首页信息相关接口
    String GET_HOME_TAB = "/api/home/getHomeLabel";
    String GET_NEWS_CONTENT = "/api/news/getNewsDetail";
    String GET_NEWS_MESSAGE_CONTENT_LIST = "/api/news/getNewsComment";
    String POST_SEND_NEWS_MESSAGE_CONTENT = "/api/news/newsCommented";
    String GET_SEARCH_NEWS_DATA = "/api/news/searchNews";

    //赛事-列表
    String GAME_LIST = "/api/match/getMatchList";

    String GAME_REFRESH_LIST = "/api/match/pullRefresh";
    String GAME_MORE_LIST = "/api/match/upRefresh";
    String GET_MATCH_SIMPLE = "/api/match/getMatchSimple";
    String GET_MATCH_RESULT_DATA = "/api/match/getMatchOuts";
    String GET_MATCH_COMMENT_LIST = "/api/match/getMatchCommentList";
    String POST_SEND_COMMENT_CONTENT = "/api/match/commented";
    String GET_MATCH_DETAILS = "/api/match/getMatchDetail";
    String GET_COLLECTION_LIST = "/api/match/getMatchPhoto";

    //场馆球类类别
    String VENUE_TYPE_LIST = "/api/stadium/getType";
    //分页获取场馆列表
    String VENUE_LIST_DATA = "/api/stadium/getStadiumList";
    //分页获取约球列表
    String ABOUT_BALL_LIST_DATA = "/api/agreeBall/getAgreeBallList";
    //获取场馆详情
    String GET_VENUE_DETAILS="/api/stadium/getStadiumDetail";
    //获取社区我关注的动态
    String COMMUNITY_ATTENTION_DATA = "/api/tweet/getLastestTweet";

    //获取微信登录token
    String GET_WEICHAT_TOKEN = "/sns/oauth2/access_token";
    //获取登录成功微信用户信息
    String GET_WEICHAT_USET_INFO = "/sns/userinfo";
    //传第三方数据给后台
    String OTHER_WEICHAT_LOGIN = "/api/account/threeLogin";

    String UPDATE_APK_INFO = "/api/commons/isLastestVersion";
    //获取我的奖品
    String GET_MY_PRIZE = "/api/prize/getMyPrize";

    //添加新地址
    String POST_EDIT_ADDRESS = "/api/prize/editAddressForPrize";
    //获取我的地址
    String GET_MY_ADDRESS = "/api/prize/getMyPrizeAddress";
    String GET_HAS_ADDRESS = "/api/prize/hasAddress";
    //更绑定手机号码
    String POST_UPDATE_PHONE_NUMBER ="/api/accountManagement/bandingPhone";
    String POST_UPDATE_PASSWORD="/api/account/modifyPassword";

    interface H5 {
        String USER_AGREEMENT = "https://ifi.bmece.com/getAgreement";
        //资讯详情分享，后缀加上id;
        String SHARE_NEWS_DETAILS = "http://ifeell.com.cn/share/index.html?id=";
    }
}

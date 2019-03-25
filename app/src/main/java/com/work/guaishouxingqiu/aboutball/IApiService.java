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
    //首页-推荐头部信息列表
    String RECOMMEND_HEAD = "/api/home/getHomePage";
    //资讯列表
    String NEWS_LIST = "/api/news/getNewsList";
    //首页信息相关接口
    String GET_HOME_TAB = "/api/home/getHomeLabel";
    String GET_NEWS_CONTENT = "/api/news/getNewsDetail";
    String GET_NEWS_MESSAGE_CONTENT_LIST = "/api/news/getNewsComment";
    String POST_SEND_NEWS_MESSAGE_CONTENT = "/api/news/newsCommented";

    //赛事-列表
    String GAME_LIST = "/api/match/getMatchList";

    String GAME_REFRESH_LIST = "/api/match/pullRefresh";
    String GAME_MORE_LIST = "/api/match/upRefresh";
    String GET_MATCH_SIMPLE = "/api/match/getMatchSimple";

    //场馆球类类别
    String VENUE_TYPE_LIST = "/api/stadium/getType";
    //分页获取场馆列表
    String VENUE_LIST_DATA = "/api/stadium/getStadiumList";
    //分页获取约球列表
    String ABOUT_BALL_LIST_DATA = "/api/agreeBall/getAgreeBallList";
    //获取社区我关注的动态
    String COMMUNITY_ATTENTION_DATA = "/api/tweet/getLastestTweet";


}

package com.work.guaishouxingqiu.aboutball.router;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 13:31
 * 更新时间: 2019/3/7 13:31
 * 描述:路由跳转的配置
 */
public interface ARouterConfig {
    interface Path {
        String ACTIVITY_MAIN = "/activity/main";
        String ACTIVITY_LOGIN = "/activity/login";
        String ACTIVITY_REGISTER = "/activity/register";
        String ACTIVITY_MY_DETAILS = "/activity/my/details";
        String ACTIVITY_ALTER_NAME = "/activity/alter/name";
        String ACTIVITY_NEW_DETAILS = "/activity/new/details";
        String ACTIVITY_GAME_DETAILS = "/activity/game/details";
        String ACTIVITY_VENUE_DETAILS = "/activity/venue/details";
        String ACTIVITY_GAME_VIDEO = "/activity/game/video";
        String ACTIVITY_SETTING = "/activity/setting";

        String FRAGMENT_GAME = "/fragment/game";
        String FRAGMENT_GAME_OFFICIAL = "/fragment/game/official";
        String FRAGMENT_GAME_FOLK = "/fragment/game/folk";
        String FRAGMENT_GAME_TEACH = "/fragment/game/teach";
        String FRAGMENT_HOME = "/fragment/home";
        String FRAGMENT_HOT = "/fragment/hot";
        String FRAGMENT_HIGHLIGHTS = "/fragment/highlights";
        String FRAGMENT_RECOMMENDED = "/fragment/recommended ";
        String FRAGMENT_SPECIAL = "/fragment/special";
        String FRAGMENT_VIDEO = "/fragment/video";
        String FRAGMENT_VENUE = "/fragment/venue";
        String FRAGMENT_COMMUNITY = "/fragment/community";
        String FRAGMENT_MY = "/fragment/my";
        String FRAGMENT_VENUE_LIST = "/fragment/venue/list";
        String FRAGMENT_ABOUT_BALL = "/fragment/about/ball";
        String FRAGMENT_COMMUNITY_ATTENTION = "/fragment/community/attention";
        String FRAGMENT_COMMUNITY_RECOMMEND = "/fragment/community/recommend";
        String FRAGMENT_COMMUNITY_NEWS = "/fragment/community/news";
        String FRAGMENT_GAME_RESULT = "/fragment/game/result";
        String FRAGMENT_GAME_DATA = "/fragment/game/data";
        String FRAGMENT_GAME_COMMENT = "/fragment/game/comment";
        String FRAGMENT_GAME_COLLECTION = "/fragment/game/collection";
        String FRAGMENT_REGISTER_PHONE = "/fragment/register/phone";
        String FRAGMENT_REGISTER_CODE = "/fragment/register/code";
        String FRAGMENT_REGISTER_PASSWORD = "/fragment/register/password";

    }

    interface Key {
        String TAB_TYPE_ID = "tabTypeId";
        String NEW_DETAILS_ID = "newDetailsId";
        String GAME_ID = "gameId";
        String GAME_DETAILS_BEAN = "gameDetailsBean";
    }

}

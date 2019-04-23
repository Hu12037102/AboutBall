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
        String ACTIVITY_EDIT_MY_ADDRESS = "/activity/edit/my/address";
        String ACTIVITY_MY_PRIZE = "/activity/my/prize";
        String ACTIVITY_NEWS_SEARCH = "/activity/news/search";
        String ACTIVITY_WEB_DATA = "/activity/web/data";
        String ACTIVITY_USER_AGREEMENT = "/activity/user/agreement";
        String ACTIVITY_SHARE_FRIEND = "/activity/share_friend";
        String ACTIVITY_UPDATE_PHONE = "/activity/update/phone";
        String ACTIVITY_UPDATE_PASSWORD = "/activity/update/password";
        String ACTIVITY_VENUE_BOOKING = "/activity/venue/booking";
        String ACTIVITY_VENUE_ORDER_DETAILS="/activity/venue/order_details";
        String ACTIVITY_LAUNCHER_BALL="/activity/launcher/ball";

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
        String FRAGMENT_BASE_PRIZE = "/fragment/base/prize";

    }

    interface Key {
        String TAB_TYPE_ID = "tabTypeId";
        String NEW_DETAILS_ID = "newDetailsId";
        String GAME_ID = "gameId";
        String GAME_DETAILS_BEAN = "gameDetailsBean";
        String ACTION_ID = "actionId";
        String URL = "url";
        //场馆Id
        String STADIUM_ID = "stadiumId";
        String KEY_STATUS = "status";
        String TYPE_ID = "typeId";
        String PARCELABLE = "parcelable";
        String ARRAY_LIST_PARCELABLE = "array_list_parcelable";
        String POSITION="position";
        String AREA_ID="areaId";
        String ORDER_ID="orderId";
        String CALENDAR_ID="calendarId";
    }

}

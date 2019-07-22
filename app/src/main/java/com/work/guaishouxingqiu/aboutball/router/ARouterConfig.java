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
        String ACTIVITY_WAIT_PAY_ORDER_DETAILS = "/activity/wait/pay/order/details";
        String ACTIVITY_LAUNCHER_BALL = "/activity/launcher/ball";
        String ACTIVITY_MY_BALL_TEAM = "/activity/my/ball/team";
        String ACTIVITY_MANAGE_BALL_TEAM = "/activity/manage/ball/team";
        String ACTIVITY_BALL_TEAM_DETAILS = "/activity/ball/team/details";
        String ACTIVITY_BALL_TEAM_MY_DETAILS = "/activity/ball/team/my/details";
        String ACTIVITY_WELCOME = "/activity/welcome";
        String ACTIVITY_GUIDE = "/activity/guide";
        String ACTIVITY_ABOUT_WE = "/activity/about/we";
        String ACTIVITY_SELECTOR_BALL_TEAM = "/activity/selector/ball/team";
        String ACTIVITY_APPLY_REFEREE = "/activity/apply/referee";
        String ACTIVITY_APPLY_BECOME_REFEREE = "/activity/apply/become/referee";
        String ACTIVITY_REFEREE_STATUS = "/activity/referee/status";
        String ACTIVITY_MY_REFEREE_RECORD = "/activity/my/referee/record";
        String ACTIVITY_FEEDBACK = "/activity/feedback";
        String ACTIVITY_MY_ORDER = "/activity/my/order";
        String ACTIVITY_ORDER_COMPLETE_EVALUATE_CANCEL = "/activity/order/complete/evaluate/cancel";
        String ACTIVITY_ORDER_EVALUATE = "/activity/order/evaluate";
        String ACTIVITY_WAIT_USER_ORDER_DETAILS = "/activity/wait/user/order/details";
        String ACTIVITY_ABOUT_BALL_DETAILS = "/activity/about/ball/details";
        String ACTIVITY_BALL_TEAM_DETAILS_VENUE = "/activity/ball/team/details/venue";
        String ACTIVITY_INVITATION_BALL = "/activity/invitation/ball";
        String ACTIVITY_PAY_SUCCEED = "/activity/pay/succeed";
        String ACTIVITY_MY_ABOUT_BALL = "/activity/my/about/ball";
        String ACTIVITY_REFUND = "/activity/refund";
        String ACTIVITY_ORDER_REFUND_DETAILS = "/activity/order/refund/details";
        String ACTIVITY_ABOUT_RULE = "/activity/about/rule";
        String ACTIVITY_INPUT_EVALUATION = "/activity/input/evaluation";
        String ACTIVITY_POST_EVALUATION = "/activity/post/evaluation";
        String ACTIVITY_REFEREE_DETAILS = "/activity/referee/details";
        String ACTIVITY_VENUE_EVALUATE = "/activity/venue/evaluate";
        String ACTIVITY_MATCH_REFEREE_RESULT = "/activity/match/referee/result";
        String ACTIVITY_ADD_BALL_PEOPLE_RECORD = "/activity/add/ball/people/record";
        String ACTIVITY_MAP = "/activity/map";
        String ACTIVITY_SCHEDULE = "/activity/schedule";
        String ACTIVITY_COMMUNITY_DETAILS = "/activity/community/details";
        String ACTIVITY_DYNAMIC_EDIT = "/activity/dynamic/edit";
        String ACTIVITY_SELECTOR_TOPIC = "/activity/selector/topic";
        String ACTIVITY_COMMUNITY_REPORT = "/activity/community/report";
        String ACTIVITY_IMAGE_PREVIEW = "/activity/preview/image";
        String ACTIVITY_TOPIC_DYNAMICS = "/activity/topic/dynamics";
        String ACTIVITY_MY_DYNAMIC = "/activity/my/dynamic";
        String ACTIVITY_GAME_INFO = "/activity/game/info";
        String ACTIVITY_MATCH_REVIEW = "/activity/match/review";
        String ACTIVITY_TEAM_MATCH_RESULT = "/activity/team/match/result";
        String ACTIVITY_MY_TEAM_EVALUATE = "/activity/my/team/evaluate";
        String ACTIVITY_VERSION_HISTORY = "/activity/version/history";
        String ACTIVITY_CAMERA_VIDEO = "/activity/camera/video";

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
        String FRAGMENT_BALL_TEAM_DETAILS = "/fragment/ball/team/details";
        String FRAGMENT_BALL_TEAM_MEMBER = "/fragment/ball/team/member";
        String FRAGMENT_MY_REFEREE_EVALUATE = "/fragment/my/referee/evaluate";
        String FRAGMENT_MY_REFEREE_RECORD = "/fragment/my/referee/record";
        String FRAGMENT_DRILL = "/fragment/drill";
        String FRAGMENT_MY_ORDER = "/fragment/my/order";
        String FRAGMENT_MY_ABOUT_BALL = "/fragment/my/about/ball";
        String FRAGMENT_POST_EVALUATION = "/fragment/post/evaluation";
        String FRAGMENT_TOPIC_DYNAMICS = "/fragment/topic/dynamics";
        String FRAGMENT_GAME_LOOK_BACK = "/fragment/game/look/back";
        String FRAGMENT_POST_ALL_EVALUATION = "/fragment/post/all/evaluation";


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
        String PARCELABLE_EDIT = "parcelable_edit";
        String ARRAY_LIST_PARCELABLE = "array_list_parcelable";
        String ARRAY_LIST_STRING = "array_list_string";
        String POSITION = "position";
        String AREA_ID = "areaId";
        String ORDER_ID = "orderId";
        String CALENDAR_ID = "calendarId";
        String TEAM_ID = "teamId";
        String OPPONENT_ID = "opponentId";
        String PLAYER_ID = "playerId";
        String REFEREE_STATUS = "refereeStatus";
        String ORDER_STATUS = "orderStatus";
        String ORDER_FLAG = "orderFlag";
        String ORDER_DETAILS = "orderDetails";
        String VENUE_NAME = "venueName";
        String TARGET_DATE = "targetDate";
        String TARGET_SITE = "targetSite";
        String TEAM_STATUS = "teamStatus";
        String OFFER_ID = "offerId";
        String ABOUT_BALL_FLAG = "aboutBallFlag";
        String MONEY = "money";
        String LOGIN_STATUS = "loginStatus";
        String INPUT_EVALUATION_FLAG = "inputEvaluationFlag";
        String REFEREE_ID = "refereeId";
        String ID = "id";
        String COUNT = "count";
        String AGREE_ID = "agreeId";
        String SHARE_ID = "shareId" /*"shareId"*/;
        String SHARE_TYPE = "shareType"/*shareType*/;
        String SHIRT_COLOR = "shirtColor";
        String LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String POSITION_NAME = "positionName";
        String HOST_TEAM_ID = "hostTeamId";
        String GUEST_TEAM_ID = "guestTeamId";
        String HOST_TEAM_NAME = "hostTeamName";
        String GUEST_TEAM_NAME = "guestTeamName";
        String IS_ADD = "isAdd";
        String BAND_PHONE_STATUS = "bandPhoneStatus";
        String SIGN_CODE = "signCode";
        String TWEET_ID = "tweetId";
        String DELETE = "delete";
        String TOPIC_STATUS = "topicStatus";
        String TOPIC_ID = "topicId";
        String CAMERA_VIDEO_PATH="cameraVideoPath";
        String IS_CAMERA_VIDEO="cameraIsVideo";
        String HAS_CAMERA_MEDIA ="hasImage";
    }

}

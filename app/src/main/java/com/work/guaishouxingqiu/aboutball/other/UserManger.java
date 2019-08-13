package com.work.guaishouxingqiu.aboutball.other;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:49
 * 更新时间: 2019/3/6 16:49
 * 描述:用戶管理界面
 */
public class UserManger {
    private static final String USER_MESSAGE_SP_NAME = "user_message_sp";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_NICK_NAME = "nickName";
    private static final String KEY_USER_INFO_ID = "userInfoId";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HAS_TEAM = "hasTeam";
    private static final String KEY_USER_HEAD_IMAGE = "headerImg";
    private static final String KEY_PASSWORD_STATUS = "passwordStatus";
    private static final String KEY_WECHAT_USER_NAME = "wechatUserName";
    private static final String KEY_WECHAT_OPEN_ID = "weChatOpenId";
    private static final String KEY_FOLLOW_COUNT = "followCount";
    private static final String KEY_FANS_COUNT = "fansCount";
    private static final String KEY_IS_REFEREE = "isReferee";
    private static final String KEY_TOKEN_TEMPORARY = "tokenTemporary";//临时token
    private static final String KEY_REFEREE_LEVEL = "refereeLevel";
    private static final String KEY_JSON_RED_POINT = "keyJsonRedPoint";
    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;
    private static SharedPreferencesHelp mSP;

    private UserManger() {
        mSP = new SharedPreferencesHelp(UserManger.USER_MESSAGE_SP_NAME);
    }

    private static UserManger mUserManger;

    public static UserManger get() {
        synchronized (UserManger.class) {
            if (mUserManger == null) {
                synchronized (UserManger.class) {
                    mUserManger = new UserManger();
                }
            }
        }
        return mUserManger;
    }


    public void putRedPointJson(@NonNull String values) {
        mSP.putObject(KEY_JSON_RED_POINT, values);
    }

    public String getRedPointJson() {
        return mSP.getString(KEY_JSON_RED_POINT);
    }

    public void putTemporaryToken(String tokenTemporary) {
        mSP.putObject(UserManger.KEY_TOKEN_TEMPORARY, tokenTemporary);
    }

    public String getTemporaryToken() {
        return mSP.getString(UserManger.KEY_TOKEN_TEMPORARY);
    }

    public void removeTemporaryToken() {
        mSP.removeValues(UserManger.KEY_TOKEN_TEMPORARY);
    }

    public void putToken(@NonNull String values) {
        mSP.putObject(UserManger.KEY_TOKEN, values);
    }

    public String getToken() {
        return mSP.getString(UserManger.KEY_TOKEN);
    }

    public void putPhone(@NonNull String phoneNumber) {
        mSP.putObject(UserManger.KEY_PHONE, phoneNumber);
    }

    public String getPhone() {
        return mSP.getString(UserManger.KEY_PHONE);
    }

    public void putNickName(@NonNull String userName) {
        mSP.putObject(UserManger.KEY_NICK_NAME, userName);
    }

    public String getNickName() {
        return mSP.getString(UserManger.KEY_NICK_NAME);
    }

    public boolean isLogin() {
        return !DataUtils.isEmpty(mSP.getString(KEY_TOKEN));
    }

    public void putFollowCount(int count) {
        mSP.putObject(KEY_FOLLOW_COUNT, count);
    }

    public void putFansCount(int count) {
        mSP.putObject(KEY_FANS_COUNT, count);
    }

    public int getFansCount() {
        return mSP.getInt(KEY_FANS_COUNT);
    }

    public int getFollowCount() {
        return mSP.getInt(KEY_FOLLOW_COUNT);
    }

    public int getRefereeStatus() {
        return mSP.getInt(KEY_IS_REFEREE, -1);
    }

    public void putWeiChatOpenId(String signCode) {
        mSP.putObject(KEY_WECHAT_OPEN_ID, signCode);
    }

    public String getWeiChatOpenId() {
        return mSP.getString(KEY_WECHAT_OPEN_ID);
    }

    public void putUser(UserBean userBean) {
        if (userBean == null) {
            return;
        }
        if (userBean.phone != null) {
            mSP.putObject(UserManger.KEY_PHONE, userBean.phone);
        }
        if (userBean.token != null) {
            mSP.putObject(UserManger.KEY_TOKEN, userBean.token);
        }
        if (userBean.headerImg != null) {
            mSP.putObject(UserManger.KEY_USER_HEAD_IMAGE, userBean.headerImg);
        }
        if (userBean.userInfoId != null) {
            mSP.putObject(UserManger.KEY_USER_INFO_ID, userBean.userInfoId);
        }
        if (userBean.nickName != null) {
            mSP.putObject(UserManger.KEY_NICK_NAME, userBean.nickName);
        }
        if (userBean.userId != null) {
            mSP.putObject(UserManger.KEY_USER_ID, userBean.userId);
        }
        if (userBean.gender != null) {
            mSP.putObject(UserManger.KEY_GENDER, userBean.gender);
        }
        if (userBean.birthday != null) {
            mSP.putObject(UserManger.KEY_BIRTHDAY, userBean.birthday);
        }
        if (userBean.height != null) {
            mSP.putObject(UserManger.KEY_HEIGHT, userBean.height);
        }
        if (userBean.weight != null) {
            mSP.putObject(UserManger.KEY_WEIGHT, userBean.weight);
        }
        if (userBean.hasTeam != null) {
            mSP.putObject(UserManger.KEY_HAS_TEAM, userBean.hasTeam);
        }
        if (userBean.passwordStatus != null) {
            mSP.putObject(UserManger.KEY_PASSWORD_STATUS, userBean.passwordStatus);
        }
        if (userBean.wechatUserName != null) {
            mSP.putObject(UserManger.KEY_WECHAT_USER_NAME, userBean.wechatUserName);
        }
        if (userBean.weChatOpenId != null) {
            mSP.putObject(UserManger.KEY_WECHAT_OPEN_ID, userBean.weChatOpenId);
        }
        mSP.putObject(UserManger.KEY_FOLLOW_COUNT, userBean.followCount);
        mSP.putObject(UserManger.KEY_FANS_COUNT, userBean.fansCount);
        if (userBean.isReferee != null) {
            mSP.putObject(UserManger.KEY_IS_REFEREE, userBean.isReferee);
        }
        if (!DataUtils.isEmpty(userBean.refereeLevel)) {
            mSP.putObject(UserManger.KEY_REFEREE_LEVEL, userBean.refereeLevel);
        }

    }

    public String getRefereeLevel() {
        return mSP.getString(UserManger.KEY_REFEREE_LEVEL);
    }

    public UserBean getUser() {
        UserBean userBean = new UserBean();
        userBean.phone = mSP.getString(UserManger.KEY_PHONE);
        userBean.token = mSP.getString(UserManger.KEY_TOKEN);
        userBean.userInfoId = mSP.getLong(UserManger.KEY_USER_INFO_ID);
        userBean.headerImg = mSP.getString(UserManger.KEY_USER_HEAD_IMAGE);
        userBean.nickName = mSP.getString(UserManger.KEY_NICK_NAME);
        userBean.userId = mSP.getLong(UserManger.KEY_USER_ID);
        userBean.gender = mSP.getInt(UserManger.KEY_GENDER);
        userBean.birthday = mSP.getString(UserManger.KEY_BIRTHDAY);
        userBean.height = mSP.getInt(UserManger.KEY_HEIGHT);
        userBean.weight = mSP.getInt(UserManger.KEY_WEIGHT);
        userBean.hasTeam = mSP.getInt(UserManger.KEY_HAS_TEAM);
        userBean.passwordStatus = mSP.getString(UserManger.KEY_PASSWORD_STATUS);
        userBean.wechatUserName = mSP.getString(UserManger.KEY_WECHAT_USER_NAME);
        userBean.weChatOpenId = mSP.getString(UserManger.KEY_WECHAT_OPEN_ID);
        userBean.followCount = mSP.getInt(UserManger.KEY_FOLLOW_COUNT);
        userBean.fansCount = mSP.getInt(UserManger.KEY_FANS_COUNT);
        userBean.isReferee = mSP.getInt(UserManger.KEY_IS_REFEREE);
        userBean.refereeLevel = mSP.getString(UserManger.KEY_REFEREE_LEVEL);
        return userBean;
    }

    public long getUserId() {
        return mSP.getLong(UserManger.KEY_USER_ID);
    }

    public int getSex() {
        return mSP.getInt(UserManger.KEY_GENDER);
    }

    public void loginOut() {
        mSP.clearData();
        // FileUtils.removeFile(FileUtils.getCacheFile());
    }

    public boolean isWeiChatId() {
        return !DataUtils.isEmpty(mSP.getString(UserManger.KEY_WECHAT_OPEN_ID));
    }

    public String getWeiChatName() {
        return mSP.getString(UserManger.KEY_WECHAT_USER_NAME);
    }

    public void putRefereeStatus(int refereeStatus) {
        mSP.putObject(UserManger.KEY_IS_REFEREE, refereeStatus);
    }
}

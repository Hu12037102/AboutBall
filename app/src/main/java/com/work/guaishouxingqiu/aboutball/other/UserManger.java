package com.work.guaishouxingqiu.aboutball.other;

import android.support.annotation.NonNull;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:49
 * 更新时间: 2019/3/6 16:49
 * 描述:用戶管理界面
 */
public class UserManger {
    private static final String USER_MESSAGE_SP_NAME = "user_message_sp";
    private static final String KEY_AUTHORIZATION = "authorization";
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

    public void putToken(@NonNull String values) {
        mSP.putObject(UserManger.KEY_AUTHORIZATION, values);
    }

    public String  getToken() {
        return mSP.getString(UserManger.KEY_AUTHORIZATION);
    }
}

package com.work.guaishouxingqiu.aboutball.other;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:49
 * 更新时间: 2019/3/6 16:49
 * 描述:用戶管理界面
 */
public class UserManger {
    private UserManger() {
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

}

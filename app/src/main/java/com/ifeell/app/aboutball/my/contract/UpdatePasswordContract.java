package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.login.contract.MessageContract;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePasswordBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:46
 * 更新时间: 2019/4/16 14:46
 * 描述:更新密码契约
 */
public interface UpdatePasswordContract {
    interface View extends MessageContract.View {
        void resultPasswordSucceed(String token);
    }

    interface Presenter extends MessageContract.Presenter {
        void updatePassword(RequestUpdatePasswordBean bean);
    }
}

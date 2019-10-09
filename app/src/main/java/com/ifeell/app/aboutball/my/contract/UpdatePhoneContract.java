package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.login.contract.MessageContract;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePhoneBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 10:47
 * 更新时间: 2019/4/16 10:47
 * 描述: 更新手机号契约
 */
public interface UpdatePhoneContract {
    interface View extends MessageContract.View {
        void updatePhoneSucceed(String phoneNumber);

        void bandThreePhoneSucceed(String token);
    }

    interface Presenter extends MessageContract.Presenter {
        void bandThreePhone(RequestUpdatePhoneBean bean);

        void updatePhone(RequestUpdatePhoneBean bean);
    }
}

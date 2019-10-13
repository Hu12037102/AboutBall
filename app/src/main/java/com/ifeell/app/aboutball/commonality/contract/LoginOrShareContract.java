package com.ifeell.app.aboutball.commonality.contract;

import com.ifeell.app.aboutball.login.bean.ResultThreeLoginBean;
import com.ifeell.app.aboutball.login.contract.MessageContract;
import com.ifeell.app.aboutball.commonality.bean.RequestWeiChatTokenBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:31
 * 更新时间: 2019/4/4 13:31
 * 描述:第三方登录分享契约
 */
public interface LoginOrShareContract {
    interface View extends MessageContract.View {
        void resultOtherLogin(ResultThreeLoginBean bean,String signCode);
        void resultBandOtherAccount(String signCode);
    }

    interface Presenter extends MessageContract.Presenter {
        void getWeiChatToken(RequestWeiChatTokenBean bean);
    }
}

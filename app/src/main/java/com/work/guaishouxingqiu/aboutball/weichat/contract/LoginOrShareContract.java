package com.work.guaishouxingqiu.aboutball.weichat.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.contract.MessageContract;
import com.work.guaishouxingqiu.aboutball.weichat.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.weichat.bean.ResultWeiChatInfo;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:31
 * 更新时间: 2019/4/4 13:31
 * 描述:第三方登录分享契约
 */
public interface LoginOrShareContract {
    interface View extends MessageContract.View {
        void resultOtherLogin(LoginResultBean bean);
    }

    interface Presenter extends MessageContract.Presenter {
        void getWeiChatToken(RequestWeiChatTokenBean bean);

    }
}

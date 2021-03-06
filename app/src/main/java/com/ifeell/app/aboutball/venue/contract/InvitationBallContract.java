package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.RequestInvitationBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 13:21
 * 更新时间: 2019/5/21 13:21
 * 描述:应邀约球契约
 */
public interface InvitationBallContract {
    interface View extends IBaseView {
        void resultOrderId(long result);
        void resultOrderIdNull();
    }

    interface Presenter extends IBasePresenter {
        void createOrder(RequestVenueOrderBean bean);

        void invitationBall(RequestInvitationBallBean requestBean);
    }
}

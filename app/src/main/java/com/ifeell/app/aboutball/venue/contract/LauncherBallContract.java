package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.RequestLauncherBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultRefereeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 11:48
 * 更新时间: 2019/4/23 11:48
 * 描述:发起约球契约
 */
public interface LauncherBallContract {
    interface View extends IBaseView {
        void resultRefereeList(List<ResultRefereeBean> data);
        void launcherBallSucceed(long orderId);

        void resultOrderId(long result);
    }

    interface Presenter extends IBasePresenter {
        void launcherBall(RequestLauncherBallBean requestBean);

        void createOrder(RequestVenueOrderBean bean);
    }
}

package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestLauncherBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueOrderBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;

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
        void launcherBallSucceed();

        void resultOrderId(long result);
    }

    interface Presenter extends IBasePresenter {
        void launcherBall(RequestLauncherBallBean requestBean);

        void createOrder(RequestVenueOrderBean bean);
    }
}

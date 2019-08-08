package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 15:25
 * 更新时间: 2019/5/16 15:25
 * 描述:约球详情契约
 */
public interface AboutBallDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultDetails(ResultAboutBallDetailsBean bean);

        void resultPlayReferee();

        void resultCancelAboutBall(String orderId);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadDetails(long offerId);

        void playReferee(long mOfferId);

        void cancelAboutBall(long mAgreeId);
    }
}

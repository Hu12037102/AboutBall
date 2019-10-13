package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/17 9:49
 * 更新时间: 2019/5/17 9:49
 * 描述:球队详情契约
 */
public interface BallTeamDetailsContract {
    interface View extends IBaseView {
        void resultDetails(ResultBallDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadDetails(long mTeamId);
    }
}

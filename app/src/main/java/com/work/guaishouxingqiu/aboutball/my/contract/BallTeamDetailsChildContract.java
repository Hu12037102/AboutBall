package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:26
 * 更新时间: 2019/4/25 16:26
 * 描述:
 */
public interface BallTeamDetailsChildContract {
    interface View extends IBaseView {
        void resultDetails(ResultBallDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadDetails(long mTeamId);
    }
}

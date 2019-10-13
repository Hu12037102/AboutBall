package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:48
 * 更新时间: 2019/3/18 10:48
 * 描述: 场馆- 约球契约
 */
public interface AboutBallContract {
    interface View extends IBaseView {
        void resultAboutBallData(List<ResultAboutBallBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}

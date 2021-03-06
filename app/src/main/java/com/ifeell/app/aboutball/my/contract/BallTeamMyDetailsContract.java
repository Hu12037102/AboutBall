package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestTeamMyDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:27
 * 更新时间: 2019/4/28 15:27
 * 描述:对内个人信息契约
 */
public interface BallTeamMyDetailsContract {
    interface View extends IBaseView {
        void resultMyDetailsSucceed();
    }

    interface Presenter extends IBasePresenter {
        void saveMyDetails(RequestTeamMyDetailsBean bean);
    }
}

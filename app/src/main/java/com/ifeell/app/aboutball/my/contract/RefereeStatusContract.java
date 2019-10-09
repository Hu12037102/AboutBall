package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 13:40
 * 更新时间: 2019/5/7 13:40
 * 描述:裁判状态契约
 */
public interface RefereeStatusContract {
    interface View extends IBaseView {
        void resultSureRefereeStatus();
    }

    interface Presenter extends IBasePresenter {
    }
}

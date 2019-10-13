package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultRefereeDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/28 10:07
 * 更新时间: 2019/5/28 10:07
 * 描述:裁判详情契约
 */
public interface RefereeDetailsContract {
    interface View extends IBaseView {
        void resultRefereeDetails(ResultRefereeDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadRefereeDetails(long mRefereeId);
    }
}

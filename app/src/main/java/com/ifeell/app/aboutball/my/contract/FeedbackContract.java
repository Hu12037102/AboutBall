package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestFeedbackBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/13 9:22
 * 更新时间: 2019/5/13 9:22
 * 描述:意见反馈契约
 */
public interface FeedbackContract {
    interface View extends IBaseView {
        void resultFeedbackSucceed();
    }

    interface Presenter extends IBasePresenter {
        void feedback(RequestFeedbackBean mRequestBean);
    }
}

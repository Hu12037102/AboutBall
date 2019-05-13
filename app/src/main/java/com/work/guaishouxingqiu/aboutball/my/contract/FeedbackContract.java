package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestFeedbackBean;

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

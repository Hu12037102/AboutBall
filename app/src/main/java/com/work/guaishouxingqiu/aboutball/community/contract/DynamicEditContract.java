package com.work.guaishouxingqiu.aboutball.community.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestPublishDynamicBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:31
 * 更新时间: 2019/6/14 16:31
 * 描述:动态编辑契约
 */
public interface DynamicEditContract {
    interface View extends IBaseView {
        void resultPublishSucceed();
    }

    interface Presenter extends IBasePresenter {
        void publishDynamic(RequestPublishDynamicBean mRequestBean);
    }
}

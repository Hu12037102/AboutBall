package com.work.guaishouxingqiu.aboutball.home.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:28
 * 更新时间: 2019/3/12 17:28
 * 描述:推荐契约
 */
public interface RecommendedContract {
    interface View extends IBaseView {
    }

    interface Presenter extends IBasePresenter {
        void loadData(RequestRecommendDataBean registerBean);
    }
}

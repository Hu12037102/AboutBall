package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestOrderEvaluateBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 13:08
 * 更新时间: 2019/5/15 13:08
 * 描述:订单评价契约
 */
public interface OrderEvaluateContract {
    interface View extends IBaseView {
        void resultEvaluateOrder();
    }

    interface Presenter extends IBasePresenter {
        void evaluateOrder(RequestOrderEvaluateBean mRequestBean);
    }
}

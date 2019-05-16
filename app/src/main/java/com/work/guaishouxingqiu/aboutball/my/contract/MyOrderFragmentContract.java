package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:23
 * 更新时间: 2019/5/14 10:23
 * 描述:我的订单fragment契约
 */
public interface MyOrderFragmentContract {
    interface View extends IBaseView {
        void resultMyOrderData(List<ResultMyOrderBean> data);
        void resultCancelOrderSucceed();
    }

    interface Presenter extends IBasePresenter {
        void lordMyOrder(int mOrderStatus);

        void cancelOrder(long orderId);
    }
}

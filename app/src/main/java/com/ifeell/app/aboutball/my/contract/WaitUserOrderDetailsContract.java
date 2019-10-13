package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.venue.contract.BaseOrderContrast;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 16:39
 * 更新时间: 2019/5/15 16:39
 * 描述:等待使用订单契约
 */
public interface WaitUserOrderDetailsContract {
    interface View extends BaseOrderContrast.View {
        void resultCancelOrderSucceed();
    }

    interface Presenter extends BaseOrderContrast.Presenter {
        void cancelOrder(long mOrderId);
    }
}

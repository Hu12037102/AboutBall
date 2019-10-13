package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 16:20
 * 更新时间: 2019/4/22 16:20
 * 描述:
 */
public interface BaseOrderContrast {
    interface View extends IBaseView {
        void resultOrderDetails(ResultOrderDetailsBean bean);

        void resultBandPhoneNumber();
        void resultCancelOrderSucceed();
    }

    interface Presenter extends IBasePresenter {
        void loadOrderDetails(long orderId);

        void bandOrderPhoneNumber(long orderId, String phoneNumber);

        void cancelOrder(long orderId);
    }
}

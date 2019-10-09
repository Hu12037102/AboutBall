package com.ifeell.app.aboutball.my.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultConfirmOrderBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 10:19
 * 更新时间: 2019/9/11 10:19
 * 描述:确认订单契约
 */
public interface SureOrderContract {
    interface View extends IBaseView {
        void resultConfirmOrder(@NonNull ResultConfirmOrderBean bean);

        void resultCheckOutOrderStatus();
    }

    interface Presenter extends IBasePresenter {
        void loadConfirmOrder(long spuId, String params, int num);

        void checkOutGoodStatus(long spuId, String params, int num);
    }
}

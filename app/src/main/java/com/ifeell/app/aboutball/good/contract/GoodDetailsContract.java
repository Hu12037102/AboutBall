package com.ifeell.app.aboutball.good.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.bean.RequestSureOrderBean;
import com.ifeell.app.aboutball.good.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.venue.contract.BaseOrderContrast;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:01
 * 更新时间: 2019/9/12 14:01
 * 描述:订单详情契约
 */
public interface GoodDetailsContract {
    interface View extends BaseOrderContrast.View {
        void resultOrderDetails(@NonNull ResultOrderDetailsBean bean);
    }

    interface Presenter extends BaseOrderContrast.Presenter {
        void loadOrderDetails(RequestSureOrderBean bean);
        void loadGoodDetails(long orderId);
    }
}

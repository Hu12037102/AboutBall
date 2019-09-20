package com.work.guaishouxingqiu.aboutball.good.contract;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.BaseOrderContrast;

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
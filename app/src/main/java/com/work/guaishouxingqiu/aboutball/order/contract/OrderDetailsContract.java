package com.work.guaishouxingqiu.aboutball.order.contract;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultOrderDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:01
 * 更新时间: 2019/9/12 14:01
 * 描述:订单详情契约
 */
public interface OrderDetailsContract {
    interface View extends IBaseView {
        void resultOrderDetails(@NonNull ResultOrderDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadOrderDetails(RequestSureOrderBean bean);
    }
}

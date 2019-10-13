package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestRefundBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 13:41
 * 更新时间: 2019/5/23 13:41
 * 描述:申请退款契约
 */
public interface RefundContract {
    interface View extends IBaseView{
        void resultRefundOrder();
    }
    interface  Presenter extends IBasePresenter{
        void refundOrder(RequestRefundBean mRequestBean);
    }
}

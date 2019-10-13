package com.ifeell.app.aboutball.good.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.good.bean.ResultGoodRefundDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/20 10:49
 * 更新时间: 2019/9/20 10:49
 * 描述:申请退款契约
 */
public interface GoodRefundContract {
    interface View extends IBaseView {
        void resultRefundDetails(ResultGoodRefundDetailsBean bean);
        void resultRefundSucceed();
    }

    interface Presenter extends IBasePresenter {
        void loadRefundDetail(long orderId);

        void applyRefund(long order, String reason);
    }
}

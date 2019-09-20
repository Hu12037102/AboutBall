package com.work.guaishouxingqiu.aboutball.good.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultGoodRefundDetailsBean;

import java.util.List;

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

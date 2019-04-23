package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;

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
    }

    interface Presenter extends IBasePresenter {
        void loadOrderDetails(long orderId);

        void bandOrderPhoneNumber(long orderId, String phoneNumber);
    }
}

package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:14
 * 更新时间: 2019/5/24 10:14
 * 描述:退款进度详情契约
 */
public interface OrderRefundDetailsContract {
    interface View extends IBaseView {
        void resultRefundDetails(ResultRefundDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void checkRefundDetails(long orderId);

    }
}

package com.work.guaishouxingqiu.aboutball.my.contract;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultConfirmOrderBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 10:19
 * 更新时间: 2019/9/11 10:19
 * 描述:确认订单契约
 */
public interface SureOrderContract {
    interface View extends IBaseView{
        void resultConfirmOrder(@NonNull ResultConfirmOrderBean bean);
    }
    interface Presenter extends IBasePresenter{
        void loadConfirmOrder(long spuId,String params,int num);
    }
}

package com.work.guaishouxingqiu.aboutball.home.contract;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultDoorTicketDetailsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultGameTicketDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:01
 * 更新时间: 2019/9/3 11:01
 * 描述:赛事详情契约
 */
public interface TicketMallDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultGameTicketDetails(@NonNull ResultGameTicketDetailsBean bean);

        void resultDoorTicketDetails(@NonNull ResultDoorTicketDetailsBean bean);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadGameTicketDetails(long skuId);

        void loadDoorTicketDetails(long skuId);
    }
}

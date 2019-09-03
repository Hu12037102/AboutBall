package com.work.guaishouxingqiu.aboutball.home.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultTicketMallBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/30 9:36
 * 更新时间: 2019/8/30 9:36
 * 描述:售票商城fragment契约
 */
public interface TicketMallChildContract {
    interface View extends IBaseView {
        void resultTickMallData(List<ResultTicketMallBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadTickMallList(int statusId);
    }
}

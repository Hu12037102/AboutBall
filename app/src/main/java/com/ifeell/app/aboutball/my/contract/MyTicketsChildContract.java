package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultMyTicketsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:35
 * 更新时间: 2019/9/4 17:35
 * 描述:我的门票fragment契约
 */
public interface MyTicketsChildContract {
    interface View extends IBaseView {
        void resultMyTicketList(List<ResultMyTicketsBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMyTicketsList(int exchange);
    }
}

package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueBookBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/18 13:19
 * 更新时间: 2019/4/18 13:19
 * 描述:
 */
public interface VenueBookingContract {
    interface View extends IBaseView {
        void resultBookList(List<ResultVenueBookBean> data);
        void resultWaitBookList(List<ResultVenueBookBean> data);
        void resultOrderId(long orderId,boolean isSelectorBook);
    }

    interface Presenter extends IBasePresenter {
        void loadBookList(long areaId, String date);
        void loadWaitBookList(long areaId, String date);

        void createOrder(RequestVenueOrderBean bean,boolean isSelectorBook);
    }
}

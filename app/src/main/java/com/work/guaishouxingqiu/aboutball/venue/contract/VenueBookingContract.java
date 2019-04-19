package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;

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
    }

    interface Presenter extends IBasePresenter {
        void loadBookList(int areaId, String date);
    }
}

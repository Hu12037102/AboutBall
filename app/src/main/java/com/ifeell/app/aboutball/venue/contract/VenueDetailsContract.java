package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 13:16
 * 更新时间: 2019/3/27 13:16
 * 描述:场馆详情契约
 */
public interface VenueDetailsContract {
    interface View extends IBaseView{
        void resultDetails(ResultVenueDetailsBean bean);
        void resultVenueData(List<ResultVenueData> data);
    }

    interface Presenter extends IBasePresenter {
        void loadDetails(long stadiumId);

        void loadVenueData(RequestVenueListBean bean);
    }
}

package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:39
 * 更新时间: 2019/3/18 10:39
 * 描述: 场馆列表契约
 */
public interface VenueListContract {
    interface View extends IBaseView {
        void resultBallTypeList(List<ResultTypeBean> data);
        void resultVenueList(List<ResultVenueData> data);
        void resultBallTypeError();

    }

    interface Presenter extends IBasePresenter {
        void loadVenueList(RequestVenueListBean bean);
    }
}

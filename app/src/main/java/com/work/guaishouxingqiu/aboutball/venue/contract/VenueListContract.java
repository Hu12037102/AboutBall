package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;

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
    }

    interface Presenter extends IBasePresenter {
        void loadVenueList(RequestVenueListBean bean);
    }
}

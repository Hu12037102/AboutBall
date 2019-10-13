package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.ResultMyAboutBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:59
 * 更新时间: 2019/5/22 17:59
 * 描述:
 */
public interface MyAboutBallChildContract {
    interface View extends IBaseView {
        void resultData(List<ResultMyAboutBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadData(int flag);
    }
}

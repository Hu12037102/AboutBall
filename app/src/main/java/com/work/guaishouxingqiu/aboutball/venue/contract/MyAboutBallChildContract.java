package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyAboutBean;

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

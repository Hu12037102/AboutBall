package com.ifeell.app.aboutball.game.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameScheduleBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:31
 * 更新时间: 2019/6/11 9:31
 * 描述:赛程契约
 */
public interface GameScheduleContract {
    interface View extends IBaseView {
        void resultScheduleData(List<ResultGameScheduleBean> data);
        void resultNotData();
    }

    interface Presenter extends IBasePresenter {
        void loadScheduleData(String mRequestTime);
    }
}

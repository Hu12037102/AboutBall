package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameScheduleBean;

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

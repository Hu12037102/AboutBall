package com.ifeell.app.aboutball.venue.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.venue.bean.ResultMyBallTeamBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 9:29
 * 更新时间: 2019/4/24 9:29
 * 描述:选择球队契约
 */
public interface SelectorBallTeamContract {
    interface View extends IBaseView {
        void resultMyBallTeam(List<ResultMyBallTeamBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}

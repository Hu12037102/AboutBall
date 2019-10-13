package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultMyBallBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 9:44
 * 更新时间: 2019/4/24 9:44
 * 描述:我的球队契约
 */
public interface MyBallTeamContract {
    interface View extends IBaseView {
        void resultMyBallTeam(List<ResultMyBallBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}

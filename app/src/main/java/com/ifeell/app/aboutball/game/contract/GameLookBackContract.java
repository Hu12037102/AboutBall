package com.ifeell.app.aboutball.game.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameLiveDetailsBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:03
 * 更新时间: 2019/6/24 11:03
 * 描述:比赛-回顾-契约
 */
public interface GameLookBackContract {
    interface View extends IBaseView {
        void resultLiveDetails(ResultGameLiveDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadLiveDetails(int matchId);
    }
}

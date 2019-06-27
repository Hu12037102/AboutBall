package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameLiveDetailsBean;

import java.util.List;

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
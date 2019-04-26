package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:38
 * 更新时间: 2019/4/25 15:38
 * 描述:
 */
public interface BallTeamDetailsContract {
    interface View extends IBaseView{
        void resultExitBallTeam();
    }
    interface Presenter extends IBasePresenter{
        void exitBallTeam(Long teamId, Long playerId);
    }
}

package com.ifeell.app.aboutball.game.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameFiltrateBean;
import com.ifeell.app.aboutball.game.bean.ResultGameGroupBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoOtherBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoScoreboardBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 11:27
 * 更新时间: 2019/6/25 11:27
 * 描述: 比赛-数据-契约
 */
public interface GameInfoContract {
    interface View extends IBaseView {
        void resultMatchFiltrateData(List<ResultGameFiltrateBean> data);

        void resultMatchGroupData(List<ResultGameGroupBean> data);

        void resultMatchScoreboardDat(List<ResultGameInfoScoreboardBean> data);
        void resultMatchOtherData(List<ResultGameInfoOtherBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMatchFiltrateData();

        void loadMatchGroupData(long gameId);

        void loadMatchScoreboardData(long requestGameId, Long requestGroupId);

        void loadOtherData(long mRequestGameId, int mRequestAction);
    }
}

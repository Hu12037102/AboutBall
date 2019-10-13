package com.ifeell.app.aboutball.game.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameDataInfoBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:39
 * 更新时间: 2019/3/25 9:39
 * 描述:比赛数据契约
 */
public interface GameDataContract {
    interface View extends IBaseView {
        void resultHeadGameDetails(ResultGameDataInfoBean bean);


    }

    interface Presenter extends IBasePresenter {
        void loadGameHeadDetails(int gameId);

    }
}

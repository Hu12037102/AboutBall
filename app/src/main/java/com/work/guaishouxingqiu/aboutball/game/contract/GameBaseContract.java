package com.work.guaishouxingqiu.aboutball.game.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:30
 * 更新时间: 2019/3/15 9:30
 * 描述: 比赛Base契约
 */
public interface GameBaseContract {
    interface View extends IBaseView {
        void resultGameData(@NonNull BaseBean<List<ResultGameBean>> bean);

        void resultGameRefreshOrMoreData(@NonNull BaseBean<List<ResultGameBean>> bean);

    }

    interface Presenter extends IBasePresenter {
        void loadGameData(int type);

        void loadGameRefreshOrMoreData(int official, @NonNull String data);

    }
}
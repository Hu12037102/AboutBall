package com.ifeell.app.aboutball.game.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.game.bean.ResultGameSimpleBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 14:54
 * 更新时间: 2019/3/22 14:54
 * 描述:比赛详情契约
 */
public interface GameDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultGameSimple(@NonNull ResultGameSimpleBean bean);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadGameSimple(int matchId);
    }
}

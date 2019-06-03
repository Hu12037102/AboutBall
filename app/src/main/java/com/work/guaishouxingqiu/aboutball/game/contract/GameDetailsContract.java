package com.work.guaishouxingqiu.aboutball.game.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;

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

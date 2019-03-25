package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.MatchResultContract;
import com.work.guaishouxingqiu.aboutball.game.model.MatchResultModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:33
 * 更新时间: 2019/3/25 9:33
 * 描述:赛况P
 */
public class MatchResultPresenter extends BasePresenter<MatchResultContract.View,MatchResultModel>
implements MatchResultContract.Presenter{
    public MatchResultPresenter(@NonNull MatchResultContract.View view) {
        super(view);
    }

    @Override
    protected MatchResultModel createModel() {
        return new MatchResultModel();
    }

    @Override
    public void start() {

    }
}

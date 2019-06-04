package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.model.MatchRefereeResultModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:09
 * 更新时间: 2019/6/4 13:09
 * 描述:赛况记录P
 */
public class MatchRefereeResultPresenter extends BasePresenter<MatchRefereeResultContract.View, MatchRefereeResultModel>
        implements MatchRefereeResultContract.Presenter {
    public MatchRefereeResultPresenter(@NonNull MatchRefereeResultContract.View view) {
        super(view);
    }

    @Override
    protected MatchRefereeResultModel createModel() {
        return new MatchRefereeResultModel();
    }

    @Override
    public void start() {

    }
}

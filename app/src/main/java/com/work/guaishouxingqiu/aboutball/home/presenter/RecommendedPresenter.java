package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.RecommendedContract;
import com.work.guaishouxingqiu.aboutball.home.model.RecommendedModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:35
 * 更新时间: 2019/3/12 17:35
 * 描述:推荐P
 */
public class RecommendedPresenter extends BasePresenter<RecommendedContract.View,RecommendedModel>
implements RecommendedContract.Presenter{
    public RecommendedPresenter(@NonNull RecommendedContract.View view) {
        super(view);
    }

    @Override
    protected RecommendedModel createModel() {
        return new RecommendedModel() ;
    }

    @Override
    public void start() {

    }
}

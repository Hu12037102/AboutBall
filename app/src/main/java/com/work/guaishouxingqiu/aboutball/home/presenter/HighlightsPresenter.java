package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.HighlightsContract;
import com.work.guaishouxingqiu.aboutball.home.model.HighlightsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:55
 * 更新时间: 2019/3/12 17:55
 * 描述:集锦P
 */
public class HighlightsPresenter extends HomeBasePresenter<HighlightsContract.View,HighlightsModel>
implements HighlightsContract.Presenter{
    public HighlightsPresenter(@NonNull HighlightsContract.View view) {
        super(view);
    }

    @Override
    protected HighlightsModel createModel() {
        return new HighlightsModel();
    }

    @Override
    public void start() {

    }
}

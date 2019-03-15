package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.SpecialContract;
import com.work.guaishouxingqiu.aboutball.home.model.SpecialModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:02
 * 更新时间: 2019/3/12 18:02
 * 描述:专栏P
 */
public class SpecialPresenter extends HomeBasePresenter<SpecialContract.View, SpecialModel>
        implements SpecialContract.Presenter {
    public SpecialPresenter(@NonNull SpecialContract.View view) {
        super(view);
    }

    @Override
    protected SpecialModel createModel() {
        return new SpecialModel();
    }

    @Override
    public void start() {

    }
}

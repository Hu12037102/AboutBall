package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.home.contract.SpecialContract;
import com.ifeell.app.aboutball.home.model.SpecialModel;

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

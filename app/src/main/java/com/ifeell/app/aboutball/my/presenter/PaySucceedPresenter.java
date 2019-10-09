package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.my.contract.PaySucceedContract;
import com.ifeell.app.aboutball.my.model.PaySucceedModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 11:11
 * 更新时间: 2019/5/22 11:11
 * 描述:
 */
public class PaySucceedPresenter extends LoginOrSharePresenter<PaySucceedContract.View,PaySucceedModel>
implements PaySucceedContract.Presenter{
    public PaySucceedPresenter(@NonNull PaySucceedContract.View view) {
        super(view);
    }

    @Override
    protected PaySucceedModel createModel() {
        return new PaySucceedModel();
    }
}

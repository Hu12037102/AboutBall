package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.venue.contract.WaitPayOrderDetailsContract;
import com.ifeell.app.aboutball.venue.model.WaitPayOrderDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 11:56
 * 更新时间: 2019/4/22 11:56
 * 描述:待支付订单P
 */
public class WaitPayOrderDetailsPresenter extends BaseOrderPresenter<WaitPayOrderDetailsContract.View,
        WaitPayOrderDetailsModel>implements WaitPayOrderDetailsContract.Presenter{
    public WaitPayOrderDetailsPresenter(@NonNull WaitPayOrderDetailsContract.View view) {
        super(view);
    }

    @Override
    protected WaitPayOrderDetailsModel createModel() {
        return new WaitPayOrderDetailsModel();
    }

    @Override
    public void start() {

    }
}

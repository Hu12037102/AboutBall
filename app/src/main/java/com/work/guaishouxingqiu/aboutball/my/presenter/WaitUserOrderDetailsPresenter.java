package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.my.contract.WaitUserOrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.WaitUserOrderDetailsModel;
import com.work.guaishouxingqiu.aboutball.venue.presenter.BaseOrderPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 16:41
 * 更新时间: 2019/5/15 16:41
 * 描述:等待使用订单详情P
 */
public class WaitUserOrderDetailsPresenter extends BaseOrderPresenter<WaitUserOrderDetailsContract.View,
        WaitUserOrderDetailsModel> implements WaitUserOrderDetailsContract.Presenter {
    public WaitUserOrderDetailsPresenter(@NonNull WaitUserOrderDetailsContract.View view) {
        super(view);
    }

    @Override
    protected WaitUserOrderDetailsModel createModel() {
        return new WaitUserOrderDetailsModel();
    }

    @Override
    public void start() {

    }



}

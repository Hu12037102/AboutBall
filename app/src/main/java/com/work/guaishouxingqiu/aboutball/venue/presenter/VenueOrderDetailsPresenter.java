package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.venue.activity.VenueDetailsActivity;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueOrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.model.VenueOrderDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 11:56
 * 更新时间: 2019/4/22 11:56
 * 描述:场馆约球订单详情P
 */
public class VenueOrderDetailsPresenter extends BaseOrderPresenter<VenueOrderDetailsContract.View,
        VenueOrderDetailsModel>implements VenueOrderDetailsContract.Presenter{
    public VenueOrderDetailsPresenter(@NonNull VenueOrderDetailsContract.View view) {
        super(view);
    }

    @Override
    protected VenueOrderDetailsModel createModel() {
        return new VenueOrderDetailsModel();
    }

    @Override
    public void start() {

    }
}

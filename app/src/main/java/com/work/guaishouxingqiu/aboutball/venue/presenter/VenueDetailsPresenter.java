package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueListContract;
import com.work.guaishouxingqiu.aboutball.venue.model.VenueDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 13:18
 * 更新时间: 2019/3/27 13:18
 * 描述:场馆详情P
 */
public class VenueDetailsPresenter extends BasePresenter<VenueDetailsContract.View,
        VenueDetailsModel> implements VenueDetailsContract.Presenter {


    public VenueDetailsPresenter(@NonNull VenueDetailsContract.View view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    protected VenueDetailsModel createModel() {
        return new VenueDetailsModel();
    }
}

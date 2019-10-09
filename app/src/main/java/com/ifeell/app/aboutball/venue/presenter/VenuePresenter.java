package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.venue.contract.VenueContract;
import com.ifeell.app.aboutball.venue.model.VenueModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:22
 * 更新时间: 2019/3/6 15:22
 * 描述: 场馆P
 */
public class VenuePresenter extends BasePresenter<VenueContract.View,VenueModel> implements VenueContract.Presenter{


    public VenuePresenter(@NonNull VenueContract.View view) {
        super(view);
    }

    @Override
    protected VenueModel createModel() {
        return new VenueModel();
    }

    @Override
    public void start() {

    }
}

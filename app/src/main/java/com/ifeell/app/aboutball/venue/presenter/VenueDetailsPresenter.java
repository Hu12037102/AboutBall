package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;
import com.ifeell.app.aboutball.venue.contract.VenueDetailsContract;
import com.ifeell.app.aboutball.venue.model.VenueDetailsModel;

import java.util.List;

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

    @Override
    public void loadDetails(long stadiumId) {
        mModel.loadDetails(stadiumId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultVenueDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultVenueDetailsBean> baseBean) {
                if (baseBean.code == IApi.Code.SUCCEED && baseBean.result != null) {
                    mView.resultDetails(baseBean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadVenueData(RequestVenueListBean bean) {
        mModel.loadVenueData(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultVenueData>>() {
            @Override
            public void onNext(BaseBean<List<ResultVenueData>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultVenueData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

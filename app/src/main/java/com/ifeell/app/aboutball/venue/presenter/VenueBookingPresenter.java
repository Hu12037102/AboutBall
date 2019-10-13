package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueBookBean;
import com.ifeell.app.aboutball.venue.contract.VenueBookingContract;
import com.ifeell.app.aboutball.venue.model.VenueBookingModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/18 13:56
 * 更新时间: 2019/4/18 13:56
 * 描述:场次预定P
 */
public class VenueBookingPresenter extends BasePresenter<VenueBookingContract.View, VenueBookingModel>
        implements VenueBookingContract.Presenter {
    public VenueBookingPresenter(@NonNull VenueBookingContract.View view) {
        super(view);
    }

    @Override
    protected VenueBookingModel createModel() {
        return new VenueBookingModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadBookList(long areaId, String date) {
        mModel.loadBookList(areaId, date, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultVenueBookBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultVenueBookBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultBookList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadWaitBookList(long areaId, String date) {
        mModel.loadWaitBookList(areaId, date, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultVenueBookBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultVenueBookBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultWaitBookList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void createOrder(RequestVenueOrderBean bean,boolean isSelectorBook) {
        mModel.createOrder(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null &&
                        t.result.code == IApi.Code.SUCCEED) {
                    mView.resultOrderId(t.result.result,isSelectorBook);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

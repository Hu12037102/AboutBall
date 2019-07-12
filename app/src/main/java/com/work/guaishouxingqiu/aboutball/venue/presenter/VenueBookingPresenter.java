package com.work.guaishouxingqiu.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueOrderBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueBookingContract;
import com.work.guaishouxingqiu.aboutball.venue.model.VenueBookingModel;

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

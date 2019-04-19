package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueBookingContract;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueListContract;
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
    public void loadBookList(int areaId, String date) {
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
}

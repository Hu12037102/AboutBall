package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueListContract;
import com.work.guaishouxingqiu.aboutball.venue.model.VenueListModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:41
 * 更新时间: 2019/3/18 10:41
 * 描述:场馆列表P
 */
public class VenueListPresenter extends BasePresenter< VenueListContract.View,VenueListModel>
        implements VenueListContract.Presenter {


    public VenueListPresenter(@NonNull VenueListContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        mModel.loadBallListType(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultTypeBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTypeBean>> bean) {
                if (mView != null && bean.result != null) {
                    mView.resultBallTypeList(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
    @Override
    public void loadVenueList(RequestVenueListBean bean) {
        if (isRefresh) {
            mPageNum = 1;
        }
        mModel.loadVenueList(mPageNum, mPageSize, bean, new BaseObserver<>(true,this, new BaseObserver.Observer<List<ResultVenueData>>() {
            @Override
            public void onNext(BaseBean<List<ResultVenueData>> bean) {
                if (mView == null) {
                    return;
                }
                if (DataUtils.isResultSure(bean)) {
                    mPageNum++;
                    mView.resultVenueList(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }


    @Override
    protected VenueListModel createModel() {
        return new VenueListModel();
    }
}

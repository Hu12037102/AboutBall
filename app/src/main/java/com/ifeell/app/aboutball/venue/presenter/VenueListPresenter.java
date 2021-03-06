package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.contract.VenueListContract;
import com.ifeell.app.aboutball.venue.model.VenueListModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:41
 * 更新时间: 2019/3/18 10:41
 * 描述:场馆列表P
 */
public class VenueListPresenter extends BasePresenter<VenueListContract.View, VenueListModel>
        implements VenueListContract.Presenter {


    public VenueListPresenter(@NonNull VenueListContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        mModel.loadBallListType(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultTypeBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTypeBean>> bean) {
                if (DataUtils.isResultSure(bean)) {
                    mView.resultBallTypeList(bean.result);
                } else {
                    mView.resultBallTypeError();
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.resultBallTypeError();
            }
        }));
    }

    @Override
    public void loadVenueList(RequestVenueListBean bean) {
        if (isRefresh) {
            mPageNum = 1;
        }
        mModel.loadVenueList(mPageNum, mPageSize, bean, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultVenueData>>() {
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

package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueEvaluateBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueEvaluateContract;
import com.work.guaishouxingqiu.aboutball.venue.model.VenueEvaluateModel;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.presenter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评价P
 *
 * @author ：
 */
public class VenueEvaluatePresenter extends BasePresenter<VenueEvaluateContract.View, VenueEvaluateModel>
        implements VenueEvaluateContract.Presenter {
    public VenueEvaluatePresenter(@NonNull VenueEvaluateContract.View view) {
        super(view);
    }

    @Override
    protected VenueEvaluateModel createModel() {
        return new VenueEvaluateModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadVenueEvaluate(long areaId, int flag) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadVenueEvaluate(areaId, flag, mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultVenueEvaluateBean>() {
            @Override
            public void onNext(BaseBean<ResultVenueEvaluateBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultVenueEvaluate(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

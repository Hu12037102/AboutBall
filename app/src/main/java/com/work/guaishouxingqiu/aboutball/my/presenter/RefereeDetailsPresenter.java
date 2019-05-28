package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.RefereeDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.RefereeDetailsModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/28 10:09
 * 更新时间: 2019/5/28 10:09
 * 描述:裁判详情P
 */
public class RefereeDetailsPresenter extends BasePresenter<RefereeDetailsContract.View, RefereeDetailsModel>
        implements RefereeDetailsContract.Presenter {
    public RefereeDetailsPresenter(@NonNull RefereeDetailsContract.View view) {
        super(view);
    }

    @Override
    protected RefereeDetailsModel createModel() {
        return new RefereeDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadRefereeDetails(long refereeId) {
        mModel.loadRefereeDetails(refereeId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultRefereeDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultRefereeDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultRefereeDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

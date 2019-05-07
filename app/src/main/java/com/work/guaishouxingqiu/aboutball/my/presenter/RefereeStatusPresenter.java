package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.contract.RefereeStatusContract;
import com.work.guaishouxingqiu.aboutball.my.model.RefereeStatusModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 13:41
 * 更新时间: 2019/5/7 13:41
 * 描述:裁判状态P
 */
public class RefereeStatusPresenter extends BasePresenter<RefereeStatusContract.View, RefereeStatusModel>
        implements RefereeStatusContract.Presenter {
    public RefereeStatusPresenter(@NonNull RefereeStatusContract.View view) {
        super(view);
    }

    @Override
    protected RefereeStatusModel createModel() {
        return new RefereeStatusModel();
    }

    @Override
    public void start() {
        mModel.sureRefereeStatus(new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultSureRefereeStatus();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
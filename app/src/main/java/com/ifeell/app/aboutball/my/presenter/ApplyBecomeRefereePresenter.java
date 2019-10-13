package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.RequestApplyRefereeBean;
import com.ifeell.app.aboutball.my.contract.ApplyBecomeRefereeContract;
import com.ifeell.app.aboutball.my.model.ApplyBecomeRefereeModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 13:32
 * 更新时间: 2019/5/6 13:32
 * 描述:申请成为裁判P
 */
public class ApplyBecomeRefereePresenter extends BasePresenter<ApplyBecomeRefereeContract.View,
        ApplyBecomeRefereeModel> implements ApplyBecomeRefereeContract.Presenter {
    public ApplyBecomeRefereePresenter(@NonNull ApplyBecomeRefereeContract.View view) {
        super(view);
    }

    @Override
    protected ApplyBecomeRefereeModel createModel() {
        return new ApplyBecomeRefereeModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void commitRefereeCredential(RequestApplyRefereeBean requestBean) {
        mModel.commitRefereeCredential(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (t.code == IApi.Code.SUCCEED){
                    mView.resultCommitRefereeCredential();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

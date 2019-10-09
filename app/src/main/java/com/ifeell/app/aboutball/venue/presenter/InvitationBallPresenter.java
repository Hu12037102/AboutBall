package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.RequestInvitationBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.contract.InvitationBallContract;
import com.ifeell.app.aboutball.venue.model.InvitationBallModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 13:25
 * 更新时间: 2019/5/21 13:25
 * 描述:应邀约球P
 */
public class InvitationBallPresenter extends BasePresenter<InvitationBallContract.View, InvitationBallModel>
        implements InvitationBallContract.Presenter {
    public InvitationBallPresenter(@NonNull InvitationBallContract.View view) {
        super(view);
    }

    @Override
    protected InvitationBallModel createModel() {
        return new InvitationBallModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void createOrder(RequestVenueOrderBean bean) {
        mModel.createOrder(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null &&
                        t.result.code == IApi.Code.SUCCEED) {
                    mView.resultOrderId(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void invitationBall(RequestInvitationBallBean requestBean) {
        mModel.invitationBall(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    if (t.result.result != null) {
                        mView.resultOrderId(t.result.result);
                    } else {
                        mView.resultOrderIdNull();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

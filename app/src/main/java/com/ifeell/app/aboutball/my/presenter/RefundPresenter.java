package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.RequestRefundBean;
import com.ifeell.app.aboutball.my.contract.RefundContract;
import com.ifeell.app.aboutball.my.model.RefundModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 13:45
 * 更新时间: 2019/5/23 13:45
 * 描述:申请退款P
 */
public class RefundPresenter extends BasePresenter<RefundContract.View,RefundModel>implements
RefundContract.Presenter{
    public RefundPresenter(@NonNull RefundContract.View view) {
        super(view);
    }

    @Override
    protected RefundModel createModel() {
        return new RefundModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void refundOrder(RequestRefundBean requestBean) {
        mModel.refundOrder(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultRefundOrder();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

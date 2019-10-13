package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.venue.contract.BaseOrderContrast;
import com.ifeell.app.aboutball.venue.model.BaseOrderModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 16:44
 * 更新时间: 2019/4/22 16:44
 * 描述:
 */
public abstract class BaseOrderPresenter<V extends BaseOrderContrast.View, M extends BaseOrderModel> extends
        BasePresenter<V, M> implements BaseOrderContrast.Presenter {


    public BaseOrderPresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void loadOrderDetails(long orderId) {
        mModel.loadOrderDetails(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultOrderDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultOrderDetailsBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultOrderDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void bandOrderPhoneNumber(long orderId, String phoneNumber) {
        mModel.bandPhoneNumber(orderId, phoneNumber, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultBandPhoneNumber();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, false));
    }
    @Override
    public void cancelOrder(long orderId) {
        mModel.cancelOrder(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultCancelOrderSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

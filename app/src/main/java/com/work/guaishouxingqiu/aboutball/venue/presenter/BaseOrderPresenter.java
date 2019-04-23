package com.work.guaishouxingqiu.aboutball.venue.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.BaseOrderContrast;
import com.work.guaishouxingqiu.aboutball.venue.model.BaseOrderModel;

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
                if (t.code == IApi.Code.SUCCEED) {
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
        }));
    }
}

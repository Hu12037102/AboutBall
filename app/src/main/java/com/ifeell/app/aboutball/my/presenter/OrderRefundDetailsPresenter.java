package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.ResultRefundDetailsBean;
import com.ifeell.app.aboutball.my.contract.OrderRefundDetailsContract;
import com.ifeell.app.aboutball.my.model.OrderRefundDetailsModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:15
 * 更新时间: 2019/5/24 10:15
 * 描述:退款进度详情P
 */
public class OrderRefundDetailsPresenter extends BasePresenter<OrderRefundDetailsContract.View,
        OrderRefundDetailsModel> implements OrderRefundDetailsContract.Presenter {
    public OrderRefundDetailsPresenter(@NonNull OrderRefundDetailsContract.View view) {
        super(view);
    }

    @Override
    protected OrderRefundDetailsModel createModel() {
        return new OrderRefundDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void checkRefundDetails(long orderId, int flag) {
        if (flag == 1) {
            mModel.checkGoodRefundDetails(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultRefundDetailsBean>() {
                @Override
                public void onNext(BaseBean<ResultRefundDetailsBean> t) {
                    if (DataUtils.isResultSure(t)) {
                        mView.resultRefundDetails(t.result);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        } else {
            mModel.checkOrderRefundDetails(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultRefundDetailsBean>() {
                @Override
                public void onNext(BaseBean<ResultRefundDetailsBean> t) {
                    if (DataUtils.isResultSure(t)) {
                        mView.resultRefundDetails(t.result);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        }
    }
}

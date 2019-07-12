package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderRefundDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.OrderRefundDetailsModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

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
    public void checkRefundDetails(long orderId) {
        mModel.checkRefundDetails(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<ResultRefundDetailsBean>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<ResultRefundDetailsBean>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t) && t.result.result != null) {
                    mView.resultRefundDetails(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

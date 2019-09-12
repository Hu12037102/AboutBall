package com.work.guaishouxingqiu.aboutball.order.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.order.contract.OrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.order.model.OrderDetailsModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:03
 * 更新时间: 2019/9/12 14:03
 * 描述:订单详情P
 */
public class OrderDetailsPresenter extends BasePresenter<OrderDetailsContract.View, OrderDetailsModel>
        implements OrderDetailsContract.Presenter {
    public OrderDetailsPresenter(@NonNull OrderDetailsContract.View view) {
        super(view);
    }

    @Override
    protected OrderDetailsModel createModel() {
        return new OrderDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadOrderDetails(RequestSureOrderBean bean) {
        mModel.loadOrderDetails(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultOrderDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultOrderDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultOrderDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

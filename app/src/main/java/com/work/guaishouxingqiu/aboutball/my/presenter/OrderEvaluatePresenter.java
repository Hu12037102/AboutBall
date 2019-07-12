package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestOrderEvaluateBean;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.model.OrderEvaluateModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 13:10
 * 更新时间: 2019/5/15 13:10
 * 描述:订单评价P
 */
public class OrderEvaluatePresenter extends BasePresenter<OrderEvaluateContract.View, OrderEvaluateModel> implements
        OrderEvaluateContract.Presenter {
    public OrderEvaluatePresenter(@NonNull OrderEvaluateContract.View view) {
        super(view);
    }

    @Override
    protected OrderEvaluateModel createModel() {
        return new OrderEvaluateModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void evaluateOrder(RequestOrderEvaluateBean requestBean) {
        mModel.evaluateOrder(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultEvaluateOrder();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

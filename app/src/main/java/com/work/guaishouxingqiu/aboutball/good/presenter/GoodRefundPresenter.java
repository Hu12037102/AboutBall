package com.work.guaishouxingqiu.aboutball.good.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultGoodRefundDetailsBean;
import com.work.guaishouxingqiu.aboutball.good.contract.GoodRefundContract;
import com.work.guaishouxingqiu.aboutball.good.model.GoodRefundModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/20 10:54
 * 更新时间: 2019/9/20 10:54
 * 描述:申请退款presenter
 */
public class GoodRefundPresenter extends BasePresenter<GoodRefundContract.View, GoodRefundModel>
        implements GoodRefundContract.Presenter {
    public GoodRefundPresenter(@NonNull GoodRefundContract.View view) {
        super(view);
    }

    @Override
    protected GoodRefundModel createModel() {
        return new GoodRefundModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadRefundDetail(long orderId) {
        mModel.loadRefundDetail(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultGoodRefundDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultGoodRefundDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultRefundDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void applyRefund(long order, String reason) {
        mModel.applyRefund(order, reason, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.isResultSureResultNull(t)) {
                    mView.resultRefundSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

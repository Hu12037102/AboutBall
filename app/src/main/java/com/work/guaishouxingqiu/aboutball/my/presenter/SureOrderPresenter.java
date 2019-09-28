package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultConfirmOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.SureOrderContract;
import com.work.guaishouxingqiu.aboutball.my.model.SureOrderModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 10:20
 * 更新时间: 2019/9/11 10:20
 * 描述:确认订单P
 */
public class SureOrderPresenter extends BasePresenter<SureOrderContract.View, SureOrderModel>
        implements SureOrderContract.Presenter {
    public SureOrderPresenter(@NonNull SureOrderContract.View view) {
        super(view);
    }

    @Override
    protected SureOrderModel createModel() {
        return new SureOrderModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadConfirmOrder(long spuId, String params, int num) {
        mModel.loadConfirmOrder(spuId, params, num, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultConfirmOrderBean>() {
            @Override
            public void onNext(BaseBean<ResultConfirmOrderBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultConfirmOrder(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void checkOutGoodStatus(long spuId, String params, int num) {
        mModel.checkOutGoodStatus(spuId, params, num, new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultCheckOutOrderStatus();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },false));
    }
}

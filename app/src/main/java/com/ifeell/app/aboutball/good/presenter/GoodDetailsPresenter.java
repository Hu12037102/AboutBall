package com.ifeell.app.aboutball.good.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.bean.RequestSureOrderBean;
import com.ifeell.app.aboutball.good.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.good.contract.GoodDetailsContract;
import com.ifeell.app.aboutball.good.model.GoodDetailsModel;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.presenter.BaseOrderPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:03
 * 更新时间: 2019/9/12 14:03
 * 描述:订单详情P
 */
public class GoodDetailsPresenter extends BaseOrderPresenter<GoodDetailsContract.View, GoodDetailsModel>
        implements GoodDetailsContract.Presenter {
    public GoodDetailsPresenter(@NonNull GoodDetailsContract.View view) {
        super(view);
    }

    @Override
    protected GoodDetailsModel createModel() {
        return new GoodDetailsModel();
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

    @Override
    public void loadGoodDetails(long orderId) {
        mModel.loadGoodDetails(orderId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultOrderDetailsBean>() {
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

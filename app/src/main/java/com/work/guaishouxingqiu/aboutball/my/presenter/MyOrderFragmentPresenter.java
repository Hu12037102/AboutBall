package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderFragmentContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyOrderFragmentModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:25
 * 更新时间: 2019/5/14 10:25
 * 描述:我的订单fragmentP
 */
public class MyOrderFragmentPresenter extends BasePresenter<MyOrderFragmentContract.View, MyOrderFragmentModel>
        implements MyOrderFragmentContract.Presenter {
    public MyOrderFragmentPresenter(@NonNull MyOrderFragmentContract.View view) {
        super(view);
    }

    @Override
    protected MyOrderFragmentModel createModel() {
        return new MyOrderFragmentModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void lordMyOrder(int mOrderStatus) {
        if (isRefresh) {
            this.mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.lordMyOrder(mOrderStatus, mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultMyOrderBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyOrderBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultMyOrderData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
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

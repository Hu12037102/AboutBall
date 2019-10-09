package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.home.bean.ResultDoorTicketDetailsBean;
import com.ifeell.app.aboutball.home.bean.ResultGameTicketDetailsBean;
import com.ifeell.app.aboutball.home.contract.TicketMallDetailsContract;
import com.ifeell.app.aboutball.home.model.TicketMallDetailsModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:06
 * 更新时间: 2019/9/3 11:06
 * 描述: 购票详情P
 */
public class TicketMallDetailsPresenter extends LoginOrSharePresenter<TicketMallDetailsContract.View, TicketMallDetailsModel>
        implements TicketMallDetailsContract.Presenter {
    public TicketMallDetailsPresenter(@NonNull TicketMallDetailsContract.View view) {
        super(view);
    }

    @Override
    protected TicketMallDetailsModel createModel() {
        return new TicketMallDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadGameTicketDetails(long skuId) {
        mModel.loadGameTicketDetails(skuId,new BaseObserver<>(true, this, new BaseObserver.Observer<ResultGameTicketDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultGameTicketDetailsBean> t) {
                if (DataUtils.isResultSure(t)){
                    mView.resultGameTicketDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadDoorTicketDetails(long skuId) {
        mModel.loadDoorTicketDetails(skuId,new BaseObserver<>(true, this, new BaseObserver.Observer<ResultDoorTicketDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultDoorTicketDetailsBean> t) {
                if (DataUtils.isResultSure(t)){
                    mView.resultDoorTicketDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

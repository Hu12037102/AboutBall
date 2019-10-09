package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.ResultPrizeBean;
import com.ifeell.app.aboutball.my.contract.BasePrizeContract;
import com.ifeell.app.aboutball.my.model.BasePrizeModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:18
 * 更新时间: 2019/4/11 17:18
 * 描述:我的奖品基类
 */
public class BasePrizePresenter extends BasePresenter<BasePrizeContract.View, BasePrizeModel>
        implements BasePrizeContract.Presenter {
    public BasePrizePresenter(@NonNull BasePrizeContract.View view) {
        super(view);
    }

    @Override
    protected BasePrizeModel createModel() {
        return new BasePrizeModel();
    }

    @Override
    public void loadMyPrize(int status) {
        mModel.loadMyPrize(status, new BaseObserver<>(this, new BaseObserver.Observer<ResultPrizeBean>() {
            @Override
            public void onNext(BaseBean<ResultPrizeBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMyPrize(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void start() {
        mModel.hasAddress(new BaseObserver<>(this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultHasAddress(Integer.valueOf(t.result));
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BasePrizeContract;
import com.work.guaishouxingqiu.aboutball.my.model.BasePrizeModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.IllegalFormatCodePointException;
import java.util.List;

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

    }
}

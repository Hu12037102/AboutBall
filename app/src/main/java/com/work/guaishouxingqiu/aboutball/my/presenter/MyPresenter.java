package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:54
 * 更新时间: 2019/3/6 15:54
 * 描述:我的P
 */
public class MyPresenter extends LoginOrSharePresenter<MyContract.View, MyModel> implements MyContract.Presenter {
    public MyPresenter(@NonNull MyContract.View view) {
        super(view);
    }

    @Override
    protected MyModel createModel() {
        return new MyModel();
    }

    @Override
    public void start() {

    }

    /*@Override
    public void judgeRefereeStatus() {
        mModel.judgeRefereeStatus(new BaseObserver<>(true, this, new BaseObserver.Observer<Integer>() {
            @Override
            public void onNext(BaseBean<Integer> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultRefereeStatus(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, false));
    }*/
}

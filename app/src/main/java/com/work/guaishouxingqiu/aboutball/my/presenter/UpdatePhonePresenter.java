package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.presenter.MessagePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePhoneBean;
import com.work.guaishouxingqiu.aboutball.my.contract.UpdatePhoneContract;
import com.work.guaishouxingqiu.aboutball.my.model.UpdatePhoneModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 10:53
 * 更新时间: 2019/4/16 10:53
 * 描述:
 */
public class UpdatePhonePresenter extends MessagePresenter<UpdatePhoneContract.View,
        UpdatePhoneModel> implements UpdatePhoneContract.Presenter {
    public UpdatePhonePresenter(@NonNull UpdatePhoneContract.View view) {
        super(view);
    }

    @Override
    protected UpdatePhoneModel createModel() {
        return new UpdatePhoneModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void updatePhone(RequestUpdatePhoneBean bean) {
        mModel.updatePhoneNumber(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.updatePhoneSucceed(bean.phone);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

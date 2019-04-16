package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePasswordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.UpdatePasswordContract;
import com.work.guaishouxingqiu.aboutball.my.model.UpdatePasswordModel;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:48
 * 更新时间: 2019/4/16 14:48
 * 描述:
 */
public class UpdatePasswordPresenter extends BasePresenter<UpdatePasswordContract.View, UpdatePasswordModel>
        implements UpdatePasswordContract.Presenter {
    public UpdatePasswordPresenter(@NonNull UpdatePasswordContract.View view) {
        super(view);
    }

    @Override
    protected UpdatePasswordModel createModel() {
        return new UpdatePasswordModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void updatePassword(RequestUpdatePasswordBean bean) {
        mModel.updatePassword(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<LoginResultBean>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<LoginResultBean>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result.code == IApi.Code.SUCCEED) {
                    mView.resultPasswordSucceed(t.result.result.id_token);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

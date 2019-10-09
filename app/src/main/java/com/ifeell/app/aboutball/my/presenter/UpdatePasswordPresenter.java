package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.presenter.MessagePresenter;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePasswordBean;
import com.ifeell.app.aboutball.my.contract.UpdatePasswordContract;
import com.ifeell.app.aboutball.my.model.UpdatePasswordModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:48
 * 更新时间: 2019/4/16 14:48
 * 描述:
 */
public class UpdatePasswordPresenter extends MessagePresenter<UpdatePasswordContract.View, UpdatePasswordModel>
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

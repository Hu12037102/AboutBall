package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.presenter.MessagePresenter;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePhoneBean;
import com.ifeell.app.aboutball.my.contract.UpdatePhoneContract;
import com.ifeell.app.aboutball.my.model.UpdatePhoneModel;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.util.DataUtils;

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
    public void bandThreePhone(RequestUpdatePhoneBean bean) {
        mModel.bandPhoneNumber(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<LoginResultBean>() {
            @Override
            public void onNext(BaseBean<LoginResultBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.bandThreePhoneSucceed(t.result.id_token);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void updatePhone(RequestUpdatePhoneBean bean) {
        mModel.updatePhoneNumber(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<LoginResultBean>() {
            @Override
            public void onNext(BaseBean<LoginResultBean> t) {
                if (DataUtils.isResultSure(t)) {
                    UserManger.get().putToken(t.result.id_token);
                    UserManger.get().putPhone(bean.phone);
                    mView.updatePhoneSucceed(t.result.id_token);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

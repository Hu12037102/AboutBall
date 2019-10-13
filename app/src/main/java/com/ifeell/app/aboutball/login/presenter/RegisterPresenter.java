package com.ifeell.app.aboutball.login.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.login.bean.RegisterResultBean;
import com.ifeell.app.aboutball.login.bean.RequestRegisterBean;
import com.ifeell.app.aboutball.login.contract.RegisterContract;
import com.ifeell.app.aboutball.login.model.RegisterModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:53
 * 更新时间: 2019/3/11 11:53
 * 描述: 注册P
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View, RegisterModel> implements RegisterContract.Presenter {


    public RegisterPresenter(@NonNull RegisterContract.View view) {
        super(view);
    }

    @Override
    protected RegisterModel createModel() {
        return new RegisterModel();
    }

    @Override
    public void start() {

    }


    @Override
    public void register(@NonNull RequestRegisterBean requestBean) {
        if (mView != null) {
            mView.showLoadingView();
        }
        mModel.register(requestBean, new BaseObserver<>(this, new BaseObserver.Observer<RegisterResultBean>() {
            @Override
            public void onNext(BaseBean<RegisterResultBean> bean) {
                if (mView == null) {
                    return;
                }
                mView.showToast(bean.message);
                mView.dismissLoadingView();
                mView.registerResult(bean);
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.dismissLoadingView();
                }
            }


        }));
    }

    @Override
    public void forgetPassword(RequestRegisterBean requestBean) {

        mModel.forgetPassword(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<RegisterResultBean>() {
            @Override
            public void onNext(BaseBean<RegisterResultBean> t) {
                mView.registerResult(t);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
      /*  mModel.register(requestBean, new BaseObserver<>(this, new BaseObserver.Observer<RegisterResultBean>() {
            @Override
            public void onNext(BaseBean<RegisterResultBean> bean) {
                if (mView == null) {
                    return;
                }
                mView.showToast(bean.message);
                mView.dismissLoadingView();
                mView.registerResult(bean);
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.dismissLoadingView();
                }
            }


        }));*/
    }
}

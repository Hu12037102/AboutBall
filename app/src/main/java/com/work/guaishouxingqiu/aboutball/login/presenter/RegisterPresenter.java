package com.work.guaishouxingqiu.aboutball.login.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterContract;
import com.work.guaishouxingqiu.aboutball.login.model.RegisterModel;
import com.work.guaishouxingqiu.aboutball.other.UserManger;

import io.reactivex.disposables.Disposable;

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

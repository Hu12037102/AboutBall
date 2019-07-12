package com.work.guaishouxingqiu.aboutball.login.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.login.contract.LoginContract;
import com.work.guaishouxingqiu.aboutball.login.model.LoginModel;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:37
 * 更新时间: 2019/3/7 11:37
 * 描述:登录P
 */
public class LoginPresenter extends LoginOrSharePresenter<LoginContract.View, LoginModel> implements LoginContract.Presenter {
    public LoginPresenter(@NonNull LoginContract.View view) {
        super(view);
    }

    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void login(@NonNull RequestLoginBean loginBean) {
        if (mView != null) {
            mView.showLoadingView();
        }
        mModel.login(loginBean, new BaseObserver(this, new BaseObserver.Observer<LoginResultBean>() {
            @Override
            public void onNext(BaseBean<LoginResultBean> resultBeanBaseBean) {
                if (mView == null)
                    return;
                mView.loginSucceedResult(resultBeanBaseBean);
              /*  mView.dismissLoadingView();
                mView.showToast(resultBeanBaseBean.message);*/
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
    public void loadUserAccount() {
     /*   mModel.loadUserAccount(new BaseObserver<>(true, this, new BaseObserver.Observer<UserBean>() {

            @Override
            public void onNext(BaseBean<UserBean> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    UserManger.get().putUser(bean.result);
                    loadUserAccountInfo();
                }
            }

            @Override
            public void onError(Throwable e) {

            }


        }));*/

    }

    public void loadUserAccountInfo() {
        mModel.loadUserAccountInfo(new BaseObserver<>(true, this, new BaseObserver.Observer<UserBean>() {
            @Override
            public void onNext(BaseBean<UserBean> bean) {

                if (bean.code == IApi.Code.SUCCEED) {
                    UserManger.get().putUser(bean.result);
                    mView.resultUserDataSucceed();
                }






            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }


}

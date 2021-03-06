package com.ifeell.app.aboutball.login.model;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.login.LoginService;
import com.ifeell.app.aboutball.login.bean.RequestLoginBean;
import com.ifeell.app.aboutball.login.bean.UserBean;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:22
 * 更新时间: 2019/3/7 11:22
 * 描述: 登录Model
 */
public class LoginModel extends LoginOrShareModel {

    public void login(@NonNull RequestLoginBean requestLoginBean, BaseObserver baseObserver) {
        mRetrofitManger.create(LoginService.class)
                .login(requestLoginBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

/*    public void loadUserAccount(BaseObserver<UserBean> observer) {
        mRetrofitManger.create(LoginService.class)
                .userAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }*/

    public void loadUserAccountInfo(BaseObserver<UserBean> observer) {
        mRetrofitManger.create(LoginService.class)
                .userAccountInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}

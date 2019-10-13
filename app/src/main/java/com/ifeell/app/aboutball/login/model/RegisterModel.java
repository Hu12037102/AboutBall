package com.ifeell.app.aboutball.login.model;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.login.LoginService;
import com.ifeell.app.aboutball.login.bean.RegisterResultBean;
import com.ifeell.app.aboutball.login.bean.RequestRegisterBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:52
 * 更新时间: 2019/3/11 11:52
 * 描述: 注册Model
 */
public class RegisterModel extends BaseModel {
    public void register(@NonNull RequestRegisterBean registerBean, BaseObserver<RegisterResultBean> observer){
        mRetrofitManger.create(LoginService.class)
                .register(registerBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void forgetPassword(RequestRegisterBean requestBean,BaseObserver<RegisterResultBean> observer) {
        mRetrofitManger.create(LoginService.class)
                .forgetPassword(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

package com.work.guaishouxingqiu.aboutball.login.model;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.login.LoginService;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;

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
}
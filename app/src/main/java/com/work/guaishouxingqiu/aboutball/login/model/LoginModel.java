package com.work.guaishouxingqiu.aboutball.login.model;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.login.LoginService;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:22
 * 更新时间: 2019/3/7 11:22
 * 描述: 登录Model
 */
public class LoginModel extends BaseModel{

    public void login(@NonNull RequestLoginBean requestLoginBean,BaseObserver baseObserver){
        mRetrofitManger.create(LoginService.class)
                .login(requestLoginBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);

    }

}

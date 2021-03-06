package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.model.MessageModel;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePhoneBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 10:54
 * 更新时间: 2019/4/16 10:54
 * 描述:更新手机号Model
 */
public class UpdatePhoneModel extends MessageModel {
    public void bandPhoneNumber(RequestUpdatePhoneBean bean, BaseObserver<LoginResultBean> observer) {
        mRetrofitManger.create(MyService.class)
                .bindPhoneNumber(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void updatePhoneNumber(RequestUpdatePhoneBean bean,BaseObserver<LoginResultBean> observer) {
        mRetrofitManger.create(MyService.class)
                .updatePhoneNumber(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

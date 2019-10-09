package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.model.MessageModel;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestUpdatePasswordBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:49
 * 更新时间: 2019/4/16 14:49
 * 描述:
 */
public class UpdatePasswordModel extends MessageModel {
    public void updatePassword(RequestUpdatePasswordBean bean, BaseObserver<BaseDataBean<LoginResultBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .updatePassword(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

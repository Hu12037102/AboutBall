package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.login.LoginService;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestUpdateSexBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:21
 * 更新时间: 2019/3/20 9:21
 * 描述:我的详情model
 */
public class MyDetailsModel extends BaseModel {
    public void updateSex(RequestUpdateSexBean bean, BaseObserver observer) {
        mRetrofitManger.create(LoginService.class)
                .updateAccountSex(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

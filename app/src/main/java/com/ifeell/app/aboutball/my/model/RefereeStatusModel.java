package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 13:40
 * 更新时间: 2019/5/7 13:40
 * 描述:裁判状态Model
 */
public class RefereeStatusModel extends BaseModel{

    public void sureRefereeStatus(BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .sureRefereeStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

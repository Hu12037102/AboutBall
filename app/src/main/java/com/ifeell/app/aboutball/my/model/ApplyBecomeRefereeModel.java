package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestApplyRefereeBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 13:31
 * 更新时间: 2019/5/6 13:31
 * 描述:申请成为裁判Model
 */
public class ApplyBecomeRefereeModel extends BaseModel{
    public void commitRefereeCredential(RequestApplyRefereeBean requestBean, BaseObserver<String> observer) {
        mRetrofitManger.create(MyService.class)
                .commitRefereeCredential(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

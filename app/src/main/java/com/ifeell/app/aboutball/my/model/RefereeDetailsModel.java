package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultRefereeDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/28 10:07
 * 更新时间: 2019/5/28 10:07
 * 描述:裁判详情model
 */
public class RefereeDetailsModel extends BaseModel{
    public void loadRefereeDetails(long refereeId,BaseObserver<ResultRefereeDetailsBean> observer) {
        mRetrofitManger.create(MyService.class )
                .loadRefereeDetails(refereeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}

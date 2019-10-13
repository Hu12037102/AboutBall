package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultRefereeRecordBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:10
 * 更新时间: 2019/5/9 10:10
 * 描述:我的裁判记录fragmentModel
 */
public class MyRefereeRecordChildModel extends BaseModel{

    public void loadMyRefereeRecord(BaseObserver<List<ResultRefereeRecordBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyRefereeRecord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadRefereeRecord(long refereeId,BaseObserver<List<ResultRefereeRecordBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadRefereeRecord(refereeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}

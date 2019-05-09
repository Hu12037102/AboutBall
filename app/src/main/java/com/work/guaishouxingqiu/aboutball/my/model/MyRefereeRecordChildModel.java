package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;

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
}

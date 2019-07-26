package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultAttentionFanBean;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/26 11:04
 * 更新时间: 2019/7/26 11:04
 * 描述:关注和我的粉丝model
 */
public class AttentionAndFansModel extends BaseModel {
    public void loadMyAttentionData(int pageNum, int pageSize, BaseObserver<List<ResultAttentionFanBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyAttentionData(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadMyFansData(int pageNum, int pageSize, BaseObserver<List<ResultAttentionFanBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyFansData(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

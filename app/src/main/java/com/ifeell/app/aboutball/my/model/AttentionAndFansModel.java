package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultAttentionFanBean;

import java.util.List;

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

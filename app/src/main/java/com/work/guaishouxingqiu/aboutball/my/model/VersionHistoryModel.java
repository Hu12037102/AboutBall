package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultVersionHistoryBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/4 10:57
 * 更新时间: 2019/7/4 10:57
 * 描述:历史版本model
 */
public class VersionHistoryModel extends BaseModel{
    public void loadVersionHistoryData(int pageNum, int pageSize, BaseObserver<List<ResultVersionHistoryBean> >observer) {
        mRetrofitManger.create(MyService.class)
                .getVersionHistoryData(pageNum,pageSize, Contast.ANDROID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

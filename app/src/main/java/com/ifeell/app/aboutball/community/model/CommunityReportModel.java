package com.ifeell.app.aboutball.community.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.community.CommunityService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 11:46
 * 更新时间: 2019/6/17 11:46
 * 描述:动态举报model
 */
public class CommunityReportModel extends BaseModel {
    public void reportCommunity(long tweetId, String reportCase, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .getReportCommunity(tweetId, reportCase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

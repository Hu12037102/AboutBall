package com.ifeell.app.aboutball.community.model;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.community.CommunityService;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/20 13:45
 * 更新时间: 2019/6/20 13:45
 * 描述:话题动态model
 */
public class TopicDynamicsModel extends LoginOrShareModel {
    public void loadRecommendedTopic(long topicId, int pageNum, int pageSize, BaseObserver<List<ResultCommunityDataBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadRecommendedTopic(topicId, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadNewTopic(long topicId, int pageNum, int pageSize, BaseObserver<List<ResultCommunityDataBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadNewTopic(topicId, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

package com.ifeell.app.aboutball.community.model;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.community.CommunityService;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.bean.ResultRecommendHotBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:55
 * 更新时间: 2019/3/19 13:55
 * 描述:社区-推荐model
 */
public class CommunityRecommendModel extends LoginOrShareModel{
    public void loadHeadData(BaseObserver<List<ResultRecommendHotBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadHotTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void loadData(int pageNum, int pageSize, BaseObserver<List<ResultCommunityDataBean>>observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadRecommendData(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}

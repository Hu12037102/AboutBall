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
 * 创建时间: 2019/3/19 13:48
 * 更新时间: 2019/3/19 13:48
 * 描述:社区-关注model
 */
public class CommunityAttentionModel extends LoginOrShareModel {
    public void loadData(int pageNum, int pageSize, BaseObserver<List<ResultCommunityDataBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadCommunityAttentionData(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

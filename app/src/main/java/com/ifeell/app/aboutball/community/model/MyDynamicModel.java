package com.ifeell.app.aboutball.community.model;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.community.CommunityService;
import com.ifeell.app.aboutball.community.bean.ResultUserDynamicBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:08
 * 更新时间: 2019/6/19 9:08
 * 描述:我的动态model
 */
public class MyDynamicModel extends LoginOrShareModel {
    public void loadMyDynamic(int pageNum, int pageSize,long userId, BaseObserver<ResultUserDynamicBean> observer) {
        mRetrofitManger.create(CommunityService.class)
                .getMyDynamicData(pageNum, pageSize,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}

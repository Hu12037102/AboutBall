package com.ifeell.app.aboutball.community.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.community.CommunityService;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:24
 * 更新时间: 2019/6/14 18:24
 * 描述:选择话题model
 */
public class SelectorTopicModel extends BaseModel{
    public void loadTopicData(BaseObserver<List<ResultTopicBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadTopicData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

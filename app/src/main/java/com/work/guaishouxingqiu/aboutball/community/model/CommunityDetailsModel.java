package com.work.guaishouxingqiu.aboutball.community.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;
import com.work.guaishouxingqiu.aboutball.community.CommunityService;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:34
 * 更新时间: 2019/6/13 18:34
 * 描述:动态详情契约
 */
public class CommunityDetailsModel extends LoginOrShareModel{
    public void loadCommentData(int pageNum, int pageSize, long tweetId, BaseObserver<List<ResultNewsMessageBean>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .loadCommunityCommentData(pageNum,pageSize,tweetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

package com.work.guaishouxingqiu.aboutball.community.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.community.CommunityService;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestPublishDynamicBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:34
 * 更新时间: 2019/6/14 16:34
 * 描述:动态编辑model
 */
public class DynamicEditModel extends BaseModel{
    public void publishDynamic(RequestPublishDynamicBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(CommunityService.class)
                .publishCommunityDynamic(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

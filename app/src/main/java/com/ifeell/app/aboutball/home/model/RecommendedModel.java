package com.ifeell.app.aboutball.home.model;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.home.HomeService;
import com.ifeell.app.aboutball.home.bean.RequestRecommendDataBean;
import com.ifeell.app.aboutball.home.bean.ResultRecommendDataBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:35
 * 更新时间: 2019/3/12 17:35
 * 描述: 推荐model
 */
public class RecommendedModel extends HomeBaseModel {
    public void loadHead(@NonNull RequestRecommendDataBean bean, BaseObserver<ResultRecommendDataBean> observer) {
        mRetrofitManger.create(HomeService.class)
                .headData(bean.longitude, bean.latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}

package com.work.guaishouxingqiu.aboutball.home.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.home.HomeService;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:03
 * 更新时间: 2019/3/6 16:03
 * 描述: 首页Fragment
 */
public class HomeModel extends BaseModel {
    public void loadHomeTab(BaseObserver<List<ResultHomeTabBean>> observer) {
        mRetrofitManger.create(HomeService.class)
                .getHomeTab()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}

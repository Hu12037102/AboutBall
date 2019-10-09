package com.ifeell.app.aboutball.home.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.home.HomeService;
import com.ifeell.app.aboutball.home.bean.ResultHomeTabBean;

import java.util.List;

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

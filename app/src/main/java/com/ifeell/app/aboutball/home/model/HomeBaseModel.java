package com.ifeell.app.aboutball.home.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.home.HomeService;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:36
 * 更新时间: 2019/3/15 9:36
 * 描述:
 */
public class HomeBaseModel extends BaseModel{
    /**
     * 资讯列表
     *
     * @param pagerNum
     * @param pagerSize
     * @param observer
     */
    public void loadData(int pagerNum, int pagerSize, int typeId,BaseObserver<List<ResultNewsBean>> observer) {
        mRetrofitManger.create(HomeService.class)
                .newsData(pagerNum, pagerSize,typeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

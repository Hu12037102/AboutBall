package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultMyGameRecordBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况model
 */
public class TeamMatchResultModel extends BaseModel {
    public void loadMyGameResultDetails(long agreeId, BaseObserver<List<ResultMyGameRecordBean>>observer) {
        mRetrofitManger.create(MyService.class)
                .getMyGameRecordDetails(agreeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

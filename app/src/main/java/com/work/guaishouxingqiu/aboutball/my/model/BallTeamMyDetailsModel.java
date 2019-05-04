package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestTeamMyDetailsBean;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:29
 * 更新时间: 2019/4/28 15:29
 * 描述:对内个人信息model
 */
public class BallTeamMyDetailsModel extends BaseModel {
    public void saveMyDetails(RequestTeamMyDetailsBean bean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .saveMyPlayerInfo(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

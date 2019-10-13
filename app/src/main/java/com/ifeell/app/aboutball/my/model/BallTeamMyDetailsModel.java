package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestTeamMyDetailsBean;

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

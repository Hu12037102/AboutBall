package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeightBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateSexBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateWeightBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:21
 * 更新时间: 2019/3/20 9:21
 * 描述:我的详情model
 */
public class MyDetailsModel extends LoginOrShareModel {
    public void updateSex(RequestUpdateSexBean bean, BaseObserver<BaseDataBean> observer) {
        mRetrofitManger.create(MyService.class)
                .updateAccountSex(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void updateWeight(RequestUpdateWeightBean bean, BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(MyService.class)
                .updateAccountWeight(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void updateHeight(RequestUpdateHeightBean bean,BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(MyService.class)
                .updateAccountHeight(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void updateBirthday(RequestUpdateBirthdayBean bean,BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(MyService.class)
                .updateAccountBirthday(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void updateHeadPhoto(RequestUpdateHeadPhotoBean bean, BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(MyService.class)
                .updateAccountHeadPhoto(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

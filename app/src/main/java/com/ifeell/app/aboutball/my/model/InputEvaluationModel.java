package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestInputEvaluationBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:43
 * 更新时间: 2019/5/27 10:43
 * 描述:填写评价model
 */
public class InputEvaluationModel extends BaseModel {
    public void postEvaluationOpponent(RequestInputEvaluationBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .postEvaluateOpponent(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void postEvaluationReferee(RequestInputEvaluationBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .postEvaluateReferee(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

package com.work.guaishouxingqiu.aboutball.login.model;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.login.LoginService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 10:44
 * 更新时间: 2019/3/11 10:44
 * 描述: 发送短信model
 */
public class MessageModel extends BaseModel {

    public void sendMessageCode(@NonNull String phoneNumber, int type, BaseObserver observer) {
        mRetrofitManger.create(LoginService.class)
                .sendMessageCode(phoneNumber, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void countDownTime(int timeLength, Observer<Long> observer) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(timeLength)
                .map(period -> timeLength - period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void judgeMessageCode(String phone, String messageCode, BaseObserver observer) {
        mRetrofitManger.create(LoginService.class)
                .judgeMessageCode(phone, messageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

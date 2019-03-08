package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.http.RetrofitManger;
import com.work.guaishouxingqiu.aboutball.login.LoginService;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 10:53
 * 更新时间: 2019/3/6 10:53
 * 描述:
 */
public class BaseModel {

    protected RetrofitManger mRetrofitManger;


    public BaseModel() {

        if (mRetrofitManger == null) {
              mRetrofitManger = RetrofitManger.getDefault();
        }
    }

    public void sendMessageCode(@NonNull String phoneNumber,int type,BaseObserver observer){
        mRetrofitManger.create(LoginService.class)
                .sendMessageCode(phoneNumber,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}

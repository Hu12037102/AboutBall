package com.work.guaishouxingqiu.aboutball.my.model;

import android.os.Build;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 15:28
 * 更新时间: 2019/4/1 15:28
 * 描述:设置Model
 */
public class SettingModel extends BaseModel {
    public void getFileSize(Observer<Long> observable) {
       /* Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            Long fileSize = FileUtils.getFileSize(FileUtils.getNetCacheFile()) +
                    FileUtils.getFileSize(FileUtils.getImageCacheFile());
            emitter.onNext(fileSize);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);*/
        Observable.just(FileUtils.getFileSize(FileUtils.getNetCacheFile()) +
                FileUtils.getFileSize(FileUtils.getImageCacheFile()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }

    public void updateApkInfo(String version, BaseObserver<BaseDataBean<ResultUpdateApkBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .updateApkInfo(Contast.ANDROID, version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}

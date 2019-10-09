package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.util.FileUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
            Long fileSize = FileUtils.getFilesSize(FileUtils.getNetCacheFile()) +
                    FileUtils.getFilesSize(FileUtils.getImageCacheFile());
            emitter.onNext(fileSize);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);*/
        Observable.just(FileUtils.getFilesSize(FileUtils.getNetCacheFile()) +
                FileUtils.getFilesSize(FileUtils.getImageCacheFile()))
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

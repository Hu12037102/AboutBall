package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my.model
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}关于我们model
 *
 * @author ：
 */
public class AboutWeModel extends BaseModel {

    public void updateApkInfo(String version, BaseObserver<BaseDataBean<ResultUpdateApkBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .updateApkInfo(Contast.ANDROID, version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

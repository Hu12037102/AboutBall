package com.ifeell.app.aboutball.venue.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultVenueEvaluateBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.presenter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评价model
 *
 * @author ：
 */
public class VenueEvaluateModel extends BaseModel {
    public void loadVenueEvaluate(long areaId, int flag, int pageNum, int pageSize, BaseObserver<ResultVenueEvaluateBean> observer) {
        mRetrofitManger.create(VenueService.class)
                .loadVenueEvaluate(areaId, flag, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

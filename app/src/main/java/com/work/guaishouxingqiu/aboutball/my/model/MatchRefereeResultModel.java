package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestActionRecordsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordDetailsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:09
 * 更新时间: 2019/6/4 13:09
 * 描述:赛况记录model
 */
public class MatchRefereeResultModel extends BaseModel {
    public void loadMatchRecord(long agreeId, BaseObserver<List<ResultMatchRefereeResultBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .getRefereeMatchRecordData(agreeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void goActionRecord(RequestActionRecordsBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .goActionRecord(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadRecordDetails(long agreeId,BaseObserver<ResultRefereeRecordDetailsBean>observer) {
        mRetrofitManger.create(MyService.class)
                .getRefereeRecordDetails(agreeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

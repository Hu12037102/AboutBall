package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultInputEvaluationBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 11:40
 * 更新时间: 2019/5/27 11:40
 * 描述:发表评论model
 */
public class PostEvaluationModel extends BaseModel {
    public void loadTeamEvaluation(long teamId, BaseObserver<List<ResultInputEvaluationBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .getTeamEvaluation(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadRefereeEvaluation(long refereeId, BaseObserver<List<ResultInputEvaluationBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .getRefereeEvaluation(refereeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadMyRefereeEvaluation(BaseObserver<List<ResultInputEvaluationBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyRefereeEvaluation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

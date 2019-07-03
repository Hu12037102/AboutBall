package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultReviewBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 11:21
 * 更新时间: 2019/6/27 11:21
 * 描述:赛事回顾model
 */
public class MatchReviewModel extends BaseModel {
    public void loadMatchReviewData(int pageNum,int pageSize,BaseObserver<List<ResultReviewBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadMatchReviewData(pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameScheduleBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:32
 * 更新时间: 2019/6/11 9:32
 * 描述:赛程Model
 */
public class GameScheduleModel extends BaseModel{
    public void loadScheduleData(String requestTime, BaseObserver<List<ResultGameScheduleBean>>observer) {
        mRetrofitManger.create(GameService.class)
                .loadGameScheduleData(requestTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

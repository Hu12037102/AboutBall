package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.ScheduleContract;
import com.work.guaishouxingqiu.aboutball.home.model.ScheduleModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:32
 * 更新时间: 2019/6/11 9:32
 * 描述:赛程P
 */
public class SchedulePresenter extends BasePresenter<ScheduleContract.View, ScheduleModel>
        implements ScheduleContract.Presenter {
    public SchedulePresenter(@NonNull ScheduleContract.View view) {
        super(view);
    }

    @Override
    protected ScheduleModel createModel() {
        return new ScheduleModel();
    }

    @Override
    public void start() {

    }
}

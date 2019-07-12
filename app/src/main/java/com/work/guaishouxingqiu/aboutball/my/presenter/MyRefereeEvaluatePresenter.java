package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.MyRefereeEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyRefereeEvaluateModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:33
 * 更新时间: 2019/5/9 10:33
 * 描述:我的裁判评价P
 */
public class MyRefereeEvaluatePresenter extends BasePresenter<MyRefereeEvaluateContract.View,
        MyRefereeEvaluateModel>implements MyRefereeEvaluateContract.Presenter{

    public MyRefereeEvaluatePresenter(@NonNull MyRefereeEvaluateContract.View view) {
        super(view);
    }

    @Override
    protected MyRefereeEvaluateModel createModel() {
        return new MyRefereeEvaluateModel();
    }

    @Override
    public void start() {

    }
}

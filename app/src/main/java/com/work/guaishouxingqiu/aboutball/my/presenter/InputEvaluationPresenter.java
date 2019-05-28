package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.InputEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.model.InputEvaluationModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:43
 * 更新时间: 2019/5/27 10:43
 * 描述:填写评价P
 */
public class InputEvaluationPresenter extends BasePresenter<InputEvaluationContract.View,
        InputEvaluationModel>implements InputEvaluationContract.Presenter{
    public InputEvaluationPresenter(@NonNull InputEvaluationContract.View view) {
        super(view);
    }

    @Override
    protected InputEvaluationModel createModel() {
        return new InputEvaluationModel();
    }

    @Override
    public void start() {

    }
}

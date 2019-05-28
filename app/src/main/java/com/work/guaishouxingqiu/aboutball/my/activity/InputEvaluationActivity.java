package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.InputEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.InputEvaluationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:44
 * 更新时间: 2019/5/27 10:44
 * 描述:填写评价Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_INPUT_EVALUATION)
public class InputEvaluationActivity extends BaseActivity<InputEvaluationPresenter> implements InputEvaluationContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_evaluation;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected InputEvaluationPresenter createPresenter() {
        return new InputEvaluationPresenter(this);
    }
}

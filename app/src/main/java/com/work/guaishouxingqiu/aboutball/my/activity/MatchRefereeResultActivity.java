package com.work.guaishouxingqiu.aboutball.my.activity;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MatchRefereeResultPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:39
 * 更新时间: 2019/6/4 13:39
 * 描述:赛况记录Activity
 */
public class MatchRefereeResultActivity extends BaseActivity<MatchRefereeResultPresenter> implements
        MatchRefereeResultContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_result;
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
    protected MatchRefereeResultPresenter createPresenter() {
        return new MatchRefereeResultPresenter(this);
    }
}

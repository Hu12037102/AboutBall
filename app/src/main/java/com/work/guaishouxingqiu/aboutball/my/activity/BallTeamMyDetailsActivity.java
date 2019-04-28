package com.work.guaishouxingqiu.aboutball.my.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamMyDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:31
 * 更新时间: 2019/4/28 15:31
 * 描述:对内个人信息Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_BALL_TEAM_MY_DETAILS)
public class BallTeamMyDetailsActivity extends BaseActivity<BallTeamMyDetailsPresenter> implements
        BallTeamMyDetailsContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_my_details;
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
    protected BallTeamMyDetailsPresenter createPresenter() {
        return new BallTeamMyDetailsPresenter(this);
    }
}

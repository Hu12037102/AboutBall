package com.work.guaishouxingqiu.aboutball.my.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsChildContract;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamDetailsChildPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:34
 * 更新时间: 2019/4/25 16:34
 * 描述:球队信息fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_BALL_TEAM_DETAILS)
public class BallTeamDetailsFragment extends BaseFragment<BallTeamDetailsChildPresenter> implements BallTeamDetailsChildContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ball_team_details;
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
    protected BallTeamDetailsChildPresenter createPresenter() {
        return new BallTeamDetailsChildPresenter(this);
    }
}

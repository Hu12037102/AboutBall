package com.work.guaishouxingqiu.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.MatchResultContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.MatchResultPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:35
 * 更新时间: 2019/3/25 9:35
 * 描述: 赛况Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_RESULT)
public class GameResultFragment extends BaseFragment<MatchResultPresenter> implements MatchResultContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_result;
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
    protected MatchResultPresenter createPresenter() {
        return new MatchResultPresenter(this);
    }
}

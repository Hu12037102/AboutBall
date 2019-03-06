package com.work.guaishouxingqiu.aboutball.game.fragment;


import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.contract.GameContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GamePresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:06
 * 更新时间: 2019/3/6 13:06
 * 描述: 比赛Fragment
 */
public class GameFragment extends BaseFragment<GamePresenter> implements GameContract.View {
    public static GameFragment newInstance() {
        return new GameFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
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
    protected GamePresenter createPresenter() {
        return new GamePresenter(this);
    }
}

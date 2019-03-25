package com.work.guaishouxingqiu.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDataPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:43
 * 更新时间: 2019/3/25 9:43
 * 描述:比赛数据fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_DATA)
public class GameDataFragment extends BaseFragment<GameDataPresenter>implements GameDataContract.View{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_data;
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
    protected GameDataPresenter createPresenter() {
        return new GameDataPresenter(this);
    }
}

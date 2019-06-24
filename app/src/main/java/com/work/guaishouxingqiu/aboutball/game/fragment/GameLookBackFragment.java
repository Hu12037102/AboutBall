package com.work.guaishouxingqiu.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.GameLookBackContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameLookBackPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:06
 * 更新时间: 2019/6/24 11:06
 * 描述:比赛-回顾-fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_LOOK_BACK)
public class GameLookBackFragment extends BaseFragment<GameLookBackPresenter>
implements GameLookBackContract.View{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_look_back;
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
    protected GameLookBackPresenter createPresenter() {
        return new GameLookBackPresenter(this);
    }
}

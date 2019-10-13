package com.ifeell.app.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.game.contract.GameTeachContract;
import com.ifeell.app.aboutball.game.presenter.GameTeachPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:37
 * 更新时间: 2019/3/15 10:37
 * 描述: 比赛- 教学-fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_TEACH)
public class GameTeachFragment extends BaseFragment<GameTeachPresenter> implements GameTeachContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_teach;
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
    protected GameTeachPresenter createPresenter() {
        return new GameTeachPresenter(this);
    }
}

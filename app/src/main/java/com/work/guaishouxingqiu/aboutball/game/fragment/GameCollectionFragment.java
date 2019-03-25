package com.work.guaishouxingqiu.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCollectionContract;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameCollectionPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:22
 * 更新时间: 2019/3/25 10:22
 * 描述:比赛-集锦fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_COLLECTION)
public class GameCollectionFragment extends BaseFragment<GameCollectionPresenter> implements
        GameCollectionContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_collection;
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
    protected GameCollectionPresenter createPresenter() {
        return new GameCollectionPresenter(this);
    }
}

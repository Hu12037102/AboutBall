package com.work.guaishouxingqiu.aboutball.game.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameCommentPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:54
 * 更新时间: 2019/3/25 9:54
 * 描述:比赛评论Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_COMMENT)
public class GameCommentFragment extends BaseFragment<GameCommentPresenter> implements
        GameCommentContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_comment;
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
    protected GameCommentPresenter createPresenter() {
        return new GameCommentPresenter(this);
    }
}

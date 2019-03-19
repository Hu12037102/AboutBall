package com.work.guaishouxingqiu.aboutball.community.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityNewsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityNewsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 14:06
 * 更新时间: 2019/3/19 14:06
 * 描述:社区-最新fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_NEWS)
public class CommunityNewFragment extends DelayedFragment<CommunityNewsPresenter>
        implements CommunityNewsContract.View {
    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_news;
    }

    @Override
    protected CommunityNewsPresenter createPresenter() {
        return new CommunityNewsPresenter(this);
    }
}

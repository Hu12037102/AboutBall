package com.work.guaishouxingqiu.aboutball.community.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityRecommendContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityRecommendPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:58
 * 更新时间: 2019/3/19 13:58
 * 描述:社区-推荐Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_RECOMMEND)
public class CommunityRecommendFragment extends DelayedFragment<CommunityRecommendPresenter>
        implements CommunityRecommendContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_recommend;
    }

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
    protected CommunityRecommendPresenter createPresenter() {
        return new CommunityRecommendPresenter(this);
    }
}

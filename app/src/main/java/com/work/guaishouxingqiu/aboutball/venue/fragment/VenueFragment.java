package com.work.guaishouxingqiu.aboutball.venue.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.community.fragment.CommunityFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenuePresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:19
 * 更新时间: 2019/3/6 15:19
 * 描述:场馆Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VENUE)
public class VenueFragment extends BaseFragment<VenuePresenter>implements VenueContract.View {
    public static VenueFragment newInstance() {
        return new VenueFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_venue;
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
    protected VenuePresenter createPresenter() {
        return new VenuePresenter(this) ;
    }
}

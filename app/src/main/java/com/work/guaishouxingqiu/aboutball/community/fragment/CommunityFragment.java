package com.work.guaishouxingqiu.aboutball.community.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityPresenter;
import com.work.guaishouxingqiu.aboutball.home.fragment.HomeFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:39
 * 更新时间: 2019/3/6 15:39
 * 描述: 社区Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY)
public class CommunityFragment extends BaseFragment<CommunityPresenter>implements CommunityContract.View{
    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragmenyt_community;
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
    protected CommunityPresenter createPresenter() {
        return new CommunityPresenter(this);
    }
}

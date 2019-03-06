package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.HomeContract;
import com.work.guaishouxingqiu.aboutball.home.model.HomeModel;
import com.work.guaishouxingqiu.aboutball.home.presenter.HomePresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:04
 * 更新时间: 2019/3/6 13:04
 * 描述: 首页Fragment
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }
}

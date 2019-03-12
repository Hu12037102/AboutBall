package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.HotContract;
import com.work.guaishouxingqiu.aboutball.home.model.HotModel;
import com.work.guaishouxingqiu.aboutball.home.presenter.HotPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:46
 * 更新时间: 2019/3/12 17:46
 * 描述: 热点Fragment
 */
public class HotFragment extends BaseFragment<HotPresenter> implements HotContract.View{

    public static HotFragment newInstance() {
        return new HotFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
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
    protected HotPresenter createPresenter() {
        return new HotPresenter(this);
    }
}

package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.SpecialContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.SpecialPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:05
 * 更新时间: 2019/3/12 18:05
 * 描述: 专栏Fragment
 */
public class SpecialFragment extends BaseFragment<SpecialPresenter>
implements SpecialContract.View{

    public static SpecialFragment newInstance() {
        return new SpecialFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_special;
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
    protected SpecialPresenter createPresenter() {
        return new SpecialPresenter(this);
    }
}

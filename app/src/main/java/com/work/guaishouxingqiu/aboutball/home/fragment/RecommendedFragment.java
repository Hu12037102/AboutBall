package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.RecommendedContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.RecommendedPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:37
 * 更新时间: 2019/3/12 17:37
 * 描述: 推荐Fragment
 */
public class RecommendedFragment extends BaseFragment<RecommendedPresenter>implements RecommendedContract.View{
    public static RecommendedFragment newInstance() {
        return new RecommendedFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
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
    protected RecommendedPresenter createPresenter() {
        return null;
    }
}

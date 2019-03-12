package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.HighlightsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.HighlightsPresenter;
import com.youth.banner.Banner;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:57
 * 更新时间: 2019/3/12 17:57
 * 描述:集锦Fragment
 */
public class HighlightsFragment extends BaseFragment<HighlightsPresenter>
implements HighlightsContract.View{
    public static HighlightsFragment newInstance() {
        return new HighlightsFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_highlights;

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
    protected HighlightsPresenter createPresenter() {
        return new HighlightsPresenter(this);
    }
}

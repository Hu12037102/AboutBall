package com.work.guaishouxingqiu.aboutball.home.fragment;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.VideoContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.VideoPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:10
 * 更新时间: 2019/3/12 18:10
 * 描述:视频Fragment
 */
public class VideoFragment extends BaseFragment<VideoPresenter>
        implements VideoContract.View {
    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
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
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }
}

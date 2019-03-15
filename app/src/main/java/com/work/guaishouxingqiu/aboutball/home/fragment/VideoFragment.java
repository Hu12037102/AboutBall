package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.VideoContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.VideoPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:10
 * 更新时间: 2019/3/12 18:10
 * 描述:视频Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VIDEO)
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

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {

    }
}

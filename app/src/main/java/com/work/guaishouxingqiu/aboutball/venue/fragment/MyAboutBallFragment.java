package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallChildContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.MyAboutBallChildPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:57
 * 更新时间: 2019/5/22 17:57
 * 描述:我的约球fragment
 */
public class MyAboutBallFragment extends DelayedFragment<MyAboutBallChildPresenter> implements
        MyAboutBallChildContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    Unbinder unbinder;

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
    protected int getLayoutId() {
        return R.layout.fragment_my_about_ball;
    }

    @Override
    protected MyAboutBallChildPresenter createPresenter() {
        return new MyAboutBallChildPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

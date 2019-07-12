package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.AboutBallDetailsActivity;
import com.work.guaishouxingqiu.aboutball.venue.adapter.MyAboutBallAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyAboutBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallChildContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.MyAboutBallChildPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:57
 * 更新时间: 2019/5/22 17:57
 * 描述:我的约球fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_ABOUT_BALL)
public class MyAboutBallFragment extends DelayedFragment<MyAboutBallChildPresenter> implements
        MyAboutBallChildContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mFlag;
    private MyAboutBallAdapter mAdapter;
    private List<ResultMyAboutBean> mData;

    @Override
    protected void initDelayedView() {
        if (mFlag == Contast.AboutBallFlag.PARTICIPATION) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    private void loadData(RefreshLayout refreshLayout, boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.loadData(mFlag);
    }

    @Override
    protected void initView() {
        super.initView();
        mFlag = mBundle.getInt(ARouterConfig.Key.ABOUT_BALL_FLAG, -1);
        if (mFlag == -1) {
            UIUtils.showToast(R.string.not_this_find_about_ball);
            DataUtils.checkData(getActivity()).finish();
            return;
        }
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        mAdapter = new MyAboutBallAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mFlag == Contast.AboutBallFlag.PUBLISH) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlRefresh.setOnRefreshListener(refreshLayout -> loadData(refreshLayout, true));
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onNotDataClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onItemClick(View view, int position) {
                startActivityToAboutBallDetails(mData.get(position));
            }
        });
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
    public void resultData(List<ResultMyAboutBean> data) {
        if (mData.size() > 0) {
            mData.clear();
        }
        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    mSrlRefresh.autoRefresh();
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * flag 0:查看、参加约球裁判 1：取消约球
     *
     * @param bean
     * @param
     */
    private void startActivityToAboutBallDetails(ResultMyAboutBean bean) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.OFFER_ID, bean.agreeId);
        bundle.putInt(ARouterConfig.Key.ABOUT_BALL_FLAG, mFlag);
        ARouterIntent.startActivityForResult(this, AboutBallDetailsActivity.class, bundle);
    }
}

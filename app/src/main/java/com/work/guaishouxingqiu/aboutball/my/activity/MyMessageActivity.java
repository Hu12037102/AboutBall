package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.MyMessageAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyMessageBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyMessageContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyMessagePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/31 15:01
 * 更新时间: 2019/7/31 15:01
 * 描述:我的消息页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_MESSAGE)
public class MyMessageActivity extends BaseActivity<MyMessagePresenter> implements MyMessageContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    private List<ResultMyMessageBean> mData;
    private MyMessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new MyMessageAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlLayout.autoRefresh();
    }

    private void loadData(boolean isRefresh) {
        if (isRefresh) {
            mSrlLayout.finishRefresh();
        } else {
            mSrlLayout.finishLoadMore();
        }
        mPresenter.getMyMessageList();
    }

    @Override
    protected void initEvent() {
        mSrlLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
    }

    @Override
    public void resultMyMessageList(List<ResultMyMessageBean> data) {
        if (mData.size() > 0){
            mData.clear();
        }
        if (data.size() > 0){
            mData.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected MyMessagePresenter createPresenter() {
        return new MyMessagePresenter(this);
    }


}

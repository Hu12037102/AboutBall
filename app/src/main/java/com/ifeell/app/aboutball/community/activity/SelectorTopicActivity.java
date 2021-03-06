package com.ifeell.app.aboutball.community.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.community.adapter.SelectorTopicAdapter;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;
import com.ifeell.app.aboutball.community.contract.SelectorTopicContract;
import com.ifeell.app.aboutball.community.presenter.SelectorTopicPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:26
 * 更新时间: 2019/6/14 18:26
 * 描述:选择话题Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SELECTOR_TOPIC)
public class SelectorTopicActivity extends BaseActivity<SelectorTopicPresenter> implements SelectorTopicContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<ResultTopicBean> mData;
    private SelectorTopicAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selector_topic;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new SelectorTopicAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadTopicData();
                refreshLayout.finishRefresh();
            }
        });
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
                Intent intent = new Intent();
                intent.putExtra(ARouterConfig.Key.PARCELABLE, mData.get(position));
                mViewModel.clickBackForResult(intent);
            }
        });
    }

    @Override
    protected SelectorTopicPresenter createPresenter() {
        return new SelectorTopicPresenter(this);
    }


    @Override
    public void resultTopicData(List<ResultTopicBean> data) {
        if (mData.size() > 0) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyData(NetWorkUtils.isNetCanUse());

    }
}

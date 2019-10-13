package com.ifeell.app.aboutball.home.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.home.adapter.RecommendedAdapter;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.home.contract.VideoContract;
import com.ifeell.app.aboutball.home.presenter.VideoPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:10
 * 更新时间: 2019/3/12 18:10
 * 描述:视频Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VIDEO)
public class VideoFragment extends DelayedFragment<VideoPresenter>
        implements VideoContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mTypId;
    private List<ResultNewsBean> mData;
    private RecommendedAdapter mAdapter;


    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
        mPresenter.loadData(mTypId);
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = false;
                mPresenter.loadData(mTypId);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = true;
                mPresenter.loadData(mTypId);
                refreshLayout.finishRefresh();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                ResultNewsBean bean = mData.get(position);
                if (!bean.isRead) {
                    DataUtils.putNewsKey(bean.newsId);
                    mAdapter.notifyDataSetChanged();
                }
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(position).newsId);
            }
        });
    }


    @Override
    protected void initPermission() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mTypId = bundle.getInt(ARouterConfig.Key.TAB_TYPE_ID);
        super.initPermission();
    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mSrlRefresh.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * home页面选择tab消息给首页控制视频
     */
    public static class MessageTabBean {
        public int selectorTab;

        public MessageTabBean(int selectorTab) {
            this.selectorTab = selectorTab;
        }
    }


}

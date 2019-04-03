package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.home.activity.MainActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.VideoContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.VideoPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        registerEventBus();
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

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null && getUserVisibleHint()) {
            mAdapter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.onDestroy();
        }
        unRegisterEventBus();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mAdapter != null) {
            if (isVisibleToUser) {

                mAdapter.onResume();
            } else {
                mAdapter.onPause();
            }
        }
    }

    @Subscribe
    public void messageToTabVideo(VideoFragment.MessageTabBean bean) {
        if (mAdapter != null) {
            if (bean.selectorTab != 0) {
                mAdapter.onPause();
            } else {
                mAdapter.onResume();
            }
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

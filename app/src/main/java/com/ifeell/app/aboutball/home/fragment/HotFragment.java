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
import com.ifeell.app.aboutball.home.contract.HotContract;
import com.ifeell.app.aboutball.home.presenter.HotPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:46
 * 更新时间: 2019/3/12 17:46
 * 描述: 热点Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_HOT)
public class HotFragment extends DelayedFragment<HotPresenter> implements HotContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrLayout;
    private List<ResultNewsBean> mData;
    private RecommendedAdapter mAdapter;
    private int mTypId;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
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
    protected void initDelayedView() {
        mRvList.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mRvList.setAdapter(mAdapter);
        mSrLayout.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    protected HotPresenter createPresenter() {
        return new HotPresenter(this);
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mSrLayout.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mData.addAll(bean.result);
         /*   if (mAdapter.isHaveFootView){
                mAdapter.removeFootView();
            }else if (bean.result.size()<mPresenter.mPageSize){
                mAdapter.addFootView(UIUtils.loadNotMoreView((ViewGroup) mRootView));
            }*/
            mAdapter.notifyDataSetChanged();
        }
    }

}

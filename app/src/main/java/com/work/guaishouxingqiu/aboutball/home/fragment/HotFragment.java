package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.HotContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.HotPresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:46
 * 更新时间: 2019/3/12 17:46
 * 描述: 热点Fragment
 */
public class HotFragment extends BaseFragment<HotPresenter> implements HotContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrLayout;
    private List<ResultNewsBean> mData;
    private RecommendedAdapter mAdapter;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initView() {
        mRvList.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mRvList.setAdapter(mAdapter);
        mSrLayout.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = false;
                mPresenter.start();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = true;
                mPresenter.start();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected HotPresenter createPresenter() {
        return new HotPresenter(this);
    }

    @Override
    public void resultNewsData(BaseBean<List<ResultNewsBean>> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mSrLayout.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }
}

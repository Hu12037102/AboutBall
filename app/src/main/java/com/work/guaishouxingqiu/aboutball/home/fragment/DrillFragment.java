package com.work.guaishouxingqiu.aboutball.home.fragment;

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
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.home.adapter.DrillTabAdapter;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.DrillContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.DrillPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/10 15:07
 * 更新时间: 2019/5/10 15:07
 * 描述:训练fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_DRILL)
public class DrillFragment extends DelayedFragment<DrillPresenter> implements DrillContract.View {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrLayout;
    @BindView(R.id.rv_tab)
    RecyclerView mRvTab;
    private List<ResultNewsBean> mData;
    private RecommendedAdapter mAdapter;
    private ResultHomeTabBean mHomeTabBean;
    private int mTypId;
    private DrillTabAdapter mTabAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_drill;
    }

    @Override
    protected DrillPresenter createPresenter() {
        return new DrillPresenter(this);
    }

    public static SpecialFragment newInstance() {
        return new SpecialFragment();
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mSrLayout.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initPermission() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mHomeTabBean = bundle.getParcelable(ARouterConfig.Key.TAB_TYPE_ID);
        if (mHomeTabBean == null) {
            getActivity().finish();
            return;
        }

        super.initPermission();
    }

    @Override
    protected void initDelayedView() {
        mRvList.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
        mRvTab.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initDelayedData() {
        if (mHomeTabBean.homeLabelList.size() > 0) {
            mHomeTabBean.homeLabelList.get(0).isCheck = true;
            mTypId = mHomeTabBean.homeLabelList.get(0).labelId;
        } else {
            mTypId = mHomeTabBean.parentLabelId;
        }
        mTabAdapter = new DrillTabAdapter(mContext, mHomeTabBean.homeLabelList);
        mRvTab.setAdapter(mTabAdapter);

        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mRvList.setAdapter(mAdapter);
        mSrLayout.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mTabAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mTypId = mTabAdapter.getSelectorTabId();
                mSrLayout.autoRefresh();
            }
        });
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

}

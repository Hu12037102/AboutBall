package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueEvaluateAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueEvaluateTypeAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueEvaluateBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueEvaluateTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueEvaluateContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueEvaluatePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.activity
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评价Activity
 *
 * @author ：
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_EVALUATE)
public class VenueEvaluateActivity extends BaseActivity<VenueEvaluatePresenter> implements VenueEvaluateContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_type)
    RecyclerView mRvType;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private long mAreaId;
    private VenueEvaluateTypeAdapter mTypeAdapter;
    private List<ResultVenueEvaluateTypeBean> mDataType;
    private VenueEvaluateAdapter mEvaluateAdapter;
    private List<ResultVenueEvaluateBean.ChildEvaluateBean> mEvaluateData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_evaluate;
    }

    @Override
    public void initPermission() {
        mAreaId = mIntent.getLongExtra(ARouterConfig.Key.AREA_ID, -1);
        if (mAreaId == -1) {
            UIUtils.showToast(R.string.not_find_this_venue_evaluate);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mRvType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mDataType = ResultVenueEvaluateTypeBean.getDefaultData();
        mTypeAdapter = new VenueEvaluateTypeAdapter(this, mDataType);
        mRvType.setAdapter(mTypeAdapter);

        mEvaluateData = new ArrayList<>();
        mEvaluateAdapter = new VenueEvaluateAdapter(mEvaluateData);
        mRvData.setAdapter(mEvaluateAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(refreshLayout, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(refreshLayout, true);
            }
        });
        mTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mSrlRefresh.autoRefresh();
            }
        });
    }

    private void loadData(RefreshLayout refreshLayout, boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.isRefresh = isRefresh;
        mPresenter.loadVenueEvaluate(mAreaId, mDataType.get(mTypeAdapter.getCheckPosition()).flag);
    }

    @Override
    protected VenueEvaluatePresenter createPresenter() {
        return new VenueEvaluatePresenter(this);
    }


    @Override
    public void resultVenueEvaluate(ResultVenueEvaluateBean bean) {
        for (int i = 0; i < mDataType.size(); i++) {
            ResultVenueEvaluateTypeBean typeBean = mDataType.get(i);
            switch (i) {
                case 0:
                    typeBean.count = bean.totalCount;
                    break;
                case 1:
                    typeBean.count = bean.picCount;
                    break;
                case 2:
                    typeBean.count = bean.noPicCount;
                    break;
                default:
                    break;
            }

        }
        mTypeAdapter.notifyDataSetChanged();
        if (mPresenter.isRefresh) {
            mEvaluateData.clear();
        }
        if (bean.orderCommentForAreaList.size() > 0 && bean.orderCommentForAreaList.get(0) != null) {
            mEvaluateData.addAll(bean.orderCommentForAreaList.get(0).orderCommentForAreaSimpleList);
            mSrlRefresh.setNoMoreData(bean.orderCommentForAreaList.get(0).orderCommentForAreaSimpleList.size() < mPresenter.mPageSize);
        } else {
            mSrlRefresh.setNoMoreData(true);
        }

        mEvaluateAdapter.notifyDataSetChanged();
    }
}

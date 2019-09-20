package com.work.guaishouxingqiu.aboutball.order.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.order.activity.MyGoodActivity;
import com.work.guaishouxingqiu.aboutball.order.adapter.MyGoodAdapter;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultMyGoodBean;
import com.work.guaishouxingqiu.aboutball.order.contract.MyGoodContract;
import com.work.guaishouxingqiu.aboutball.order.presenter.MyGoodPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 14:59
 * 更新时间: 2019/9/19 14:59
 * 描述:我的订单fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_GOOD)
public class MyGoodFragment extends DelayedFragment<MyGoodPresenter> implements MyGoodContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private MyGoodAdapter mAdapter;
    private List<ResultMyGoodBean> mData;
    private int mStatus;
    public static final int REQUEST_CODE_TO_GOOD_DETAILS = 1259;

    @Override
    protected void initPermission() {
        mStatus = mBundle.getInt(ARouterConfig.Key.GOOD_STATUS, -1);
        if (mStatus == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            getBaseActivity().finish();
            return;
        }
        super.initPermission();

    }

    @Override
    protected void initDelayedView() {
        if (mStatus != 0) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }


    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        mAdapter = new MyGoodAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mStatus == 0) {
            mSrlRefresh.autoRefresh();
        }


    }

    @Override
    protected void loadRefreshData(boolean isRefresh) {
        super.loadRefreshData(isRefresh);
        mPresenter.loadMyGoodList(mStatus);
    }

    @Override
    protected SmartRefreshLayout getRefreshLayout() {
        return mSrlRefresh;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
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

            }
        });
        mAdapter.setOnClickMyGoodListener(new MyGoodAdapter.OnClickMyGoodListener() {
            @Override
            public void onClickDetails(View view, int position) {
               mViewModel.startGoodDetailsActivityForResult(MyGoodFragment.this, MyGoodFragment.REQUEST_CODE_TO_GOOD_DETAILS,mData.get(position).orderId);
            }

            @Override
            public void onClickOperation(View view, int position) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_good;
    }

    @Override
    protected MyGoodPresenter createPresenter() {
        return new MyGoodPresenter(this);
    }

    @Override
    public void resultMyGoodList(List<ResultMyGoodBean> data) {
        if (mData.size() > 0 && mPresenter.isRefresh) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mSrlRefresh.setEnableLoadMore(data.size() == mPresenter.mPageSize);
        mAdapter.notifyData(data.size() == mPresenter.mPageSize);

    }

}

package com.work.guaishouxingqiu.aboutball.good.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.game.activity.GameScheduleActivity;
import com.work.guaishouxingqiu.aboutball.good.activity.GoodDetailsActivity;
import com.work.guaishouxingqiu.aboutball.good.adapter.MyGoodAdapter;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultMyGoodBean;
import com.work.guaishouxingqiu.aboutball.good.contract.MyGoodContract;
import com.work.guaishouxingqiu.aboutball.good.presenter.MyGoodPresenter;
import com.work.guaishouxingqiu.aboutball.my.activity.OrderRefundDetailsActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

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
    private static final int REQUEST_CODE_REFUND_DETAIL = 1258;
    private static final int REQUEST_CODE_REFUND_SCHEDULE = 1260;
    private int mClickOperationPosition = -1;

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
                mViewModel.startGoodDetailsActivityForResult(MyGoodFragment.this, MyGoodFragment.REQUEST_CODE_TO_GOOD_DETAILS, mData.get(position).orderId);
            }

            @Override
            public void onClickOperation(View view, int position) {
                ResultMyGoodBean bean = mData.get(position);
                mClickOperationPosition = position;
                mPresenter.getCheckOutOrderStatus(bean.orderId);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MyGoodFragment.REQUEST_CODE_REFUND_DETAIL:
                case MyGoodFragment.REQUEST_CODE_TO_GOOD_DETAILS:
                case MyGoodFragment.REQUEST_CODE_REFUND_SCHEDULE:
                    mSrlRefresh.autoRefresh();
                    break;
                default:
                    break;
            }

        }
    }

    private void startGoodDetailsActivity(int newStatus) {
        ResultMyGoodBean bean = mData.get(mClickOperationPosition);
        switch (newStatus) {
            //待支付
            case Contast.MyGoodStatus.WAIT_PAY:
                mViewModel.startGoodDetailsActivityForResult(MyGoodFragment.this, MyGoodFragment.REQUEST_CODE_TO_GOOD_DETAILS, bean.orderId);
                //  mViewModel.showPayDialog(DataUtils.getMoneyFormat(bean.amount), view1 -> mPresenter.payWeiChatSing(mResultBean.id));
                break;
            //已付款
            case Contast.MyGoodStatus.PAYING:
                mViewModel.startGoodRefundDetailActivityForResult(MyGoodFragment.this, MyGoodFragment.REQUEST_CODE_REFUND_DETAIL, bean.orderId);
                break;
            //已完成
            case Contast.MyGoodStatus.COMPLETE:
                break;
            //已取消
            case Contast.MyGoodStatus.CANCEL:
                break;
            //退款中
            case Contast.MyGoodStatus.REFUNDING:
                ARouterIntent.startActivityForResult(this, OrderRefundDetailsActivity.class, ARouterConfig.Key.ORDER_ID, bean.orderId, ARouterConfig.Key.ORDER_FLAG, 1, MyGoodFragment.REQUEST_CODE_REFUND_SCHEDULE);
                break;
            //已退款
            case Contast.MyGoodStatus.REFUNDED:
                break;
            default:
                break;
        }
    }

    @Override
    public void resultGoodStatus(int goodStatus) {
        ResultMyGoodBean bean = mData.get(mClickOperationPosition);
        if (goodStatus == bean.status) {
            startGoodDetailsActivity(goodStatus);
        } else {
            mViewModel.showGoodStatusErrorDialog(goodStatus, view -> {
                startGoodDetailsActivity(goodStatus);
            });
        }
    }
}

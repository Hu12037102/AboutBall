package com.work.guaishouxingqiu.aboutball.my.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.my.adapter.DynamicNotificationAdapter;
import com.work.guaishouxingqiu.aboutball.my.adapter.SystemNotificationAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultDynamicNotificationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyMessageBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultSystemNotificationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MessageNotificationContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MessageNotificationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/1 9:43
 * 更新时间: 2019/8/1 9:43
 * 描述:系统通知activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MESSAGE_NOTIFICATION)
public class MessageNotificationActivity extends BaseActivity<MessageNotificationPresenter> implements
        MessageNotificationContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    private ResultMyMessageBean mIntentBean;
    private List<ResultDynamicNotificationBean> mDynamicData;
    private DynamicNotificationAdapter mDynamicAdapter;
    private List<ResultSystemNotificationBean> mSystemData;
    private SystemNotificationAdapter mSystemAdapter;
    private boolean isFirst = true;
    private boolean isCanBack = true;

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_notification);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_notification;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        UIUtils.setText(mTitleView.mTvCenter, mIntentBean.noticeTypeName);
    }

    private void clickBack() {
        if (isCanBack) {
            mViewModel.clickBackForResult();
        }
    }

    @Override
    protected void initData() {
        if (mIntentBean.noticeType == 4) {
            mDynamicData = new ArrayList<>();
            mDynamicAdapter = new DynamicNotificationAdapter(mDynamicData);
            mRvData.setAdapter(mDynamicAdapter);
            mDynamicAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onNotNetClick(View view) {
                    mSrlLayout.autoRefresh();
                }

                @Override
                public void onNotDataClick(View view) {
                    mSrlLayout.autoRefresh();
                }

                @Override
                public void onItemClick(View view, int position) {

                }
            });
        } else {
            mSystemData = new ArrayList<>();
            mSystemAdapter = new SystemNotificationAdapter(mSystemData);
            mRvData.setAdapter(mSystemAdapter);
            mSystemAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onNotNetClick(View view) {
                    mSrlLayout.autoRefresh();
                }

                @Override
                public void onNotDataClick(View view) {
                    mSrlLayout.autoRefresh();
                }

                @Override
                public void onItemClick(View view, int position) {

                }
            });
        }
        mSrlLayout.autoRefresh();
    }

    private void clearRedPoint() {
        if (isFirst) {
            isCanBack = false;
            mPresenter.clearRedPoint(mIntentBean.noticeType);
            isFirst = false;
        }
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mSrlLayout.finishRefresh();
        } else {
            mSrlLayout.finishLoadMore();
        }
        if (mIntentBean.noticeType == 4) {
            mPresenter.getDynamicNotificationList(mIntentBean.noticeType);
        } else {
            mPresenter.getSystemNotificationList(mIntentBean.noticeType);
        }
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
        mTitleView.setOnBackViewClickListener(view -> clickBack());
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    @Override
    public void resultSystemNotificationData(List<ResultSystemNotificationBean> data) {
        if (mSystemData.size() > 0 && mPresenter.isRefresh) {
            mSystemData.clear();
        }
        if (data.size() > 0) {
            mSystemData.addAll(data);
        }
        mViewModel.setRefreshViewMoreStatus(mSrlLayout, data, mPresenter.mPageSize);
        mSystemAdapter.setNotifyData(data.size() < mPresenter.mPageSize);
        clearRedPoint();
    }

    @Override
    public void resultDynamicNotificationData(List<ResultDynamicNotificationBean> data) {
        if (mDynamicData.size() > 0 && mPresenter.isRefresh) {
            mDynamicData.clear();
        }
        if (data.size() > 0) {
            mDynamicData.addAll(data);
        }
        mViewModel.setRefreshViewMoreStatus(mSrlLayout, data, mPresenter.mPageSize);
        mDynamicAdapter.setNotifyData(data.size() < mPresenter.mPageSize);
        clearRedPoint();
    }

    @Override
    public void resultClearRedPoint(boolean isSucceed) {
        if (isSucceed) {
            mPresenter.obtainRedPoint();
        } else {
            isCanBack = true;
        }
    }

    @Override
    public void resultRedPointData(List<ResultRedPointInfoBean> data) {
        isCanBack = true;
    }

    @Override
    protected MessageNotificationPresenter createPresenter() {
        return new MessageNotificationPresenter(this);
    }


}

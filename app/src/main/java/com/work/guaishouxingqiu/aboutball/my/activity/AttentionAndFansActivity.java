package com.work.guaishouxingqiu.aboutball.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.AttentionFansAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultAttentionFanBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AttentionAndFansContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.AttentionAndFansPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/26 11:08
 * 更新时间: 2019/7/26 11:08
 * 描述:关注和我的粉丝Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ATTENTION_AND_FANS)
public class AttentionAndFansActivity extends BaseActivity<AttentionAndFansPresenter> implements AttentionAndFansContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    public static final int ATTENTION_ID = 1;//我的关注
    public static final int FANS_ID = 2;//我的粉丝
    private int mStatusId;
    private AttentionFansAdapter mAdapter;
    private List<ResultAttentionFanBean> mData;
    private static final int REQUEST_CODE_USER_DYNAMIC = 215;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention_and_fans;
    }

    @Override
    public void initPermission() {
        mStatusId = mIntent.getIntExtra(ARouterConfig.Key.ATTENTION_AND_FANS, -1);
        if ((mStatusId == AttentionAndFansActivity.ATTENTION_ID)
                || (mStatusId == AttentionAndFansActivity.FANS_ID)) {
            super.initPermission();
        } else {
            UIUtils.showToast(R.string.abnormal_illegal_state);
        }

    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new AttentionFansAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mStatusId == AttentionAndFansActivity.ATTENTION_ID) {
            mTitleView.mTvCenter.setText(R.string.my_focus);
        } else if (mStatusId == AttentionAndFansActivity.FANS_ID) {
            mTitleView.mTvCenter.setText(R.string.my_fans);
        }
        mSrlLayout.autoRefresh();
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mSrlLayout.finishRefresh();
        } else {
            mSrlLayout.finishLoadMore();
        }
        if (mStatusId == AttentionAndFansActivity.ATTENTION_ID) {
            mPresenter.loadAttentionData();
        } else if (mStatusId == AttentionAndFansActivity.FANS_ID) {
            mPresenter.loadFansData();
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
        mAdapter.setOnAttentionClickListener(new AttentionFansAdapter.onAttentionClickListener() {
            @Override
            public void onClickAttention(View view, int position) {
                ResultAttentionFanBean bean = mData.get(position);
                if (bean.isFollow == 0) {
                    mPresenter.getAttentionTweet(position, bean.userId);
                } else if (bean.isFollow == 1) {
                    mPresenter.getCancelAttentionTweet(position, bean.userId);
                }
            }

            @Override
            public void onClickHead(View view, int position) {
                mViewModel.startActivityToUserDynamicForResult(null, mData.get(position).userId, REQUEST_CODE_USER_DYNAMIC);
            }
        });
    }

    @Override
    public void resultAttentionTweetStatus(int position) {
        ResultAttentionFanBean bean = mData.get(position);
        if (bean.isFollow == 1) {
            bean.isFollow = 0;
        } else if (bean.isFollow == 0) {
            bean.isFollow = 1;
        }
        EventBus.getDefault().post(bean);
        if (mStatusId == AttentionAndFansActivity.ATTENTION_ID) {
            mData.remove(position);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_USER_DYNAMIC) {
            mSrlLayout.autoRefresh();
        }
    }

    @Override
    protected AttentionAndFansPresenter createPresenter() {
        return new AttentionAndFansPresenter(this);
    }


    @Override
    public void resultAttentionFansData(List<ResultAttentionFanBean> data) {
        if (mPresenter.isRefresh && mData.size() > 0) {
            mData.clear();
        }
        if (!DataUtils.isEmptyList(data)) {
            mData.addAll(data);
        }
        mViewModel.setRefreshViewMoreStatus(mSrlLayout, data, mPresenter.mPageSize);
        mAdapter.setNotifyData(DataUtils.getListSize(data) == mPresenter.mPageSize);
    }


}

package com.work.guaishouxingqiu.aboutball.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.MyDynamicContract;
import com.work.guaishouxingqiu.aboutball.community.fragment.CommunityNewFragment;
import com.work.guaishouxingqiu.aboutball.community.presenter.MyDynamicPresenter;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:11
 * 更新时间: 2019/6/19 9:11
 * 描述:我的动态Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_DYNAMIC)
public class MyDynamicActivity extends BaseActivity<MyDynamicPresenter> implements MyDynamicContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.ctl_group)
    CollapsingToolbarLayout mCtlGroup;
    @BindView(R.id.abl_group)
    AppBarLayout mAblGroup;
    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;
    @BindView(R.id.rl_body)
    RelativeLayout mRlBody;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_follow_count)
    TextView mTvFollowCount;
    @BindView(R.id.tv_fans_count)
    TextView mTvFansCount;
    @BindView(R.id.civ_head)
    CircleImageView mCivHead;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<ResultCommunityDataBean> mData;
    private CommunityDataAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_dynamic;
    }

    @Override
    protected void initStatusColor() {
        //  super.initStatusColor();
    }

    @Override
    protected void initView() {
        setTranslucentStatusBar(mTitleView);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 设置透明状态栏
     *
     * @return CoordinatorTabLayout
     */
    public void setTranslucentStatusBar(View view) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mTitleView.post(() -> mTitleView.setPadding(0, mTitleView.getPaddingTop() +
                ScreenUtils.getStatuWindowsHeight(MyDynamicActivity.this) + ScreenUtils.dp2px(MyDynamicActivity.this, 10), 0, 0));


    }

    @Override
    protected void initData() {

        initHeadData();

        initAdapter();

    }

    private void initHeadData() {
        mAblGroup.setLiftable(true);
        UserBean userBean = UserManger.get().getUser();
        mTvName.setText(userBean.nickName);
        String followContent = UIUtils.getString(R.string.attention_s, userBean.followCount);
        mTvFollowCount.setText(SpanUtils.getTextSize(14, 0, String.valueOf(userBean.followCount).length(),
                SpanUtils.getTextColor(R.color.color_4, 0, String.valueOf(userBean.followCount).length(), followContent)));
        String fansContent = UIUtils.getString(R.string.fans_s, userBean.fansCount);
        mTvFansCount.setText(SpanUtils.getTextSize(14, 0, String.valueOf(userBean.fansCount).length(),
                SpanUtils.getTextColor(R.color.color_4, 0, String.valueOf(userBean.fansCount).length(), fansContent)));
        GlideManger.get().loadHeadImage(this, userBean.headerImg, mCivHead);
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData);
       // mAdapter.addFootView(UIUtils.loadNotMoreView(mRvData));
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {

        mAblGroup.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                mRlBody.setAlpha(1 - ((float) Math.abs(i)) / ((float) appBarLayout.getTotalScrollRange()));
                if (Math.abs(i) == appBarLayout.getTotalScrollRange()) {
                    ScreenUtils.setStatusTextColor(getWindow().getDecorView(), true);
                    mTitleView.mTvBack.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_back, 0, 0, 0);
                    mTitleView.mTvCenter.setTextColor(ContextCompat.getColor(MyDynamicActivity.this, R.color.color_4));
                } else {
                    mTitleView.mTvBack.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_back_white, 0, 0, 0);
                    mTitleView.mTvCenter.setTextColor(ContextCompat.getColor(MyDynamicActivity.this, R.color.colorWhite));
                    ScreenUtils.setStatusTextColor(getWindow().getDecorView(), false);

                }
            }
        });
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                finish();
            }
        });
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
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
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), null);
            }
        });
        mAdapter.setOnTextContentClickListener(new CommunityDataAdapter.OnTextContentClickListener() {
            @Override
            public void onClickContent(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), null);
            }

            @Override
            public void onClickTopic(View view, int position) {

            }

            @Override
            public void onClickReport(View view, int position) {

            }

            @Override
            public void onClickAttention(View view, int position) {

            }

            @Override
            public void onClickDelete(View view, int position) {
                mPresenter.deleteDynamics(mData.get(position).tweetId, position);
            }

            @Override
            public void onClickDianZan(View view, int position) {
                ResultCommunityDataBean bean = mData.get(position);
                if (bean.hasPraise == 1) {
                    mPresenter.dynamicsCancelDianZan(bean.tweetId, position);
                } else if (bean.hasPraise == 0) {
                    mPresenter.dynamicsDianZan(bean.tweetId, position);
                }
            }

            @Override
            public void onClickShare(View view, int position) {

            }
        });

    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        mPresenter.loadMyDynamic();
    }

    @Override
    protected MyDynamicPresenter createPresenter() {
        return new MyDynamicPresenter(this);
    }


    @OnClick(R.id.civ_head)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_head:
                mViewModel.startActivityToPreview(0, DataUtils.getOnePreviewData(UserManger.get().getUser().headerImg));
                break;
            default:
                break;
        }
    }

    @Override
    public void resultMyDynamic(List<ResultCommunityDataBean> data) {
        if (mPresenter.isRefresh && mData.size() > 0) {
            mData.clear();
        }
        if (data.size() != 0) {
            mData.addAll(data);
        }
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
    /*    if (data.size() < mPresenter.mPageSize) {
            mAdapter.showFootView();
        } else {
            mAdapter.hideFootView();
        }*/
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultDeleteDynamicSucceed(int position) {
        mData.remove(position);
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void resultDianZanStatus(int position) {
        mViewModel.updateDianZan(mAdapter, mData, position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    ResultCommunityDataBean bean = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    boolean isDelete = data.getBooleanExtra(ARouterConfig.Key.DELETE, false);
                    mViewModel.resultCommunityData(mAdapter, bean, mData,isDelete);
                    break;
                default:
                    break;
            }
        }
    }
}

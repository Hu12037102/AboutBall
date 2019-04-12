package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.home.activity.MainActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.CarousePagerAdapter;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendHeadGameAdapter;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.contract.RecommendedContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.RecommendedPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.weight.CarouselViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:37
 * 更新时间: 2019/3/12 17:37
 * 描述: 推荐Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_RECOMMENDED)
public class RecommendedFragment extends BaseFragment<RecommendedPresenter> implements RecommendedContract.View {
    private static final int WHAT = 100;
    private static final int POST_TIME = 2000;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.srl_recommend)
    SmartRefreshLayout mSrlRecommend;
    public RecommendedAdapter mRecommendAdapter;
    private List<ResultNewsBean> mRecommendData;
    private View mInflateHead;
    private CarouselViewPager mCvpBanner;
    private RecyclerView mRvGameLive;
    private TextView mTvVenue;
    private TextView mTvBall;
    private TextView mTvGame;
    private Handler mTimeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RecommendedFragment.WHAT:
                    removeTimeMessage();
                    mCvpBanner.setCurrentItem(mCvpBanner.getCurrentItem() + 1, true);
                    postTimeMessage();
                    break;
            }
            return true;
        }
    });
    private RecommendHeadGameAdapter mHeadGameAdapter;
    private CarousePagerAdapter mCarouseAdapter;
    private int mTypeId;
    private RequestRecommendDataBean mHeadBean;
    private ResultRecommendDataBean mResultHeadBean;

    public static RecommendedFragment newInstance() {
        return new RecommendedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    @Override
    protected void initView() {
        mRvRecommend.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            mTypeId = getArguments().getInt(ARouterConfig.Key.TAB_TYPE_ID);
        }
        initHeadView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initHeadView() {
        mInflateHead = LayoutInflater.from(getContext()).inflate(R.layout.item_recommend_head_view, null);
        mCvpBanner = mInflateHead.findViewById(R.id.cvp_banner);
        mRvGameLive = mInflateHead.findViewById(R.id.rv_game_live);
        mRvGameLive.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())
                , LinearLayoutManager.HORIZONTAL, false));
        mTvVenue = mInflateHead.findViewById(R.id.tv_venue);
        mTvBall = mInflateHead.findViewById(R.id.tv_ball);
        mTvGame = mInflateHead.findViewById(R.id.tv_game);
        mCvpBanner.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    removeTimeMessage();
                    break;
                case MotionEvent.ACTION_UP:
                    postTimeMessage();
                    break;
            }
            return false;
        });
        mTvVenue.setOnClickListener(v -> {

        });
        mTvBall.setOnClickListener(v -> {

        });
        mTvGame.setOnClickListener(v -> {

        });
    }
    private void postTimeMessage() {
        mTimeHandler.sendEmptyMessageDelayed(RecommendedFragment.WHAT, RecommendedFragment.POST_TIME);
    }

    private void removeTimeMessage() {
        mTimeHandler.removeMessages(RecommendedFragment.WHAT, null);

    }

    @Override
    protected void initData() {
        mRecommendData = new ArrayList<>();
        mRecommendAdapter = new RecommendedAdapter(mRecommendData);
        mRecommendAdapter.setHasStableIds(true);
        mRecommendAdapter.addHeadView(mInflateHead);
        mRvRecommend.setAdapter(mRecommendAdapter);
        mHeadBean = new RequestRecommendDataBean();
        initLocation();

    }

    private void loadRefreshData() {
        mPresenter.loadHead(mHeadBean);
        mSrlRecommend.finishRefresh();
    }

    private void loadMoreData() {
        mSrlRecommend.finishLoadMore();
        mPresenter.loadData(mTypeId);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvent() {
        mSrlRecommend.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = false;
                loadMoreData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = true;
                loadRefreshData();
            }
        });
        mRecommendAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                if (position != RecommendedAdapter.POSITION_VENUE_ITEM
                        && position != RecommendedAdapter.POSITION_BALL_ITEM) {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                            ARouterConfig.Key.NEW_DETAILS_ID, mRecommendData.get(position).newsId);
                }
            }
        });

    }


    private void initLocation() {
        Location location = PhoneUtils.getGPSLocation(this);
        if (location != null) {
            mHeadBean.latitude = String.valueOf(location.getLatitude());
            mHeadBean.longitude = String.valueOf(location.getLongitude());
        }
        LogUtils.w("initLocation--", mHeadBean.latitude + "--" + mHeadBean.longitude);
        mSrlRecommend.autoRefresh();
    }


    @Override
    protected RecommendedPresenter createPresenter() {
        return new RecommendedPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contast.REQUEST_CODE && resultCode == Contast.REQUEST_CODE) {
            //  PhoneUtils.checkoutGPS(this);
            initLocation();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.w("onHiddenChanged--", hidden + "--");
    }

    @Override
    public void resultBannerData(BaseBean<ResultRecommendDataBean> bean) {

        if (bean.code == Contast.REQUEST_CODE && bean.result != null) {
            this.mResultHeadBean = bean.result;
            if (bean.result.banner != null) {
                if (mCarouseAdapter == null) {
                    mCarouseAdapter = new CarousePagerAdapter(bean.result.banner);
                    mCvpBanner.setOffscreenPageLimit(3);
                    mCvpBanner.setPageMargin(ScreenUtils.dp2px(DataUtils.checkData(getContext()), 10));
                    mCvpBanner.setAdapter(mCarouseAdapter);
                } else {
                    mCarouseAdapter.notifyDataSetChanged();
                }
                this.postTimeMessage();

            }
            if (bean.result.match != null) {
                if (mHeadGameAdapter == null) {
                    mHeadGameAdapter = new RecommendHeadGameAdapter(bean.result.match);
                    mRvGameLive.setAdapter(mHeadGameAdapter);
                    mHeadGameAdapter.setOnItemClickListener((view, position) -> {
                        LogUtils.w("mHeadGameAdapter--", bean.result.match.get(position).matchId + "---");
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_DETAILS, ARouterConfig.Key.GAME_ID, (int) bean.result.match.get(position).matchId);
                    });
                } else {

                    mHeadGameAdapter.notifyDataSetChanged();
                }
            }
        }
        mPresenter.loadData(mTypeId);
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (bean.result != null) {
            if (mPresenter.isRefresh) {
                mRecommendData.clear();
            }
            mSrlRecommend.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mRecommendData.addAll(bean.result);
            if (mPresenter.mPageNum == 2 && mResultHeadBean != null) {
                if (mRecommendData.size() >= RecommendedAdapter.POSITION_VENUE_ITEM && mResultHeadBean.stadium != null && mResultHeadBean.stadium.size() > 0) {
                    mRecommendData.add(RecommendedAdapter.POSITION_VENUE_ITEM, null);
                }
                if (mRecommendData.size() >= RecommendedAdapter.POSITION_BALL_ITEM && mResultHeadBean.agreeBallMatch != null && mResultHeadBean.agreeBallMatch.size() > 0) {
                    mRecommendData.add(RecommendedAdapter.POSITION_BALL_ITEM, null);
                }
            }
            if (mResultHeadBean != null && mResultHeadBean.stadium != null && mResultHeadBean.stadium.size() > 0 &&
                    mResultHeadBean.agreeBallMatch != null && mResultHeadBean.agreeBallMatch.size() > 0) {
                mRecommendAdapter.notifyData(mResultHeadBean.stadium, mResultHeadBean.agreeBallMatch);
            } else {
                mRecommendAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mCvpBanner != null && mCvpBanner.getChildCount() > 0) {
            removeTimeMessage();
            postTimeMessage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.removeTimeMessage();
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

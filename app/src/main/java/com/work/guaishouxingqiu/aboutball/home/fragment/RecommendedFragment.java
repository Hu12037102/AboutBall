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
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
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
    private RecommendedAdapter mRecommendAdapter;
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
        mRecommendAdapter.addHeadView(mInflateHead);
        mRvRecommend.setAdapter(mRecommendAdapter);
        initLocation();
        mSrlRecommend.autoRefresh();
    }

    private void loadRefreshData() {
        mPresenter.loadHead(new RequestRecommendDataBean());
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

            }
        });

    }

    private void initLocation() {
        PhoneUtils.checkoutGPS(this);
        LocationManager locationManager = (LocationManager) DataUtils.checkData(getContext()).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<String> providers = locationManager.getProviders(true);
        String provider = null;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        if (provider == null) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 3000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LogUtils.w("requestLocation", "onLocationChanged--" + location.getLongitude() + "--" +
                        location.getLatitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LogUtils.w("requestLocation", "onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                LogUtils.w("requestLocation", "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                LogUtils.w("requestLocation", "onProviderDisabled");
            }
        });


    }


    @Override
    protected RecommendedPresenter createPresenter() {
        return new RecommendedPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contast.REQUEST_CODE && resultCode == Contast.REQUEST_CODE) {
            PhoneUtils.checkoutGPS(this);
        }
    }

    @Override
    public void resultBannerData(BaseBean<ResultRecommendDataBean> bean) {
        if (bean.code == Contast.REQUEST_CODE && bean.result != null) {
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
                } else {
                    mHeadGameAdapter.notifyDataSetChanged();
                }
            }
        }
        mPresenter.loadData(mTypeId);
        mRecommendAdapter.notifyData(NetWorkUtils.isNetCanUse());
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (bean.result != null) {
            if (mPresenter.isRefresh) {
                mRecommendData.clear();
            }
            mSrlRecommend.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mRecommendData.addAll(bean.result);
            mRecommendAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        removeTimeMessage();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

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
}

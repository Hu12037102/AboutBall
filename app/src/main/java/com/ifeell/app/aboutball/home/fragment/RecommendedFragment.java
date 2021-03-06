package com.ifeell.app.aboutball.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.home.adapter.CarousePagerAdapter;
import com.ifeell.app.aboutball.home.adapter.RecommendHeadGameAdapter;
import com.ifeell.app.aboutball.home.adapter.RecommendedAdapter;
import com.ifeell.app.aboutball.home.bean.RequestRecommendDataBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.home.bean.ResultRecommendDataBean;
import com.ifeell.app.aboutball.home.contract.RecommendedContract;
import com.ifeell.app.aboutball.home.presenter.RecommendedPresenter;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.venue.activity.AboutBallDetailsActivity;
import com.ifeell.app.aboutball.weight.CarouselViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ifeell.app.aboutball.home.adapter.RecommendedAdapter.TYPE_BALL;
import static com.ifeell.app.aboutball.home.adapter.RecommendedAdapter.TYPE_VENUE;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:37
 * 更新时间: 2019/3/12 17:37
 * 描述: 推荐Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_RECOMMENDED)
public class RecommendedFragment extends BaseFragment<RecommendedPresenter> implements RecommendedContract.View {
    private static final int WHAT = 100;
    private static final int POST_TIME = 4000;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.srl_recommend)
    SmartRefreshLayout mSrlRecommend;
    @BindView(R.id.iv_activities)
    ImageView mIvActivities;
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
    private List<ResultRecommendDataBean.Match> mLiveMatchData;

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
        mInflateHead.findViewById(R.id.tv_ticket_mall).setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_TICKET_MALL));
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
            DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0111);
            EventBus.getDefault().post(new HomeFragment.Message(TYPE_VENUE, 2));
        });
        mTvBall.setOnClickListener(v -> {
            DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0112);
            EventBus.getDefault().post(new HomeFragment.Message(TYPE_BALL, 2));
        });
        mTvGame.setOnClickListener(v -> {
            DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0113);
            EventBus.getDefault().post(new HomeFragment.Message(0, 1));
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
        //直播list
        mLiveMatchData = new ArrayList<>();
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

                ResultNewsBean bean = mRecommendData.get(position);
                if (bean == null) {
                    return;
                }
                if (!bean.isRead) {
                    DataUtils.putNewsKey(bean.newsId);
                    mRecommendAdapter.notifyDataSetChanged();
                }

                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mRecommendData.get(position).newsId);


            }
        });
        mIvActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.isCheckOutLogin(RecommendedFragment.this)){
                    mViewModel.startWebUrlActivityForResult(RecommendedFragment.this,mResultHeadBean.activity.activityUrl);
                }
            }
        });

    }


    private void initLocation() {
      /*  Location location = PhoneUtils.getGPSLocation(this);
        if (location != null) {
            mHeadBean.latitude = String.valueOf(location.getLatitude());
            mHeadBean.longitude = String.valueOf(location.getLongitude());
        }*/

        mHeadBean.longitude = String.valueOf(getSPLongitude());
        mHeadBean.latitude = String.valueOf(getSPLatitude());
        //113.836712--22.605544--深圳市
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
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AboutBallDetailsActivity.REQUEST_CODE:
                    mPresenter.loadHead(mHeadBean);
                    break;
                default:
                    break;
            }
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
                if (mLiveMatchData.size() > 0) {
                    mLiveMatchData.clear();
                }
                mLiveMatchData.addAll(bean.result.match);
                if (mHeadGameAdapter == null) {
                    mHeadGameAdapter = new RecommendHeadGameAdapter(mLiveMatchData);
                    mRvGameLive.setAdapter(mHeadGameAdapter);
                } else {
                    mHeadGameAdapter.notifyDataSetChanged();
                }
                mHeadGameAdapter.setOnItemClickListener((view, position) -> {
                    LogUtils.w("mHeadGameAdapter--", bean.result.match.get(position).matchId + "---");
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_DETAILS, ARouterConfig.Key.GAME_ID, (int) bean.result.match.get(position).matchId);
                });
            }
            if (bean.result.activity!=null && bean.result.activity.status ==1){
                mIvActivities.setVisibility(View.VISIBLE);
            }else {
                mIvActivities.setVisibility(View.GONE);
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
            if (mResultHeadBean != null /*&& mResultHeadBean.stadium != null && mResultHeadBean.stadium.size() > 0 &&
                    mResultHeadBean.agreeBallMatch != null && mResultHeadBean.agreeBallMatch.size() > 0*/) {
                mRecommendAdapter.notifyData(this, mResultHeadBean.stadium, mResultHeadBean.agreeBallMatch);
            } /*else {
                mRecommendAdapter.notifyDataSetChanged();
            }*/
            mRecommendAdapter.notifyDataSetChanged();
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

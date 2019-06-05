package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.media.IntentData;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.AboutBallAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueDateAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueListAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueUserCommentAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 13:15
 * 更新时间: 2019/3/27 13:15
 * 描述:场馆详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_DETAILS)
public class VenueDetailsActivity extends BaseActivity<VenueDetailsPresenter> implements VenueDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_banner)
    ImageView mIvBanner;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rb_grade)
    RatingBar mRbGrade;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tab_session)
    TabLayout mTabSession;
    @BindView(R.id.rv_session)
    RecyclerView mRvSession;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private View mHeadView;
    private VenueDateAdapter mDateAdapter;
    private RecyclerView mRvDate;
    private TextView mTvImageText;
    private ChipGroup mCgEvaluate;
    private VenueUserCommentAdapter mCommentAdapter;
    private List<ResultVenueDetailsBean.CalendarListForAreaList> mDateData;
    private RecyclerView mRvComment;
    private List<ResultVenueDetailsBean.OrderCommentForAreaSimpleList> mCommentData;
    private RecyclerView mRvAboutBall;
    private List<ResultAboutBallBean> mAboutBallData;
    private AboutBallAdapter mAboutBallAdapter;
    private List<ResultVenueData> mVenueData;
    private int mStadiumId;
    private RequestVenueListBean mRequestVenueBean;
    private ResultVenueDetailsBean mDetailsBean;
    private VenueListAdapter mVenueListAdapter;
    private int mCalendarPosition;
    private LinearLayout mLlHeadCommentGroup, mLlHeadImageTextGroup, mLlHeadSiteGroup, mLlHeadSiteDetailsGroup,
            mLlHeadAboutBallGroup, mLlHeadGroup;
    private int mSelectorTabPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_details;
    }


    @Override
    protected void initView() {
        mStadiumId = mIntent.getIntExtra(ARouterConfig.Key.STADIUM_ID, -1);
        LogUtils.w("viewHolder--", mStadiumId + "--");
        if (mStadiumId == -1) {
            finish();
            UIUtils.showToast(R.string.not_this_venue);
            return;
        }
        mRvSession.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_venue_details_view, mRvSession, false);
        mHeadView.requestFocus();
        mHeadView.setFocusable(true);
        mHeadView.setFocusableInTouchMode(true);
        mRvDate = mHeadView.findViewById(R.id.rv_date);
        mRvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDateData = new ArrayList<>();
        mTvImageText = mHeadView.findViewById(R.id.tv_image_text);
        mCgEvaluate = mHeadView.findViewById(R.id.cg_flow);
        ViewGroup.LayoutParams layoutParams = mCgEvaluate.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mCgEvaluate.setLayoutParams(layoutParams);

        mRvComment = mHeadView.findViewById(R.id.rv_evaluation);
        mRvComment.setLayoutManager(new LinearLayoutManager(this));
        mCommentData = new ArrayList<>();

        mRvAboutBall = mHeadView.findViewById(R.id.rv_ball);
        mRvAboutBall.setLayoutManager(new LinearLayoutManager(this));
        mAboutBallData = new ArrayList<>();

        mLlHeadCommentGroup = mHeadView.findViewById(R.id.ll_comment_group);
        //图文介绍Group =场地介绍group+场地情况group
        mLlHeadImageTextGroup = mHeadView.findViewById(R.id.ll_image_text_group);
        //场地介绍group
        mLlHeadSiteGroup = mHeadView.findViewById(R.id.ll_site_group);
        //场地情况group
        mLlHeadSiteDetailsGroup = mHeadView.findViewById(R.id.ll_site_details);
        //球友约球group
        mLlHeadAboutBallGroup = mHeadView.findViewById(R.id.ll_about_ball);
        mLlHeadGroup = mHeadView.findViewById(R.id.ll_head_group);
    }

    private void initLocation() {
        mRequestVenueBean = new RequestVenueListBean();
      //  Location location = PhoneUtils.getGPSLocation(this);
        mRequestVenueBean.stadiumId = mStadiumId;
      /*  if (location != null) {
            mRequestVenueBean.latitude = String.valueOf(location.getLatitude());
            mRequestVenueBean.longitude = String.valueOf(location.getLongitude());
        }*/
        mRequestVenueBean.longitude = String.valueOf(getSPLongitude());
        mRequestVenueBean.latitude = String.valueOf(getSPLatitude());
    }

    @Override
    protected void initData() {
        initLocation();
        initHeadView();
        mVenueData = new ArrayList<>();
        mVenueListAdapter = new VenueListAdapter(mVenueData);
        mVenueListAdapter.addHeadView(mHeadView);
        mRvSession.setAdapter(mVenueListAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadDetails(mStadiumId);
    }

    @Override
    protected void initEvent() {
        mTabSession.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                if (mDetailsBean != null && mDetailsBean.areaForDetailList != null && mDetailsBean.areaForDetailList.size() > tab.getPosition()) {
                    mSelectorTabPosition = tab.getPosition();
                    notifyDate(mDetailsBean, tab.getPosition());
                }
                LogUtils.w("initEvent===", mSelectorTabPosition + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVenueListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_DETAILS, ARouterConfig.Key.STADIUM_ID, mVenueData.get(position).stadiumId);
            }
        });
    }

    @Override
    protected VenueDetailsPresenter createPresenter() {
        return new VenueDetailsPresenter(this);
    }


    @OnClick({R.id.tv_go, R.id.iv_banner, R.id.iv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                clickGo();
                break;
            case R.id.iv_banner:
                break;
            case R.id.iv_phone:
                clickCallPhone();
                break;
        }
    }

    private void clickGo() {
        if (mDetailsBean.areaForDetailList != null &&
                mDetailsBean.areaForDetailList.size() > mSelectorTabPosition) {
            Bundle bundle = new Bundle();
            bundle.putInt(ARouterConfig.Key.POSITION, mCalendarPosition);
            bundle.putLong(ARouterConfig.Key.AREA_ID, mDetailsBean.areaForDetailList.get(mSelectorTabPosition).areaId);
            bundle.putLong(ARouterConfig.Key.STADIUM_ID, mStadiumId);
            IntentData.get().putData(mDetailsBean.areaForDetailList.get(mSelectorTabPosition).calendarListForAreaList);
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_BOOKING, bundle);
        }
    }

    private void clickCallPhone() {
        UIUtils.showCallPhoneDialog(this, mDetailsBean.telephone);
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
    public void resultDetails(ResultVenueDetailsBean bean) {
        this.mDetailsBean = bean;
        mTitleView.mTvCenter.setText(bean.stadiumName);
        GlideManger.get().loadBannerImage(this, bean.photoUrl, mIvBanner);
        mTvName.setText(bean.stadiumName);
        mRbGrade.setRating(Float.valueOf(bean.grade));
        mTvGrade.setText(getString(R.string.how_long_gradle, bean.grade));
        mTvAddress.setText(getString(R.string.address_s, bean.address));
        mTvService.setText(getString(R.string.service_s, bean.service));
        List<ResultVenueDetailsBean.AreaForDetailsList> areaData = bean.areaForDetailList;
        if (areaData != null && areaData.size() > 0) {
            if (mTabSession.getTabCount() == 0) {
                for (int i = 0; i < areaData.size(); i++) {
                /*TabLayout.Tab tab = mTabSession.newTab().setText(areaData.get(i).areaName);
                mTabSession.addTab(tab, i == 0);*/
                    UIUtils.setBaseCustomTabLayout(mTabSession, areaData.get(i).areaName, i == mSelectorTabPosition, 45);
                }
            }
            notifyDate(bean, mSelectorTabPosition);
        }
        mPresenter.loadVenueData(mRequestVenueBean);
    }

    @Override
    public void resultVenueData(List<ResultVenueData> data) {
        mVenueData.clear();
        mVenueData.addAll(data);
        mVenueListAdapter.notifyDataSetChanged();
    }

    private void notifyDate(ResultVenueDetailsBean bean, int position) {
        ResultVenueDetailsBean.AreaForDetailsList areaBean = bean.areaForDetailList.get(position);
        if (areaBean == null) {
            return;
        }
        mDateData.clear();
        //場次
        if (areaBean.calendarListForAreaList != null && areaBean.calendarListForAreaList.size() > 0) {
            mRvDate.setVisibility(View.VISIBLE);
            mLlBottom.setVisibility(View.VISIBLE);
            mRvDate.setPadding(0, ScreenUtils.dp2px(this, 8), 0, ScreenUtils.dp2px(this, 8));
            mDateData.addAll(areaBean.calendarListForAreaList);
        } else {
            mLlBottom.setVisibility(View.GONE);
            mRvDate.setVisibility(View.GONE);
            mRvDate.setPadding(0, 0, 0, 0);
        }
        if (mDateAdapter == null) {
            mDateAdapter = new VenueDateAdapter(this, mDateData);
            mRvDate.setAdapter(mDateAdapter);
            mDateAdapter.setOnItemClickListener((view, position1) -> {
                VenueDetailsActivity.this.mCalendarPosition = position1;
                clickGo();
            });
        } else {
            mDateAdapter.notifyDataSetChanged();
        }


        if (DataUtils.isEmpty(areaBean.introduce)) {
            mLlHeadSiteGroup.setVisibility(View.GONE);
        } else {
            mLlHeadSiteGroup.setVisibility(View.VISIBLE);
            mTvImageText.setText(areaBean.introduce);
        }

        if (mCgEvaluate.getChildCount() > 0) {
            mCgEvaluate.removeAllViews();
        }
        //場地情況
        String[] evaluateArray = areaBean.supporting.split(" ");
        if (evaluateArray.length > 0) {
            mLlHeadSiteDetailsGroup.setVisibility(View.VISIBLE);
        } else {
            mLlHeadSiteDetailsGroup.setVisibility(View.GONE);
        }

        if (DataUtils.isEmpty(areaBean.introduce) && evaluateArray.length == 0) {
            mLlHeadImageTextGroup.setVisibility(View.GONE);
        } else {
            mLlHeadImageTextGroup.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < evaluateArray.length; i++) {
            TextView textView = new TextView(this);
            mCgEvaluate.addView(textView, i);
            textView.setTextColor(ContextCompat.getColor(this, R.color.color_4));
            textView.setText(evaluateArray[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setBackgroundResource(R.drawable.shape_venu_evaluate_view);
            ChipGroup.LayoutParams layoutParams = (ChipGroup.LayoutParams) textView.getLayoutParams();
            layoutParams.topMargin = ScreenUtils.dp2px(this, 20);
            layoutParams.rightMargin = ScreenUtils.dp2px(this, 20);
            textView.setLayoutParams(layoutParams);
        }
        //評論
        if (mCommentData.size() > 0) {
            mCommentData.clear();
        }
        int commentCount = 0;
        if (areaBean.orderCommentForAreaList != null && areaBean.orderCommentForAreaList.size() > 0) {
            mLlHeadCommentGroup.setVisibility(View.VISIBLE);
            mCommentData.addAll(areaBean.orderCommentForAreaList.get(0).orderCommentForAreaSimpleList);
            commentCount = areaBean.orderCommentForAreaList.get(0).totalCount;
        } else {
            mLlHeadCommentGroup.setVisibility(View.GONE);
        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new VenueUserCommentAdapter(this, mCommentData, commentCount);
            mRvComment.setAdapter(mCommentAdapter);
        } else {
            mCommentAdapter.notifyDataSetChanged();
        }
        mCommentAdapter.setOnLoadMoreCommentListener(new VenueUserCommentAdapter.OnLoadMoreCommentListener() {
            @Override
            public void onClickMore(View view) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_EVALUATE, ARouterConfig.Key.AREA_ID, areaBean.areaId);
            }
        });

        //約球
        if (mAboutBallData.size() > 0) {
            mAboutBallData.clear();
        }
        if (bean.offerListForStadium != null && bean.offerListForStadium.size() > 0) {
            mLlHeadAboutBallGroup.setVisibility(View.VISIBLE);
            mAboutBallData.addAll(bean.offerListForStadium);
        } else {
            mLlHeadAboutBallGroup.setVisibility(View.GONE);
        }
        if (mAboutBallAdapter == null) {
            mAboutBallAdapter = new AboutBallAdapter(mAboutBallData);
            mRvAboutBall.setAdapter(mAboutBallAdapter);
        } else {
            mAboutBallAdapter.notifyDataSetChanged();
        }
        mAboutBallAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                if (UserManger.get().isLogin()) {
                    //mClickPosition = position;
                    ResultAboutBallBean bean = mAboutBallData.get(position);
                    startActivityToAboutBallDetails(bean);
                } else {
                    mViewModel.showLoginDialog();
                }
            }
        });
    }

    /**
     * flag 0:查看、参加约球裁判 1：取消约球
     *
     * @param bean
     * @param
     */
    private void startActivityToAboutBallDetails(ResultAboutBallBean bean) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.REFEREE_STATUS, bean.hasReferee);
        bundle.putInt(ARouterConfig.Key.TEAM_STATUS, bean.hasOpponent);
        bundle.putLong(ARouterConfig.Key.OFFER_ID, bean.agreeId);
        bundle.putInt(ARouterConfig.Key.ABOUT_BALL_FLAG, 0);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ABOUT_BALL_DETAILS, bundle);
    }
}

package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.AboutBallAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueDateAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueDetailsAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueUserCommentAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private View mHeadView;
    private VenueDateAdapter mDateAdapter;
    private RecyclerView mRvDate;
    private TextView mTvImageText;
    private ChipGroup mCgEvaluate;
    private VenueUserCommentAdapter mCommentAdapter;
    private List<ResultVenueDetailsBean.CalendarListForAreaList> mDateData;
    private RecyclerView mRvComment;
    private List<ResultVenueDetailsBean.OrderCommentForAreaList> mCommentData;
    private RecyclerView mRvAboutBall;
    private List<ResultAboutBallBean> mAboutBallData;
    private AboutBallAdapter mAboutBallAdapter;
    private List<ResultVenueData> mVenueData;
    private VenueDetailsAdapter mVenueDataAdapter;
    private int mStadiumId;
    private RequestVenueListBean mRequestVenueBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_details;
    }


    @Override
    protected void initView() {
        mStadiumId = mIntent.getIntExtra(ARouterConfig.Key.STADIUM_ID, -1);
        if (mStadiumId == -1) {
            finish();
            UIUtils.showToast(R.string.not_this_venue);
            return;
        }
        mRvSession.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_venue_details_view, mRvSession, false);
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
    }

    private void initLocation() {
        mRequestVenueBean = new RequestVenueListBean();
        Location location = PhoneUtils.getGPSLocation(this);
        mRequestVenueBean.stadiumId = mStadiumId;
        if (location != null) {
            mRequestVenueBean.latitude = String.valueOf(location.getLatitude());
            mRequestVenueBean.longitude = String.valueOf(location.getLongitude());
        }

    }

    @Override
    protected void initData() {
        initLocation();
        initHeadView();
        mVenueData = new ArrayList<>();
        mVenueDataAdapter = new VenueDetailsAdapter(mVenueData);
        mVenueDataAdapter.addHeadView(mHeadView);
        mRvSession.setAdapter(mVenueDataAdapter);
        mPresenter.loadDetails(mStadiumId);
        mPresenter.loadVenueData(mRequestVenueBean);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected VenueDetailsPresenter createPresenter() {
        return new VenueDetailsPresenter(this);
    }


    @OnClick({R.id.tv_go, R.id.iv_banner, R.id.iv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                break;
            case R.id.iv_banner:
                break;
            case R.id.iv_phone:
                break;
        }
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
        GlideManger.get().loadBannerImage(this, bean.photoUrl, mIvBanner);
        mTvName.setText(bean.stadiumName);
        mRbGrade.setRating(Float.valueOf(bean.grade));
        mTvGrade.setText(getString(R.string.how_long_gradle, bean.grade));
        mTvAddress.setText(getString(R.string.address_s, bean.address));
        mTvService.setText(getString(R.string.service_s, bean.service));
        List<ResultVenueDetailsBean.AreaForDetailsList> areaData = bean.areaForDetailList;
        if (areaData != null && areaData.size() > 0) {
            for (int i = 0; i < areaData.size(); i++) {
                mTabSession.addTab(mTabSession.newTab().setText(areaData.get(i).areaName), i == 0);
            }
            notifyDate(bean, 0);
        }


    }

    @Override
    public void resultVenueData(List<ResultVenueData> data) {
        mVenueData.clear();
        mVenueData.addAll(data);
        mVenueDataAdapter.notifyDataSetChanged();
    }

    private void notifyDate(ResultVenueDetailsBean bean, int position) {
        ResultVenueDetailsBean.AreaForDetailsList areaBean = bean.areaForDetailList.get(position);
        if (areaBean == null) {
            return;
        }
        if (mDateData.size() > 0) {
            mDateData.clear();
        }
        //場次
        if (areaBean.calendarListForAreaList != null) {
            mDateData.addAll(areaBean.calendarListForAreaList);
        }
        if (mDateAdapter == null) {
            mDateAdapter = new VenueDateAdapter(this, mDateData);
            mRvDate.setAdapter(mDateAdapter);
        } else {
            mDateAdapter.notifyDataSetChanged();
        }
        mTvImageText.setText(areaBean.introduce);
        if (mCgEvaluate.getChildCount() > 0) {
            mCgEvaluate.removeAllViews();
        }
        //場地情況
        String[] evaluateArray = areaBean.supporting.split(" ");
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
        if (areaBean.orderCommentForAreaList != null && areaBean.orderCommentForAreaList.size() > 0) {
            mCommentData.addAll(areaBean.orderCommentForAreaList);
        }
        if (mCommentAdapter == null) {
            mCommentAdapter = new VenueUserCommentAdapter(this, mCommentData);
        } else {
            mCommentAdapter.notifyDataSetChanged();
        }

        //約球
        if (mAboutBallData.size() > 0) {
            mAboutBallData.clear();
        }
        if (bean.offerListForStadium != null && bean.offerListForStadium.size() > 0) {
            mAboutBallData.addAll(bean.offerListForStadium);
        }
        if (mAboutBallAdapter == null) {
            mAboutBallAdapter = new AboutBallAdapter(mAboutBallData);
            mRvAboutBall.setAdapter(mAboutBallAdapter);
        } else {
            mAboutBallAdapter.notifyDataSetChanged();
        }


    }
}

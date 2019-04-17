package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueDetailsAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
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
    ImageView ivBanner;
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
    private View mHeadView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_details;
    }

    @Override
    protected void initView() {
        mRvSession.setLayoutManager(new LinearLayoutManager(this));
        initLocation();
        initHeadView();
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_venue_details_view, mRvSession, false);
    }

    private void initLocation() {
        RequestVenueListBean mRequestVenueBean = new RequestVenueListBean();
        mRequestVenueBean.stadiumId = mIntent.getLongExtra(ARouterConfig.Key.STADIUM_ID, 0);
        Location location = PhoneUtils.getGPSLocation(this);
        if (location != null) {
            mRequestVenueBean.latitude = String.valueOf(location.getLatitude());
            mRequestVenueBean.longitude = String.valueOf(location.getLongitude());
        }
    }

    @Override
    protected void initData() {
        List<ResultVenueData> mVenueData = new ArrayList<>();
        VenueDetailsAdapter mVenueDataAdapter = new VenueDetailsAdapter(mVenueData);
        mVenueDataAdapter.addHeadView(mHeadView);
        mRvSession.setAdapter(mVenueDataAdapter);

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


}

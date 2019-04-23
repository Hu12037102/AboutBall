package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.media.IntentData;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueBookAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueWaitBookAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueOrderBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueBookingContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueBookingPresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/18 13:18
 * 更新时间: 2019/4/18 13:18
 * 描述:场馆预定
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_BOOKING)
public class VenueBookingActivity extends BaseActivity<VenueBookingPresenter> implements VenueBookingContract.View {
    @BindView(R.id.tb_date)
    TabLayout mTbDate;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.rg_book)
    RadioGroup mRgBooking;
    @BindView(R.id.tv_bottom_hit)
    TextView mTvBottomHit;
    @BindView(R.id.tv_bottom_left)
    TextView mTvBottomLeft;
    @BindView(R.id.tv_bottom_right)
    TextView mTvBottomRight;
    @BindView(R.id.include_rule)
    View mIncludeRuleView;
    @BindView(R.id.ll_bottom)
    View mLlBottom;
    private int mTabPosition;
    private int mAreaId, mStadiumId;
    private String mDate;
    private List<ResultVenueBookBean> mBookData;
    private BookPagerAdapter mPagerAdapter;
    private List<ResultVenueDetailsBean.CalendarListForAreaList> mCalendarData;
    private boolean mIsSelectorBook = true;
    private List<ResultVenueBookBean> mWaitBookData;
    private VenueBookAdapter mBookAdapter;
    private VenueWaitBookAdapter mWaitBookAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_booking;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        mTabPosition = bundle.getInt(ARouterConfig.Key.POSITION, 0);
        mAreaId = bundle.getInt(ARouterConfig.Key.AREA_ID, 0);
        mStadiumId = bundle.getInt(ARouterConfig.Key.STADIUM_ID, 0);
        LogUtils.w("initView--", mTabPosition + "--" + mAreaId);
        mCalendarData = IntentData.get().getData();
        for (int i = 0; i < mCalendarData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTbDate, mCalendarData.get(i).date, i == mTabPosition, 45);
        }
        mDate = mCalendarData.get(mTabPosition).date;

    }

    @Override
    protected void initData() {
        mPagerAdapter = new BookPagerAdapter();
        mBvpContent.setAdapter(mPagerAdapter);

        mBookData = new ArrayList<>();
        mPresenter.loadBookList(mAreaId, mDate);

        mWaitBookData = new ArrayList<>();

    }

    @Override
    protected void initEvent() {
        mTbDate.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabPosition = tab.getPosition();
                if (mIsSelectorBook) {
                    mPresenter.loadBookList(mAreaId, mCalendarData.get(mTabPosition).date);
                } else {
                    mPresenter.loadWaitBookList(mAreaId, mCalendarData.get(mTabPosition).date);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton radioButton = (RadioButton) mRgBooking.getChildAt(i);
                radioButton.setChecked(true);
                switch (i) {
                    case 0:
                        mIsSelectorBook = true;
                        mPresenter.loadBookList(mAreaId, mCalendarData.get(mTabPosition).date);
                        break;
                    case 1:
                        mIsSelectorBook = false;
                        mPresenter.loadWaitBookList(mAreaId, mCalendarData.get(mTabPosition).date);
                        break;
                    default:
                        break;
                }
                mLlBottom.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRgBooking.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_1:

                    mBvpContent.setCurrentItem(0, true);
                    break;
                case R.id.rb_2:

                    mBvpContent.setCurrentItem(1, true);
                    break;
            }
        });
    }

    @Override
    protected VenueBookingPresenter createPresenter() {
        return new VenueBookingPresenter(this);
    }


    @Override
    public void resultBookList(List<ResultVenueBookBean> data) {
        mBookData.clear();
        mBookData.addAll(data);
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultWaitBookList(List<ResultVenueBookBean> data) {
        mWaitBookData.clear();
        mWaitBookData.addAll(data);
        mPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 当获取订单成功的时候，该订单应该被占用
     *
     * @param orderId        订单Id
     * @param isSelectorBook 包场or待约球的场次
     */
    @Override
    public void resultOrderId(long orderId, boolean isSelectorBook) {
        if (isSelectorBook) {
            if (mBookAdapter != null) {
                mBookData.get(mBookAdapter.getSelectorPosition()).stateId = 1;
                mBookAdapter.notifyDataSetChanged();
            }
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_ORDER_DETAILS, ARouterConfig.Key.ORDER_ID, orderId);
        } else {
            if (mWaitBookAdapter != null) {
                mWaitBookData.get(mWaitBookAdapter.getSelectorPosition()).stateId = 1;
                mWaitBookAdapter.notifyDataSetChanged();
            }
        }


        LogUtils.w("resultOrderId--", orderId + "--");
    }


    @OnClick({R.id.iv_close, R.id.tv_rule, R.id.tv_bottom_left, R.id.tv_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                mIncludeRuleView.setVisibility(View.GONE);
                break;
            case R.id.tv_rule:
                break;
            case R.id.tv_bottom_left:

                if (mIsSelectorBook) {
                    if (mBookAdapter != null) {
                        RequestVenueOrderBean bean = new RequestVenueOrderBean();
                        bean.areaId = mAreaId;
                        bean.calendarId = new long[]{mBookData.get(mBookAdapter.getSelectorPosition()).calendarId};
                        bean.flag = 2;
                        bean.stadiumId = mStadiumId;
                        mPresenter.createOrder(bean, mIsSelectorBook);
                    }
                } else {

                }
                break;
            case R.id.tv_bottom_right:
                if (mIsSelectorBook) {
                    Bundle bundle = new Bundle();
                    bundle.putLong(ARouterConfig.Key.STADIUM_ID,mStadiumId);
                    bundle.putLong(ARouterConfig.Key.CALENDAR_ID,mBookData.get(mBookAdapter.getSelectorPosition()).calendarId);
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LAUNCHER_BALL, bundle);
                }
                break;
        }
    }


    class BookPagerAdapter extends PagerAdapter {

        private int mChildCount;

        private View mBookInflateView;
        private RecyclerView mBookRvData;


        private View mWaitBookInflateView;
        private RecyclerView mWaitBookRvData;

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            switch (position) {
                case 0:
                    if (mBookInflateView == null) {
                        mBookInflateView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_venue_book_pager_view, container, false);
                        container.addView(mBookInflateView);
                        mBookRvData = mBookInflateView.findViewById(R.id.rv_data);
                        mBookRvData.setLayoutManager(new LinearLayoutManager(container.getContext()));
                    }

                    if (mBookAdapter == null) {
                        mBookAdapter = new VenueBookAdapter(mBookData);
                        mBookAdapter.setHasStableIds(true);
                        mBookRvData.setAdapter(mBookAdapter);
                        mBookAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onNotNetClick(View view) {

                            }

                            @Override
                            public void onNotDataClick(View view) {

                            }

                            @Override
                            public void onItemClick(View view, int position) {

                                if (mBookData.get(position).isCheck) {
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.money_make_booking, mBookData.get(position).price));
                                    mLlBottom.setVisibility(View.VISIBLE);
                                } else {
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.make_a_block_booking));
                                    mLlBottom.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        mBookAdapter.notifyDataSetChanged();
                    }
                    LogUtils.w("instantiateItem--", mBookRvData.hashCode() + "--");
                    break;
                case 1:
                    if (mWaitBookInflateView == null) {
                        mWaitBookInflateView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_venue_book_pager_view, container, false);
                        container.addView(mWaitBookInflateView);
                        mWaitBookRvData = mWaitBookInflateView.findViewById(R.id.rv_data);
                        mWaitBookRvData.setLayoutManager(new LinearLayoutManager(container.getContext()));
                    }
                    if (mWaitBookAdapter == null) {
                        mWaitBookAdapter = new VenueWaitBookAdapter(mWaitBookData);
                        mWaitBookRvData.setAdapter(mWaitBookAdapter);
                        mWaitBookAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onNotNetClick(View view) {

                            }

                            @Override
                            public void onNotDataClick(View view) {

                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                if (mWaitBookData.get(position).isCheck) {
                                    mLlBottom.setVisibility(View.VISIBLE);
                                } else {
                                    mLlBottom.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        mWaitBookAdapter.notifyDataSetChanged();
                    }
                    LogUtils.w("instantiateItem---", mBookRvData.hashCode() + "===" + mWaitBookRvData.hashCode());
                    break;
                default:
                    break;
            }

            return position == 1 ? mWaitBookInflateView : mBookInflateView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //  container.removeView((View) object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return mRgBooking.getChildCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return o == view;
        }
    }
}

package com.ifeell.app.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.media.IntentData;
import com.ifeell.app.aboutball.other.ActivityManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.adapter.VenueBookAdapter;
import com.ifeell.app.aboutball.venue.adapter.VenueWaitBookAdapter;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultNotBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;
import com.ifeell.app.aboutball.venue.contract.VenueBookingContract;
import com.ifeell.app.aboutball.venue.presenter.VenueBookingPresenter;
import com.ifeell.app.aboutball.weight.BaseViewPager;
import com.ifeell.app.aboutball.weight.NotAboutBallDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_rule)
    TextView mTvRule;
    @BindView(R.id.rl_create_sure)
    RelativeLayout mRlCreateSure;
    @BindView(R.id.ll_parent_bottom)
    LinearLayout mLlParentBottom;
    private int mTabPosition;
    private long mAreaId, mStadiumId;
    private String mDate;
    private List<ResultVenueBookBean> mBookData;
    private BookPagerAdapter mPagerAdapter;
    private List<ResultVenueDetailsBean.CalendarListForAreaList> mCalendarData;
    private boolean mIsSelectorBook = true;
    private List<ResultVenueBookBean> mWaitBookData;
    private VenueBookAdapter mBookAdapter;
    private VenueWaitBookAdapter mWaitBookAdapter;
    private static final int REQUEST_CODE_INVITATION = 112;
    private boolean mIsCreateBall;
    private NotAboutBallDialog mNotBookDialog;

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
        registerEventBus();
        LogUtils.w("sendVenueTypeMessage--", "我收到消息啦initView" + "--");
        mIncludeRuleView.setBackgroundResource(R.color.colorFFEBF0FF);
        mIvClose.setImageResource(R.mipmap.icon_item_right);
        mTvRule.setTextColor(ContextCompat.getColor(this, R.color.color_2));
        mTabPosition = bundle.getInt(ARouterConfig.Key.POSITION, 0);
        mAreaId = bundle.getLong(ARouterConfig.Key.AREA_ID, 0);
        mStadiumId = bundle.getLong(ARouterConfig.Key.STADIUM_ID, 0);
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
                mRlCreateSure.setVisibility(View.GONE);
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
            if (mBookAdapter != null && mBookAdapter.getCheckData().size() > 0) {


                for (ResultVenueBookBean bean : mBookAdapter.getCheckData()) {
                    bean.stateId = 1;
                    bean.isCheck = false;
                }
                // mBookData.get(mBookAdapter.getCheckData()).stateId = 1;
                mBookAdapter.notifyDataSetChanged();
            }
            // ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, ARouterConfig.Key.ORDER_ID, orderId);
            //包场
            mViewModel.startActivityToOrderPay(orderId, Contast.PayOrderFlag.PAY_VENUE_BOOK);
        } else {
            if (mWaitBookAdapter != null) {
                mWaitBookData.get(mWaitBookAdapter.getSelectorPosition()).stateId = 1;
                mWaitBookAdapter.notifyDataSetChanged();
            }
        }
        mLlBottom.setVisibility(View.GONE);

        LogUtils.w("resultOrderId--", orderId + "--");
    }


    @OnClick({R.id.tv_rule, R.id.tv_bottom_left, R.id.tv_bottom_right, R.id.tv_create_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_rule:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ABOUT_RULE);
                break;
            case R.id.tv_bottom_left:

                if (mIsSelectorBook) {
                    if (mBookAdapter != null) {
                        RequestVenueOrderBean bean = new RequestVenueOrderBean();
                        bean.areaId = mAreaId;
                        List<Long> calendarData = new ArrayList<>();
                        for (ResultVenueBookBean bookBean : mBookAdapter.getCheckData()) {
                            calendarData.add(bookBean.calendarId);
                        }
                        Long[] calendarArray = new Long[]{};
                        bean.calendarId = calendarData.toArray(calendarArray);
                        bean.flag = 3;
                        bean.stadiumId = mStadiumId;
                        mPresenter.createOrder(bean, mIsSelectorBook);
                    }
                } else {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS_VENUE, ARouterConfig.Key.TEAM_ID, mWaitBookData.get(mWaitBookAdapter.getSelectorPosition()).hostTeamId);
                }
                break;
            case R.id.tv_bottom_right:
                if (mIsSelectorBook) {
                    mPresenter.gainNotBooking();
                  /*  Bundle bundle = new Bundle();
                    bundle.putLong(ARouterConfig.Key.STADIUM_ID, mStadiumId);
                    bundle.putLong(ARouterConfig.Key.CALENDAR_ID, mBookData.get(mBookAdapter.getSelectorPosition()).calendarId);
                    bundle.putLong(ARouterConfig.Key.AREA_ID, mAreaId);
                    ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_LAUNCHER_BALL, this, bundle);*/
                } else {

                    ResultVenueBookBean bean = mWaitBookData.get(mWaitBookAdapter.getSelectorPosition());
                    mViewModel.startActivityToInvitation(bean.agreeId, bean.calendarId, VenueBookingActivity.REQUEST_CODE_INVITATION);
                }
                break;
            case R.id.tv_create_sure:
                EventBus.getDefault().post(mBookData.get(mBookAdapter.getSelectorPosition()));

                ActivityManger.get().removeActivity(VenueDetailsActivity.class);
                ActivityManger.get().removeActivity(VenueListActivity.class);
                finish();
                break;

        }
    }

    private void launcherBall(@Nullable ArrayList<ResultNotBookBean> data) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.STADIUM_ID, mStadiumId);
        bundle.putLong(ARouterConfig.Key.CALENDAR_ID, mBookData.get(mBookAdapter.getSelectorPosition()).calendarId);
        bundle.putLong(ARouterConfig.Key.AREA_ID, mAreaId);
        bundle.putParcelableArrayList(ARouterConfig.Key.ARRAY_LIST_PARCELABLE,data);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_LAUNCHER_BALL, this, bundle);
    }

    @Override
    public void resultNotBookData(List<ResultNotBookBean> data) {
        if (data.size() == 0) {
            launcherBall(null);
        } else {
            if (mNotBookDialog == null) {
                mNotBookDialog = new NotAboutBallDialog(this);
            }
            mNotBookDialog.setOnClickAboutItemClickListener(new NotAboutBallDialog.OnClickAboutItemClickListener() {
                @Override
                public void onClickFormerBall(View view) {
                    mNotBookDialog.dismiss();
                    launcherBall((ArrayList<ResultNotBookBean>) data);
                }

                @Override
                public void onClickNewBall(View view) {
                    launcherBall(null);
                    mNotBookDialog.dismiss();
                }
            });
            mNotBookDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //发起约球返回结果
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    mBookData.get(mBookAdapter.getSelectorPosition()).stateId = 1;
                    mBookData.get(mBookAdapter.getSelectorPosition()).isCheck = false;
                    mBookAdapter.notifyDataSetChanged();
                    break;
                case VenueBookingActivity.REQUEST_CODE_INVITATION:
                    mWaitBookData.get(mWaitBookAdapter.getSelectorPosition()).stateId = 1;
                    mWaitBookData.get(mWaitBookAdapter.getSelectorPosition()).isCheck = false;
                    mWaitBookAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
       /* if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mBookData.get(mBookAdapter.getSelectorPosition()).stateId = 1;
            mBookData.get(mBookAdapter.getSelectorPosition()).isCheck = false;
            mBookAdapter.notifyDataSetChanged();
        }*/
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
                                ResultVenueBookBean bean = mBookData.get(position);
                                if (mBookAdapter.getCheckData().size() > 0) {
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.money_make_booking, (DataUtils.getMoneyFormats(mBookData.get(position).price) * mBookAdapter.getCheckData().size())));
                                    mLlBottom.setVisibility(View.VISIBLE);
                                    mTvBottomRight.setText(UIUtils.getString(R.string.s_post_about_ball, DataUtils.getMoneyFormats(mBookData.get(position).price )));
                                    if (mBookAdapter.getCheckData().size() > 1) {
                                        mTvBottomRight.setEnabled(false);
                                        mTvBottomRight.setBackgroundResource(R.drawable.shape_default_button);
                                    } else {
                                        mTvBottomRight.setEnabled(true);
                                        mTvBottomRight.setBackgroundResource(R.drawable.shape_click_button);
                                    }
                                } else {
                                    mLlBottom.setVisibility(View.GONE);
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.make_a_block_booking));
                                }
                                if (mIsCreateBall) {
                                    if (mBookAdapter.getCheckData().size() > 0) {
                                        mRlCreateSure.setVisibility(View.VISIBLE);
                                    } else {
                                        mRlCreateSure.setVisibility(View.GONE);
                                    }
                                    mLlBottom.setVisibility(View.GONE);
                                } /*else {
                                    mLlBottom.setVisibility(View.VISIBLE);
                                }*/


                              /*  if (mBookData.get(position).isCheck) {
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.money_make_booking, mBookData.get(position).price));
                                    mLlBottom.setVisibility(View.VISIBLE);
                                } else {
                                    mTvBottomLeft.setText(UIUtils.getString(R.string.make_a_block_booking));
                                    mLlBottom.setVisibility(View.GONE);
                                }*/
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
                                    mTvBottomLeft.setText(R.string.read_ball_details);
                                    mTvBottomRight.setText(R.string.join_about_ball);
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

    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }

    /**
     * 创建发起约球发送消息
     *
     * @param status
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sendVenueTypeMessage(@NonNull CreateBallActivity.Status status) {
        mBvpContent.setViewPagerScroll(false);
        mRgBooking.setVisibility(View.GONE);
        mIncludeRuleView.setVisibility(View.GONE);
        mLlBottom.setVisibility(View.GONE);
        mIsCreateBall = true;
    }


}

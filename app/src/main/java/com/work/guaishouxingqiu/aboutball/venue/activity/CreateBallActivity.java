package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.PaySucceedActivity;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestCreateBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.CreateBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.CreateBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/5 14:17
 * 更新时间: 2019/8/5 14:17
 * 描述:创建约球
 */
@Route(path = ARouterConfig.Path.ACTIVITY_CREATE_BALL)
public class CreateBallActivity extends BaseActivity<CreateBallPresenter> implements CreateBallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.item_date)
    ItemView mItemDate;
    @BindView(R.id.item_time)
    ItemView mItemTime;
    @BindView(R.id.item_site)
    ItemView mItemSite;
    @BindView(R.id.acet_phone)
    AppCompatEditText mAcetPhone;
    @BindView(R.id.tv_sures)
    TextView mTvSure;
    private ResultMyBallTeamBean mMyBallTeam;
    private SingWheelDialog mTimeDialog;
    private static final int REQUEST_CODE_SITE = 127;
    private VenueDetailsActivity.CreateBean mResultCreateBean;
    private RequestCreateBallBean mRequestBean;
    private ResultAboutBallDetailsBean mIntentBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ball;
    }


    private void initIntent() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean != null) {
            mRequestBean.agreeId = mIntentBean.agreeId;
            mItemTeam.mTvRight.setText(mIntentBean.hostTeamName);
            mItemColor.mTvRight.setText(mIntentBean.hostShirtColor);
            mItemDate.mTvRight.setText(DateUtils.getNextCountData(mIntentBean.startTime, 0));
            mItemTime.setVisibility(View.VISIBLE);
            mItemTime.mTvRight.setText(DateUtils.getStartTime2EndTimeForHourMinute(mIntentBean.startTime, mIntentBean.endTime));
            mItemSite.mTvRight.setText(DataUtils.getNotNullData(mIntentBean.stadiumName));
          //  mRequestBean.calendarId = mIntentBean.calendarId == 0 ? null : mIntentBean.calendarId;
            mRequestBean.hostTeamId = mIntentBean.hostTeamId;
            mRequestBean.hostShirtColor = mIntentBean.hostShirtColor;
            mRequestBean.startTime = mIntentBean.startTime;
            mRequestBean.endTime = mIntentBean.endTime;
            if (mIntentBean.calendarId != 0) {
                mItemSite.mRootView.setEnabled(false);
                mItemDate.mRootView.setEnabled(false);
                mItemTime.mRootView.setEnabled(false);
            } else {
                mItemSite.mRootView.setEnabled(true);
                mItemDate.mRootView.setEnabled(true);
                mItemTime.mRootView.setEnabled(true);
            }
            mTvSure.setText(R.string.edit_about_ball);
            notifyInputAllContent();
        }
    }

    @Override
    protected void initView() {

        mRequestBean = new RequestCreateBallBean();
        mRequestBean.refereeId = new Long[]{};
        registerEventBus();
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);

        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);

        mItemDate.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemDate.mTvRight.setHint(R.string.please_selector_date);

        mItemTime.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTime.mTvRight.setHint(R.string.please_selector_time);

        mItemSite.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemSite.mTvRight.setHint(R.string.book_a_venue_not_selector);
        initIntent();
    }

    @Override
    protected void initData() {

        if (UserManger.get().isLogin() && !DataUtils.isEmpty(UserManger.get().getPhone())) {
            String phoneNumber = UserManger.get().getPhone();
            mAcetPhone.setText(phoneNumber);
            mAcetPhone.setSelection(DataUtils.checkData(mAcetPhone.getText()).length());
            mRequestBean.phone = phoneNumber;
        }


    }

    @Override
    protected void initEvent() {
        mItemTeam.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM,
                        CreateBallActivity.this, ARouterConfig.Key.PARCELABLE, mMyBallTeam);
            }
        });
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(CreateBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        //mRequestBean.guestShirtColor = color;
                        mItemColor.mTvRight.setText(color);
                        mRequestBean.hostShirtColor = color;
                        notifyInputAllContent();
                    });
                }
                if (!mColorDialog.isShowing() && !isFinishing()) {
                    mColorDialog.show();
                }
            }
        });
        mItemDate.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SingWheelDialog mDateDialog;

            @Override
            public void onClickItem(View view) {
                if (mDateDialog == null) {
                    List<String> dateData = DateUtils.getCountYearForDay(new Date(System.currentTimeMillis()), 30);
                    mDateDialog = new SingWheelDialog(CreateBallActivity.this, dateData);
                    mDateDialog.setTitle(R.string.please_selector_date);
                    mDateDialog.setOnItemClickListener((view12, position) -> {
                        mItemDate.mTvRight.setText(dateData.get(position));

                        LogUtils.w("mDateDialog--", DateUtils.isSelectorDayThanNewDay(dateData.get(position)) + "--");
                        mItemTime.setVisibility(View.VISIBLE);
                        notifyInputAllContent();
                    });
                }
                if (!mDateDialog.isShowing() && !isFinishing()) {
                    mDateDialog.show();
                }

            }
        });
        mItemTime.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickShowTimeDialog();
            }
        });
        mItemSite.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                EventBus.getDefault().postSticky(new CreateBallActivity.Status(Contast.VenueType.CREATE_BALL));

                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_VENUE_LIST, CreateBallActivity.this, REQUEST_CODE_SITE);
            }
        });
    }

    private void clickShowTimeDialog() {
        List<String> timeData = Arrays.asList(getResources().getStringArray(R.array.scheduled_venues_time_array));
        if (mTimeDialog == null) {
            mTimeDialog = new SingWheelDialog(this, timeData);
            mTimeDialog.setTitle(R.string.please_selector_time);
            mTimeDialog.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClickItem(@NonNull View view, int position) {
                    if (isSureSelectorTime(DataUtils.getTextViewContent(mItemDate.mTvRight), timeData.get(position))) {
                        String[] timeArray = timeData.get(position).split("-");
                        if (timeArray.length >= 2) {
                            mRequestBean.startTime = DataUtils.getTextViewContent(mItemDate.mTvRight) + " " + timeArray[0] + ":00";
                            mRequestBean.endTime = DataUtils.getTextViewContent(mItemDate.mTvRight) + " " + timeArray[1] + ":00";
                        }
                        mItemTime.mTvRight.setText(timeData.get(position));
                    } else {
                        UIUtils.showToast(R.string.selector_venue_time_than_new_time);
                    }
                    notifyInputAllContent();
                }
            });
        }
        if (!mTimeDialog.isShowing() && !isFinishing()) {
            mTimeDialog.show();
        }
    }


    @OnClick(R.id.tv_sures)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.tv_sures:
                if (mIntentBean == null) {
                    mPresenter.createPostBall(mRequestBean);
                } else {
                    mPresenter.editAboutBall(mRequestBean);
                }
                break;
        }
    }

    /**
     * 是否可以选择当前时间
     *
     * @param date
     * @param time
     * @return
     */
    private boolean isSureSelectorTime(String date, String time) {
        if (DateUtils.isSelectorDayThanNewDay(date)) {
            return true;
        }
        String[] timeArray = time.split("-");
        if (timeArray.length > 0) {
            String[] hourArray = timeArray[0].split(":");
            if (hourArray.length > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                return Integer.valueOf(hourArray[0]) > calendar.get(Calendar.HOUR_OF_DAY);
            }
        }
        return false;
    }

    @Override
    protected CreateBallPresenter createPresenter() {
        return new CreateBallPresenter(this);
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
                    mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    mItemTeam.setContentText(mMyBallTeam.teamName);
                    mRequestBean.hostTeamId = mMyBallTeam.teamId;
                    notifyInputAllContent();
                    // mRequestBean.guestTeamId = mMyBallTeam.teamId;
                    break;
                default:
                    break;
            }
        }
    }

    public void notifyInputAllContent() {
        if (!DataUtils.isEmpty(DataUtils.getTextViewContent(mItemTeam.mTvRight)) &&
                !DataUtils.isEmpty(DataUtils.getTextViewContent(mItemColor.mTvRight)) &&
                !DataUtils.isEmpty(DataUtils.getTextViewContent(mItemDate.mTvRight)) &&
                !DataUtils.isEmpty(DataUtils.getTextViewContent(mItemTime.mTvRight))) {
            mTvSure.setEnabled(true);
            mTvSure.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_click_button));
        } else {
            mTvSure.setEnabled(false);
            mTvSure.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_default_button));

        }

    }


    public static class Status {
        public int mType;

        public Status(int type) {
            this.mType = type;
        }
    }

    @Override
    protected void onDestroy() {
       // EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().removeStickyEvent(VenueDetailsActivity.CreateBean.class);
        unRegisterEventBus();
        super.onDestroy();
    }

    /**
     * 回调场地信息bean
     *
     * @param venueBookBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resultBallMessage(@NonNull ResultVenueBookBean venueBookBean) {
        if (mResultCreateBean != null) {
            mItemSite.mTvRight.setText(mResultCreateBean.venueName.concat(mResultCreateBean.screening));
        }
        mItemDate.mTvRight.setText(DateUtils.getNextCountData(venueBookBean.startTime, 0));
        mItemTime.setVisibility(View.VISIBLE);
        mRequestBean.calendarId = venueBookBean.calendarId;
        mRequestBean.startTime = venueBookBean.startTime;
        mRequestBean.endTime = venueBookBean.endTime;
        mItemTime.mTvRight.setText(DateUtils.getStartTime2EndTimeForHourMinute(venueBookBean.startTime, venueBookBean.endTime));
        notifyInputAllContent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resultVenueName(VenueDetailsActivity.CreateBean createBean) {
        this.mResultCreateBean = createBean;
        if (createBean != null) {
            mItemSite.mTvRight.setText(createBean.venueName.concat(createBean.screening));
        }
    }

    @Override
    public void resultCreateBallOrderId(String orderId) {
        if (orderId != null) {
            EventBus.getDefault().postSticky(new PaySucceedActivity.Type(1) );
            mViewModel.startActivityToOrderPay(Long.valueOf(orderId), Contast.PayOrderFlag.PAY_LAUNCHER_ORDER);
        }
        mViewModel.clickBackForResult();

    }
}

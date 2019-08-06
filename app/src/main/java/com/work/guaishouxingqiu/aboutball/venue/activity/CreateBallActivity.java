package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.CreateBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.CreateBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private ResultMyBallTeamBean mMyBallTeam;
    private SingWheelDialog mTimeDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ball;
    }

    @Override
    protected void initView() {
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);

        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);

        mItemDate.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemDate.mTvRight.setHint(R.string.please_selector_date);

        mItemTime.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTime.mTvRight.setHint(R.string.please_selector_time);

        mItemSite.mTvRight.setHintTextColor(ContextCompat.getColor(this,R.color.colorFFA6A6A6));
        mItemSite.mTvRight.setHint(R.string.book_a_venue_not_selector);

    }

    @Override
    protected void initData() {
        if (UserManger.get().isLogin() && !DataUtils.isEmpty(UserManger.get().getPhone())) {
            mAcetPhone.setText(UserManger.get().getPhone());
            mAcetPhone.setSelection(DataUtils.checkData(mAcetPhone.getText()).length());
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
                        mItemTime.mTvRight.setText(timeData.get(position));
                    } else {
                        UIUtils.showToast(R.string.selector_venue_time_than_new_time);
                    }
                }
            });
        }
        if (!mTimeDialog.isShowing() && !isFinishing()) {
            mTimeDialog.show();
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
        String[] timeArray = time.split(" - ");
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
                    // mRequestBean.guestTeamId = mMyBallTeam.teamId;
                    break;
                default:
                    break;
            }
        }
    }
    public boolean isInputAllContent(){
       return true;
    }
}

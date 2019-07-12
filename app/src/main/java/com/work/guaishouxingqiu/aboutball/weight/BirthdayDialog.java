package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 10:03
 * 更新时间: 2019/4/1 10:03
 * 描述:生日Dialog
 */
public class BirthdayDialog extends BaseDialog {
    private static final int MIN_YEAR = 1900;

    private TitleView mTitleView;
    private WheelView mLeftWheelView;
    private WheelView mRightWheelView;
    private WheelView mCenterWheelView;
    private Calendar mCalendar;
    private List<String> mYearData;
    private List<String> mMonthData;
    private List<String> mDayData;
    private ArrayWheelAdapter<String> mRightAdapter;
    private ArrayWheelAdapter<String> mCenterAdapter;
    private int mNesYear;
    private int mNesMonth;
    private int mNewDay;

    public void setOnBirthdayResultListener(OnBirthdayResultListener onBirthdayResultListener) {
        this.onBirthdayResultListener = onBirthdayResultListener;
    }

    private OnBirthdayResultListener onBirthdayResultListener;

    public BirthdayDialog(Context context) {
        super(context);
    }

    public void setBirthdayDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date time = dateFormat.parse(date);
            mCalendar.clear();
            mCalendar.setTime(time);
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            LogUtils.w("setBirthdayDate--", year + "--" + month + "--" + day);
            mLeftWheelView.setCurrentItem(mYearData.indexOf(String.valueOf(year)));
            mMonthData.clear();
            int monthLength;
            if (year == mNesYear) {
                monthLength = mNesMonth + 1;
            } else {
                monthLength = 12;
            }
            for (int i = 0; i < monthLength; i++) {
                mMonthData.add(String.valueOf(i + 1));
            }

            mCenterWheelView.setCurrentItem(mMonthData.indexOf(String.valueOf(month + 1)));
            mCenterWheelView.setAdapter(mCenterAdapter);

            mDayData.clear();
            int dayLength;
            if (mNesYear == mCalendar.get(Calendar.YEAR) && mNesMonth == mCalendar.get(Calendar.MONTH)) {
                dayLength = mNewDay;
            } else {
                //获取月份的日期长度，月份要+1
                mCalendar.set(year, month+1, 0);
                dayLength = mCalendar.get(Calendar.DAY_OF_MONTH);
            }
            // dayLength = mCalendar.get(Calendar.DAY_OF_MONTH);
            LogUtils.w("notifyRightData--", dayLength + "--" + year + "--" + month);
            for (int i = 1; i <= dayLength; i++) {
                mDayData.add(String.valueOf(i));
            }
            mRightWheelView.setCurrentItem(mDayData.indexOf(String.valueOf(day)));
            mRightWheelView.setAdapter(mRightAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initEvent() {
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                dismiss();
            }

            @Override
            public void onSureClick(@NonNull View view) {
                String date = mYearData.get(mLeftWheelView.getCurrentItem())
                        .concat("-")
                        .concat(Integer.valueOf(mMonthData.get(mCenterWheelView.getCurrentItem())) >= 10 ?
                                mMonthData.get(mCenterWheelView.getCurrentItem())
                                : "0" + mMonthData.get(mCenterWheelView.getCurrentItem()))
                        .concat("-")
                        .concat(Integer.valueOf(mDayData.get(mRightWheelView.getCurrentItem())) >= 10 ?
                                mDayData.get(mRightWheelView.getCurrentItem())
                                : "0" + mDayData.get(mRightWheelView.getCurrentItem()));
                if (onBirthdayResultListener != null) {
                    onBirthdayResultListener.onBirthdayResult(date);
                }
                dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        mCalendar = Calendar.getInstance();
        initLeftData();
        initCenterData();
        initRightData();
    }

    private void initRightData() {
        mDayData = new ArrayList<>();
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        mNewDay = day;
        //   mCalendar.set(year, month, 0);
        for (int i = 1; i <= day; i++) {
            mDayData.add(String.valueOf(i));
        }
        mRightAdapter = new ArrayWheelAdapter<>(mDayData);
        mRightWheelView.setCurrentItem(day - 1);
        mRightWheelView.setAdapter(mRightAdapter);

    }

    private void initCenterData() {
        mMonthData = new ArrayList<>();
        int month = mCalendar.get(Calendar.MONTH);
        mNesMonth = month;
        for (int i = 0; i <= month; i++) {
            mMonthData.add(String.valueOf(i + 1));
        }
        mCenterAdapter = new ArrayWheelAdapter<>(mMonthData);
        mCenterWheelView.setCurrentItem(mCalendar.get(Calendar.MONTH));
        mCenterWheelView.setAdapter(mCenterAdapter);
        mCenterWheelView.setOnItemSelectedListener(index -> {
            mCalendar.set(Integer.valueOf(mYearData.get(mLeftWheelView.getCurrentItem())),
                    Integer.valueOf(mMonthData.get(index)), 0);
            notifyRightData();
        });
    }

    private void initLeftData() {
        mYearData = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());
        mCalendar.setTime(date);
        int year = mCalendar.get(Calendar.YEAR);
        mNesYear = year;
        for (int i = MIN_YEAR; i <= year; i++) {
            mYearData.add(String.valueOf(i));
        }
        ArrayWheelAdapter<String> leftAdapter = new ArrayWheelAdapter<>(mYearData);
        mLeftWheelView.setCurrentItem(mYearData.size());
        mLeftWheelView.setAdapter(leftAdapter);
        mLeftWheelView.setOnItemSelectedListener(index -> {
            if (mMonthData != null) {
                mCalendar.set(Integer.valueOf(mYearData.get(index)),
                        Integer.valueOf(mMonthData.get(mCenterWheelView.getCurrentItem())), 0);
            }
            notifyCenterData();
            notifyRightData();
        });
    }


    private void notifyCenterData() {
        if (mCenterAdapter != null && mMonthData != null) {
            mMonthData.clear();
            int year = mCalendar.get(Calendar.YEAR);
            int monthLength;
            if (year == mNesYear) {
                monthLength = mNesMonth + 1;
            } else {
                monthLength = 12;
            }
            for (int i = 0; i < monthLength; i++) {
                mMonthData.add(String.valueOf(i + 1));
            }
            mCenterWheelView.setCurrentItem(0);
            mCenterWheelView.setAdapter(mCenterAdapter);
        }
    }

    private void notifyRightData() {
        if (mRightAdapter != null && mDayData != null) {
            mDayData.clear();
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH) + 1;

            int dayLength;
            if (mNesYear == mCalendar.get(Calendar.YEAR) && mNesMonth == mCalendar.get(Calendar.MONTH)) {
                dayLength = mNewDay;
            } else {
                mCalendar.set(year, month, 0);
                dayLength = mCalendar.get(Calendar.DAY_OF_MONTH);
            }
            // dayLength = mCalendar.get(Calendar.DAY_OF_MONTH);
            LogUtils.w("notifyRightData--", dayLength + "--" + year + "--" + month);
            for (int i = 1; i <= dayLength; i++) {
                mDayData.add(String.valueOf(i));
            }
            mRightWheelView.setCurrentItem(0);
            mRightWheelView.setAdapter(mRightAdapter);
        }
    }

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_birthday_view, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mLeftWheelView = inflateView.findViewById(R.id.wv_left);
        mRightWheelView = inflateView.findViewById(R.id.wv_right);
        mCenterWheelView = inflateView.findViewById(R.id.wv_center);
        initWheelView(mLeftWheelView);
        initWheelView(mRightWheelView);
        initWheelView(mCenterWheelView);

        this.setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        //layoutParams.height = ScreenUtils.dp2px(getContext(), 270);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }

    private void initWheelView(WheelView wheelView) {
        wheelView.setCyclic(false);
        wheelView.setLineSpacingMultiplier(2.0f);
        wheelView.setTextColorCenter(ContextCompat.getColor(getContext(), R.color.colorFF333333));
        wheelView.setGravity(Gravity.CENTER);
    }

    public interface OnBirthdayResultListener {
        void onBirthdayResult(@NonNull String date);
    }
}

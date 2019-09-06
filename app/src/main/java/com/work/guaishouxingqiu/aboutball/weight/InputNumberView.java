package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/5 13:58
 * 更新时间: 2019/9/5 13:58
 * 描述: 数量加减view
 */
public class InputNumberView extends LinearLayout {

    private int mMaxNum;
    private int mDefaultNum;
    private int mDigit;
    private TextView mTvSubtract;
    private AppCompatEditText mAcetNum;
    private TextView mTvAdd;

    public InputNumberView(Context context) {
        super(context);
        init();
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView);
        if (typedArray != null) {
            mMaxNum = typedArray.getInteger(R.styleable.InputNumberView_inv_input_max_num, 999);
            mDefaultNum = typedArray.getInteger(R.styleable.InputNumberView_inv_default_num, 1);
            mDigit = typedArray.getInteger(R.styleable.InputNumberView_inv_input_digit, 3);
            typedArray.recycle();
        }
        init();

    }

    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.view_input_number, this, true);
        mTvSubtract = inflateView.findViewById(R.id.tv_subtract);
        mAcetNum = inflateView.findViewById(R.id.et_num);
        mTvAdd = inflateView.findViewById(R.id.tv_add);
    }

    private void initData() {
        mAcetNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mDigit)});
        mAcetNum.setText(String.valueOf(mDefaultNum));
    }

    private void clickSubtractAddView(boolean isAdd) {
        int number;
        if (isAdd) {
            number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum)) + 1;
        } else {
            number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum)) - 1;
        }
        mAcetNum.setText(String.valueOf(number));
        if (mAcetNum.isFocused()) {
            mAcetNum.setSelection(mAcetNum.getText().length());
        }
    }

    private void initEvent() {
        mTvSubtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubtractAddView(false);
            }
        });
        mTvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubtractAddView(true);

            }
        });
        mAcetNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum));
                if (number <= 0) {
                    mTvSubtract.setBackgroundResource(R.drawable.layer_default_left_six_radius_view);
                    mTvSubtract.setEnabled(false);
                } else {
                    mTvSubtract.setBackgroundResource(R.drawable.layer_click_left_six_radius_view);
                    mTvSubtract.setEnabled(true);
                }

                if (number >= mMaxNum) {
                    mTvAdd.setEnabled(false);
                    mTvAdd.setBackgroundResource(R.drawable.layer_default_right_six_radius_view);
                } else {
                    mTvAdd.setEnabled(true);
                    mTvAdd.setBackgroundResource(R.drawable.layer_click_right_six_radius_view);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setMaxNum(int maxNum) {
        this.mMaxNum = maxNum;
    }

    public void setDigit(int digitNum) {
        this.mDigit = digitNum;
        mAcetNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mDigit)});
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int resultWidth = ScreenUtils.dp2px(getContext(), 115);
        int resultHeight = ScreenUtils.dp2px(getContext(), 30);
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }
}

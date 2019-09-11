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
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
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
    private int mMinNum;
    private static final int DEFAULT_MAX_COUNT = 999;

    public void setOnClickInputClickListener(OnClickInputClickListener onClickInputClickListener) {
        this.onClickInputClickListener = onClickInputClickListener;
    }

    private OnClickInputClickListener onClickInputClickListener;

    public InputNumberView(Context context) {
        super(context);
        init();
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView);
        if (typedArray != null) {
            mMaxNum = typedArray.getInteger(R.styleable.InputNumberView_inv_input_max_num, DEFAULT_MAX_COUNT);
            mMaxNum = mMaxNum > DEFAULT_MAX_COUNT ? DEFAULT_MAX_COUNT : mMaxNum;
            mDefaultNum = typedArray.getInteger(R.styleable.InputNumberView_inv_default_num, 1);
            mDigit = typedArray.getInteger(R.styleable.InputNumberView_inv_input_digit, 3);
            mMinNum = typedArray.getInteger(R.styleable.InputNumberView_inv_input_min_num, 0);
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
        int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum));
        LogUtils.w("initData--", number + "--" + (number > mMinNum)+"--"+(number < mMaxNum)+"--"+mMaxNum);
        setSubtractClickable(number > mMinNum);
        setAddClickable(number < mMaxNum);
    }

    public void clickSubtractAddView(boolean isAdd) {
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

    public void setClickable(boolean clickable) {
        setAddClickable(clickable);
        setSubtractClickable(clickable);
    }

    public void setAddClickable(boolean clickable) {
        if (clickable) {
            mTvAdd.setBackgroundResource(R.drawable.layer_click_right_six_radius_view);
        } else {
            mTvAdd.setBackgroundResource(R.drawable.layer_default_right_six_radius_view);
        }
        mTvAdd.setEnabled(clickable);
        if (mAcetNum.isFocused()) {
            mAcetNum.setSelection(mAcetNum.getText().length());
        }
    }

    private void setSubtractClickable(boolean clickable) {
        if (clickable) {
            mTvSubtract.setBackgroundResource(R.drawable.layer_click_left_six_radius_view);
        } else {
            mTvSubtract.setBackgroundResource(R.drawable.layer_default_left_six_radius_view);
        }
        mTvSubtract.setEnabled(clickable);
        if (mAcetNum.isFocused()) {
            mAcetNum.setSelection(mAcetNum.getText().length());
        }
    }

    private void initEvent() {
        mTvSubtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubtractAddView(false);
                if (onClickInputClickListener != null) {
                    onClickInputClickListener.onClickSubtract(v, getNum());
                }
            }
        });
        mTvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubtractAddView(true);
                if (onClickInputClickListener != null) {
                    onClickInputClickListener.onClickAdd(v, getNum());
                }
            }
        });
        mAcetNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum));
              /*  if (number <= 0) {
                    mTvSubtract.setBackgroundResource(R.drawable.layer_default_left_six_radius_view);
                    mTvSubtract.setEnabled(false);
                } else {
                    mTvSubtract.setBackgroundResource(R.drawable.layer_click_left_six_radius_view);
                    mTvSubtract.setEnabled(true);
                }*/
                setSubtractClickable(number > mMinNum);
                setAddClickable(number < mMaxNum);
                if (number > mMaxNum) {
                    mAcetNum.setText(String.valueOf(mMaxNum));
                    if (mAcetNum.isFocused()) {
                        mAcetNum.setSelection(mAcetNum.getText().length());
                    }
                }
                if (number < mMinNum) {
                    mAcetNum.setText(String.valueOf(mMinNum));
                    if (mAcetNum.isFocused()) {
                        mAcetNum.setSelection(mAcetNum.getText().length());
                    }
                }
          /*      if (number >= mMaxNum) {
                    mTvAdd.setEnabled(false);
                    mTvAdd.setBackgroundResource(R.drawable.layer_default_right_six_radius_view);
                } else {
                    mTvAdd.setEnabled(true);
                    mTvAdd.setBackgroundResource(R.drawable.layer_click_right_six_radius_view);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setMaxNum(int maxNum) {
        this.mMaxNum = maxNum > DEFAULT_MAX_COUNT || maxNum <= 0 ? DEFAULT_MAX_COUNT : maxNum;
        int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum));
        setAddClickable(number < mMaxNum);
    }

    public void setInputNum(int num) {
        if (num < mMinNum) {
            num = mMinNum;
        }
        if (num > mMaxNum) {
            num = mMaxNum;
        }
        mAcetNum.setText(String.valueOf(num));
        if (mAcetNum.isFocused()) {
            mAcetNum.setSelection(DataUtils.getEditDetails(mAcetNum).length());
        }
    }

    public void setDigit(int digitNum) {
        this.mDigit = digitNum;
        mAcetNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mDigit)});
    }

    public int getNum() {
        return DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum));
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

    public interface OnClickInputClickListener {
        void onClickSubtract(View view, int num);

        void onClickAdd(View view, int num);
    }
}

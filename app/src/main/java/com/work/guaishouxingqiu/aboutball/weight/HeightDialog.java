package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/29 10:55
 * 更新时间: 2019/3/29 10:55
 * 描述:体重Dialog
 */
public class HeightDialog extends BaseDialog {

    private WheelView mWheelView;
    private TitleView mTitleView;
    private static final int MIN_HEIGHT = 50;
    private static final int MAX_HEIGHT = 300;
    private List<String> mData;

    public void setOnHeightResultListener(OnHeightResultListener onHeightResultListener) {
        this.onHeightResultListener = onHeightResultListener;
    }

    private OnHeightResultListener onHeightResultListener;

    public HeightDialog(Context context) {
        super(context);
    }

    public void setHeight(int height) {
        if (mWheelView != null) {
            mWheelView.setCurrentItem(height >= MIN_HEIGHT ? height - MIN_HEIGHT : 0);
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
                if (onHeightResultListener != null) {
                    onHeightResultListener.onResultHeight(mData.get(mWheelView.getCurrentItem()));
                }
                dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = MIN_HEIGHT; i < MAX_HEIGHT; i++) {
            mData.add(String.valueOf(i));
        }
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(mData);
        mWheelView.setAdapter(adapter);
    }

    @Override
    protected void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_height_picker, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mWheelView = inflateView.findViewById(R.id.wv_data);
        mWheelView.setCyclic(false);
        mWheelView.setLineSpacingMultiplier(2.0f);
        mWheelView.setTextColorCenter(ContextCompat.getColor(getContext(), R.color.colorFF333333));
        mWheelView.setGravity(Gravity.CENTER);
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

    public interface OnHeightResultListener {
        void onResultHeight(String height);
    }
}

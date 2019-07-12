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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/29 9:16
 * 更新时间: 2019/3/29 9:16
 * 描述:体重Dialog
 */
public class WeightDialog extends BaseDialog {


    public void setOnWeightResultListener(OnWeightResultListener onWeightResultListener) {
        this.onWeightResultListener = onWeightResultListener;
    }

    private OnWeightResultListener onWeightResultListener;
    private TitleView mTitleView;
    private WheelView mLeftWheelView, mRightWheelView;

    public WeightDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_weight_picker, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mLeftWheelView = inflateView.findViewById(R.id.wv_left);
        mRightWheelView = inflateView.findViewById(R.id.wv_right);
        initWheelView(mLeftWheelView);
        initWheelView(mRightWheelView);
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

    @Override
    protected void initData() {
        initLeftData();
        initRightData();
    }


    private void initLeftData() {
        List<String> leftData = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            leftData.add(String.valueOf(i * 10));

        }
        ArrayWheelAdapter<String> leftAdapter = new ArrayWheelAdapter<>(leftData);
        mLeftWheelView.setCurrentItem(10);
        mLeftWheelView.setAdapter(leftAdapter);


    }

    private void initRightData() {
        List<String> rightData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rightData.add(String.valueOf(i));
        }
        ArrayWheelAdapter<String> rightAdapter = new ArrayWheelAdapter<>(rightData);
        mRightWheelView.setCurrentItem(5);
        mRightWheelView.setAdapter(rightAdapter);
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
                if (mLeftWheelView.getCurrentItem() * 10 + mRightWheelView.getCurrentItem() <= 0) {
                    Toasts.with().showToast(R.string.weight_not_zero);
                } else {
                    if (onWeightResultListener != null) {
                        onWeightResultListener.onResult(String.valueOf(mLeftWheelView.getCurrentItem() * 10 + mRightWheelView.getCurrentItem()));
                    }
                    dismiss();
                }
            }
        });

    }

    public void setWeight(int weight) {
        if (mLeftWheelView != null) {
            mLeftWheelView.setCurrentItem(weight / 10);
        }
        if (mRightWheelView != null) {
            mRightWheelView.setCurrentItem(weight % 10);
        }
    }

    public interface OnWeightResultListener {
        void onResult(String weight);
    }

}

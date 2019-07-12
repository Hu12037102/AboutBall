package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;

import java.util.Arrays;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 17:57
 * 更新时间: 2019/4/23 17:57
 * 描述:颜色选择dialog
 */
public class SelectorColorDialog extends BaseDialog {
    private TitleView mTitleView;
    private WheelView mWheelView;
    private List<String> mData;

    public void setOnColorSelectorListener(OnColorSelectorListener onColorSelectorListener) {
        this.onColorSelectorListener = onColorSelectorListener;
    }

    private OnColorSelectorListener onColorSelectorListener;

    public SelectorColorDialog(Context context) {
        super(context);
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
                if (onColorSelectorListener != null) {
                    onColorSelectorListener.onResult(view, mData.get(mWheelView.getCurrentItem())+"色");
                }
                dismiss();
            }
        });
    }

    public void setTitle(@StringRes int titleRes) {
        mTitleView.mTvCenter.setText(titleRes);
    }

    public void setTitle(@NonNull String title) {
        mTitleView.mTvCenter.setText(title);
    }

    @Override
    protected void initData() {
        mTitleView.mTvCenter.setText(R.string.selector_ball_clothing_color);
        mData = Arrays.asList(getContext().getResources().getStringArray(R.array.clothing_color_array));
        ArrayWheelAdapter<String> wheelAdapter = new ArrayWheelAdapter<>(mData);
        mWheelView.setAdapter(wheelAdapter);

    }

    @Override
    protected void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sing_wheel_view, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mWheelView = inflateView.findViewById(R.id.wv_data);
        mWheelView.setCyclic(false);

        mWheelView.setLineSpacingMultiplier(2.0f);
        mWheelView.setTextColorCenter(ContextCompat.getColor(getContext(), R.color.colorFF333333));
        mWheelView.setGravity(Gravity.CENTER);
    }

    public interface OnColorSelectorListener {
        void onResult(@NonNull View view, String color);
    }
}

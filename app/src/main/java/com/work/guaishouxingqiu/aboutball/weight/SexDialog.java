package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;

import java.util.Arrays;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:51
 * 更新时间: 2019/3/20 11:51
 * 描述:性别选择器dialog
 */
public class SexDialog extends AppCompatDialog {

    private TitleView mTitleView;
    private WheelView mWheelView;

    public void setOnSelectorSexResult(OnSelectorSexResult onSelectorSexResult) {
        this.onSelectorSexResult = onSelectorSexResult;
    }

    private OnSelectorSexResult onSelectorSexResult;

    public SexDialog(Context context) {
        this(context, R.style.DefaultDialogStyle);
    }

    public SexDialog(Context context, int theme) {
        super(context, theme);
        initView();
        initData();
        initEvent();
    }

    public void setSexIndex(int sexType) {
        if (mWheelView != null) {
            mWheelView.setCurrentItem(sexType == 0 ? 0 : sexType - 1);
        }
    }

    private void initView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sex_picker_view, null);
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

    private void initData() {
        List<String> mData = Arrays.asList(getContext().getResources().getStringArray(R.array.sex_array));
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(mData);

        mWheelView.setAdapter(adapter);

    }

    private void initEvent() {
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                dismiss();
            }

            @Override
            public void onSureClick(@NonNull View view) {
                if (onSelectorSexResult != null) {
                    onSelectorSexResult.onSexResult(mWheelView.getCurrentItem() + 1);
                }
                dismiss();
            }
        });

    }

    public interface OnSelectorSexResult {
        void onSexResult(int sexType);
    }
}

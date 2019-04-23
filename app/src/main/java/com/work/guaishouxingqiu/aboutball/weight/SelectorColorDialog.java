package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

    public SelectorColorDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        List<String> mData = Arrays.asList(getContext().getResources().getStringArray(R.array.clothing_color_array));
        ArrayWheelAdapter<String> wheelAdapter = new ArrayWheelAdapter<>(mData);
        mWheelView.setAdapter(wheelAdapter);
    }

    @Override
    protected void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_selector_color_view, null);
        setContentView(inflateView);
        mTitleView = inflateView.findViewById(R.id.title_view);
        mWheelView = inflateView.findViewById(R.id.wv_data);
        mWheelView.setCyclic(false);

        mWheelView.setLineSpacingMultiplier(2.0f);
        mWheelView.setTextColorCenter(ContextCompat.getColor(getContext(), R.color.colorFF333333));
        mWheelView.setGravity(Gravity.CENTER);
    }
}

package com.ifeell.app.aboutball.weight;

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
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 13:50
 * 更新时间: 2019/4/24 13:50
 * 描述:
 */
public  class SingWheelDialog extends BaseDialog {
    private List<String> mData;
    private TitleView mTitleView;
    private WheelView mWheelView;
    private ArrayWheelAdapter<String> mWheelAdapter;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public SingWheelDialog(Context context, List<String> data) {
        super(context);
        this.mData = data;
        initViews(context);
        initDatas();
        initEvents();

    }

    private void initViews(Context context) {
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

    private void initDatas() {
        mWheelAdapter = new ArrayWheelAdapter<>(mData);
        mWheelView.setAdapter(mWheelAdapter);
    }

    private void initEvents() {
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                dismiss();
            }

            @Override
            public void onSureClick(@NonNull View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(view, mWheelView.getCurrentItem());
                }
                dismiss();
            }
        });
    }

    public void notifyData(List<String> data){
        mData.clear();
        mData.addAll(data);
        mWheelView.setAdapter(mWheelAdapter);
    }

    public void setTitle(@NonNull String title){
        mTitleView.mTvCenter.setText(title);
    }
    public void setTitle(@StringRes int titleRes){
        mTitleView.mTvCenter.setText(titleRes);
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {

    }

}

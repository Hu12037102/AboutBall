package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/28 10:55
 * 更新时间: 2019/8/28 10:55
 * 描述: 确认订单dialog
 */
public class SureOrderDialog extends BaseDialog {

    private ImageView mIvClose;
    private TextView mTvSures;
    private InputNumberView mInvCount;

    public SureOrderDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {
        mIvClose.setOnClickListener(v -> dismiss());
        /*mTvSubtract.setOnClickListener(v -> {
            int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum)) - 1;
            mAcetNum.setText(String.valueOf(number));
        });
        mTvAdd.setOnClickListener(v -> {
            int number = DataUtils.getIntFormat(DataUtils.getEditDetails(mAcetNum)) + 1;
            mAcetNum.setText(String.valueOf(number));
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
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sure_order_view, null);
        setContentView(view);
        mTvSures = findViewById(R.id.tv_sures);
        mIvClose = findViewById(R.id.iv_close);
        mInvCount = findViewById(R.id.inv_count);
        LinearLayout mLlData = findViewById(R.id.ll_data);
        View childView = LayoutInflater.from(context).inflate(R.layout.item_dialog_sure_order_view, mLlData, false);
        mLlData.addView(childView);
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }
}

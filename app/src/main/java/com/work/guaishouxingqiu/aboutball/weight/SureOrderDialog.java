package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/28 10:55
 * 更新时间: 2019/8/28 10:55
 * 描述: 确认订单dialog
 */
public class SureOrderDialog extends BaseDialog {
    public SureOrderDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sure_order_view, null);
        setContentView(view);
        TextView mTvSures = findViewById(R.id.tv_sures);
        ImageView mIvClose = findViewById(R.id.iv_close);
        LinearLayout mLlData = findViewById(R.id.ll_data);
        View childView = LayoutInflater.from(context).inflate(R.layout.item_dialog_sure_order_view, mLlData, false);
        mLlData.addView(childView);
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }
}

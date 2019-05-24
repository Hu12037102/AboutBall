package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:45
 * 更新时间: 2019/5/24 10:45
 * 描述:退款详情进度适配器
 */
public class OrderRefundDetailsAdapter extends RecyclerView.Adapter<OrderRefundDetailsAdapter.ViewHolder> {
    public List<ResultRefundDetailsBean.RefundDetailList> mData;
    public Context mContext;

    public OrderRefundDetailsAdapter(@NonNull Context context, List<ResultRefundDetailsBean.RefundDetailList> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_refund_details_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultRefundDetailsBean.RefundDetailList bean = mData.get(i);
        viewHolder.mTvContent.setText(bean.description);
        viewHolder.mTvTime.setText(bean.time);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private TextView mTvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

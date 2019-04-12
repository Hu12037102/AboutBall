package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:36
 * 更新时间: 2019/4/11 17:36
 * 描述:获取我的奖品适配器
 */
public class BasePrizeAdapter extends BaseRecyclerAdapter<BasePrizeAdapter.ViewHolder, List<ResultPrizeBean.DataBean>> {

    private int mStatus;


    public BasePrizeAdapter(@NonNull List<ResultPrizeBean.DataBean> data, int status) {
        super(data);
        this.mStatus = status;
    }

    private void notifyData(int status){
        this.mStatus = status;
        notifyDataSetChanged();
    }
    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultPrizeBean.DataBean bean = mData.get(i);
        viewHolder.mTvTitle.setText(UIUtils.getString(R.string.prize_data, bean.prizeLevel, bean.prizeName));
        viewHolder.mTvValidityRight.setText(bean.prizeTime);
        viewHolder.mTvRemarkRight.setText(bean.source);
        viewHolder.mTvWayRight.setText(bean.getWay);
        viewHolder.mTvPrizeCodeRight.setText(bean.prizeCode);
        switch (mStatus){
            case Contast.PRIZE_WAIT:
                viewHolder.mTvStatus.setVisibility(View.GONE);
                break;
            case Contast.PRIZE_HAS_CHANGE:
                viewHolder.mTvStatus.setVisibility(View.VISIBLE);
                viewHolder.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.color_2));
                viewHolder.mTvStatus.setTextColor(R.string.have_change);
                break;
            case Contast.PRIZE_TIME_OUT:
                viewHolder.mTvStatus.setVisibility(View.VISIBLE);
                viewHolder.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.colorFFA6A6A6));
                viewHolder.mTvStatus.setTextColor(R.string.out_off_date);
                break;
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_prize, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvStatus;
        private TextView mTvTitle;
        private TextView mTvValidityRight;
        private TextView mTvRemarkRight;
        private TextView mTvWayRight,mTvPrizeCodeRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvValidityRight = itemView.findViewById(R.id.tv_validity_right);
            mTvRemarkRight = itemView.findViewById(R.id.tv_remark_right);
            mTvWayRight = itemView.findViewById(R.id.tv_way_right);
            mTvPrizeCodeRight = itemView.findViewById(R.id.tv_prize_code_right);
        }
    }
}

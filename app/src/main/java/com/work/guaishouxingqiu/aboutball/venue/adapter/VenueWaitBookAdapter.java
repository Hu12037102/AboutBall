package com.work.guaishouxingqiu.aboutball.venue.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueBookBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/19 12:55
 * 更新时间: 2019/4/19 12:55
 * 描述:待约球场的Adapter
 */
public class VenueWaitBookAdapter extends BaseRecyclerAdapter<VenueWaitBookAdapter.ViewHolder, List<ResultVenueBookBean>> {

    public VenueWaitBookAdapter(@NonNull List<ResultVenueBookBean> data) {
        super(data, false);
    }
    public int getSelectorPosition() {
        if (mData != null && mData.size() > 0) {
            for (ResultVenueBookBean bean : mData) {
                if (bean.isCheck) {
                    return mData.indexOf(bean);
                }
            }
        }
        return -1;
    }
    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVenueBookBean bean = mData.get(i);
        viewHolder.mCbStatus.setChecked(bean.isCheck);
        switch (bean.stateId) {
            case 0:
                viewHolder.mCbStatus.setVisibility(View.VISIBLE);
                viewHolder.itemView.setEnabled(true);
                String content = UIUtils.getString(R.string.yuan_s_cost_money, bean.price);
                String price = String.valueOf(bean.price);
                viewHolder.mTvMoney.setText(SpanUtils.getTextColor(R.color.color_2,0,content.length() -5,SpanUtils.getTextSize(23, 0, price.length(), content)));
                viewHolder.itemView.setBackgroundResource(R.drawable.selector_item);
                break;
            case 1:
            case 2:
                viewHolder.mCbStatus.setVisibility(View.INVISIBLE);
                viewHolder.itemView.setEnabled(false);
                viewHolder.mTvMoney.setText(R.string.be_occupied);
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorFFFCFCFA));
                viewHolder.mTvMoney.setTextColor(ContextCompat.getColor(mContext,R.color.colorFFA6A6A6));
                break;
            default:
                break;
        }
        viewHolder.mTvStart.setText(DateUtils.getHourMinutes(bean.startTime));
        viewHolder.mTvEnd.setText(UIUtils.getString(R.string.time_end,DateUtils.getHourMinutes(bean.endTime)));
        if (i == mData.size() - 1) {
            viewHolder.mLine.setVisibility(View.GONE);
        } else {
            viewHolder.mLine.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            bean.isCheck = !bean.isCheck;
            for (int j = 0; j < mData.size(); j++) {
                if (i != j) {
                    if (mData.get(j).isCheck) {
                        mData.get(j).isCheck = !mData.get(j).isCheck;
                        notifyItemChanged(j);
                    }
                } else {
                    notifyItemChanged(i);
                }
            }
            LogUtils.w("setOnClickListener--",bean.isCheck+"--");
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, i);
            }
        });
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_booking_view_2, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbStatus;
        private TextView mTvMoney;
        private TextView mTvStart;
        private TextView mTvEnd;
        private View mLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCbStatus = itemView.findViewById(R.id.cb_status);
            mCbStatus.setClickable(false);
            mTvMoney = itemView.findViewById(R.id.tv_money);
            mTvStart = itemView.findViewById(R.id.tv_start_time);
            mTvEnd = itemView.findViewById(R.id.tv_end_time);
            mLine = itemView.findViewById(R.id.line);
        }
    }
}

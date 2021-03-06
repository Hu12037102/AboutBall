package com.ifeell.app.aboutball.venue.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.DateUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 14:49
 * 更新时间: 2019/4/17 14:49
 * 描述: 场馆预定时间Adapter
 */
public class VenueDateAdapter extends RecyclerView.Adapter<VenueDateAdapter.ViewHolder> {

    private Context mContext;
    private List<ResultVenueDetailsBean.CalendarListForAreaList> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public VenueDateAdapter(@NonNull Context context, List<ResultVenueDetailsBean.CalendarListForAreaList> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_date_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVenueDetailsBean.CalendarListForAreaList bean = mData.get(i);

        if (Integer.valueOf(bean.remainingTimes) == 0) {
            viewHolder.mTvCount.setTextColor(ContextCompat.getColor(mContext,R.color.colorFFA6A6A6));
            viewHolder.itemView.setEnabled(false);
            viewHolder.mTvCount.setText("已约满");
        } else {
            viewHolder.mTvCount.setTextColor(ContextCompat.getColor(mContext,R.color.color_2));
            viewHolder.itemView.setEnabled(true);
            viewHolder.mTvCount.setText(UIUtils.getString(R.string.residue_count_s, bean.remainingTimes));
        }
        viewHolder.mTvDay.setText(DateUtils.getWeek(bean.date));
        viewHolder.mTvDate.setText(bean.date);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtils.dp2px(mContext, 85),
                ScreenUtils.dp2px(mContext, 95));
        layoutParams.leftMargin = 0;
        layoutParams.rightMargin = ScreenUtils.dp2px(mContext, 10);
        if (i == 0) {
            layoutParams.leftMargin = ScreenUtils.dp2px(mContext, 20);
        } else if (i == mData.size() - 1) {
            layoutParams.rightMargin = ScreenUtils.dp2px(mContext, 20);
        }
        viewHolder.itemView.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(v, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDay;
        private TextView mTvDate;
        private TextView mTvCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvDay = itemView.findViewById(R.id.tv_day);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTvCount = itemView.findViewById(R.id.tv_count);
        }
    }
}

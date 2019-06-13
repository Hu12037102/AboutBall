package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 9:41
 * 更新时间: 2019/6/13 9:41
 * 描述:训练TabAdapter
 */
public class DrillTabAdapter extends RecyclerView.Adapter<DrillTabAdapter.ViewHolder> {
    private Context mContext;
    private List<ResultHomeTabBean.LabBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public DrillTabAdapter(@NonNull Context context, List<ResultHomeTabBean.LabBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getSelectorTabId() {
        for (ResultHomeTabBean.LabBean bean : mData) {
            if (bean.isCheck) {
                return bean.labelId;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_ball_type, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultHomeTabBean.LabBean bean = mData.get(i);
        viewHolder.mTvType.setText(bean.labelName);
        if (bean.isCheck) {
            viewHolder.mTvType.setBackgroundResource(R.drawable.shape_item_ball_type);
            viewHolder.mTvType.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorTheme));
        } else {
            viewHolder.mTvType.setBackgroundResource(0);
            viewHolder.mTvType.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_4));
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.mTvType.getLayoutParams();
        if (i == mData.size() - 1) {
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
        } else {
            layoutParams.rightMargin = 0;
        }
        viewHolder.mTvType.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!bean.isCheck) {
                for (int h = 0; h < mData.size(); h++) {
                    mData.get(h).isCheck = i == h;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(viewHolder.itemView, i);
                }
                notifyDataSetChanged();
            }

        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView((itemView));
        }

        private void initView(View itemView) {
            mTvType = itemView.findViewById(R.id.tv_ball_type);
        }
    }
}

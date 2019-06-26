package com.work.guaishouxingqiu.aboutball.game.adapter;

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
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameGroupBean;
import com.work.guaishouxingqiu.aboutball.venue.adapter.BallTypeAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 16:58
 * 更新时间: 2019/6/25 16:58
 * 描述:赛事数据分组Adapter
 */
public class GameInfoGroupAdapter extends RecyclerView.Adapter<GameInfoGroupAdapter.ViewHolder> {
    private Context mContext;
    private List<ResultGameGroupBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public GameInfoGroupAdapter(Context context, List<ResultGameGroupBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GameInfoGroupAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_ball_type, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameGroupBean bean = mData.get(i);
        viewHolder.mTvGroup.setText(bean.groupName);
        if (bean.isCheck) {
            viewHolder.mTvGroup.setBackgroundResource(R.drawable.shape_venue_count_click_view);
            viewHolder.mTvGroup.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorTheme));
        } else {
            viewHolder.mTvGroup.setBackgroundResource(R.drawable.shape_venue_count_default_view);
            viewHolder.mTvGroup.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_4));
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.mTvGroup.getLayoutParams();
        if (i == mData.size() - 1) {
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
        } else {
            layoutParams.rightMargin = 0;
        }
        viewHolder.mTvGroup.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!bean.isCheck) {
                for (int h = 0; h < mData.size(); h++) {
                    mData.get(h).isCheck = i == h;
                }

                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(v, i);
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
        private TextView mTvGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            mTvGroup = itemView.findViewById(R.id.tv_ball_type);
        }
    }
}

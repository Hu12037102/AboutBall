package com.ifeell.app.aboutball.venue.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 11:46
 * 更新时间: 2019/3/18 11:46
 * 描述:
 */
public class BallTypeAdapter extends RecyclerView.Adapter<BallTypeAdapter.ViewHolder> {
    public List<ResultTypeBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;


    public BallTypeAdapter(@NonNull List<ResultTypeBean> data) {
        this.mData = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue_ball_type, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultTypeBean bean = mData.get(i);
        viewHolder.mTvBallType.setText(bean.typeName);
        if (bean.isCheck) {
            viewHolder.mTvBallType.setBackgroundResource(R.drawable.shape_item_ball_type);
            viewHolder.mTvBallType.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorTheme));
        } else {
            viewHolder.mTvBallType.setBackgroundResource(0);
            viewHolder.mTvBallType.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_4));
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.mTvBallType.getLayoutParams();
        if (i == mData.size() - 1) {
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
        } else {
            layoutParams.rightMargin = 0;
        }
        viewHolder.mTvBallType.setLayoutParams(layoutParams);
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

        private TextView mTvBallType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvBallType = itemView.findViewById(R.id.tv_ball_type);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(View view, int position);
    }
}

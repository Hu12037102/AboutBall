package com.ifeell.app.aboutball.venue.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.bean.ResultVenueEvaluateTypeBean;

import java.util.List;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.adapter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评价类型适配器
 *
 * @author ：
 */
public class VenueEvaluateTypeAdapter extends RecyclerView.Adapter<VenueEvaluateTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<ResultVenueEvaluateTypeBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public VenueEvaluateTypeAdapter(@NonNull Context context, @NonNull List<ResultVenueEvaluateTypeBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getCheckPosition() {
        if (mData != null && mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).isCheck) {
                    return i;
                }
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_evaluate_type_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVenueEvaluateTypeBean bean = mData.get(i);
        if (bean.count > 100) {
            UIUtils.setText(viewHolder.mTvType, bean.content + "(100+)");
        } else if (bean.count > 0) {
            UIUtils.setText(viewHolder.mTvType, bean.content + "(" + bean.count + ")");
        } else {
            UIUtils.setText(viewHolder.mTvType, bean.content);
        }
        if (bean.isCheck) {
            viewHolder.mTvType.setBackgroundResource(R.drawable.shape_item_ball_type);
            viewHolder.mTvType.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        } else {
            viewHolder.mTvType.setBackgroundResource(R.drawable.shape_venue_evaluate_type_default_view);
            viewHolder.mTvType.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
        }
        viewHolder.itemView.setOnClickListener(view -> {
            if (!bean.isCheck) {
                bean.isCheck = true;
                for (ResultVenueEvaluateTypeBean typeBean : mData) {
                    if (!typeBean.equals(bean)) {
                        typeBean.isCheck = false;
                    }
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(view, i);
                }
                VenueEvaluateTypeAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvType = itemView.findViewById(R.id.tv_ball_type);
        }
    }
}

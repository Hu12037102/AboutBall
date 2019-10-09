package com.ifeell.app.aboutball.my.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 15:34
 * 更新时间: 2019/5/23 15:34
 * 描述:退款原因adapter
 */
public class RefundCauseAdapter extends RecyclerView.Adapter<RefundCauseAdapter.ViewHolder> {
    private Context mContext;
    private List<ResultRefundCauseBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public RefundCauseAdapter(@NonNull Context context, @NonNull List<ResultRefundCauseBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_refund_cause_view, viewGroup, false));
    }

    public boolean isCheckItem() {
        if (mData == null || mData.size() == 0) {
            return false;
        }
        for (ResultRefundCauseBean bean : mData) {
            if (bean.isCheck) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultRefundCauseBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvContent, bean.reasonContent);
        viewHolder.mTvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, bean.isCheck ? R.mipmap.icon_circle_check : R.mipmap.icon_circle_default, 0);
        viewHolder.itemView.setBackgroundResource(R.drawable.selector_item);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!bean.isCheck) {
                for (int j = 0; j < mData.size(); j++) {
                    if (i == j) {
                        bean.isCheck = !bean.isCheck;
                    } else {
                        mData.get(j).isCheck = false;
                    }
                }
                notifyDataSetChanged();
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

        private final TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

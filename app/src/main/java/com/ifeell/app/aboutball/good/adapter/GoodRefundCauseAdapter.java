package com.ifeell.app.aboutball.good.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/20 14:12
 * 更新时间: 2019/9/20 14:12
 * 描述: 退款申请Adapter
 */
public class GoodRefundCauseAdapter extends RecyclerView.Adapter<GoodRefundCauseAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mData;
    private List<Boolean> mCheckData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;


    public GoodRefundCauseAdapter(@NonNull Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        initCheckList();
    }

    private void initCheckList() {
        mCheckData = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            mCheckData.add(i, false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_refund_cause_view, parent, false));
    }

    public void checkPosition(int position) {
        for (int i = 0; i < mCheckData.size(); i++) {
            if (position == i) {
                mCheckData.set(i, true);
            } else {
                mCheckData.set(i, false);
            }
        }
    }

    public int getCheckPosition() {
        for (int i = 0; i < mCheckData.size(); i++) {
            if (mCheckData.get(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UIUtils.setText(holder.mTvContent, mData.get(position));
        boolean isChecked = mCheckData.get(position);
        holder.mTvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                isChecked ? R.mipmap.icon_circle_check : R.mipmap.icon_circle_default,
                0);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    checkPosition(position);
                    notifyDataSetChanged();
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(v, position);
                    }
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

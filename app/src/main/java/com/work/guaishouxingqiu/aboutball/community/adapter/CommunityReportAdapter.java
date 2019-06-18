package com.work.guaishouxingqiu.aboutball.community.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/18 15:32
 * 更新时间: 2019/6/18 15:32
 * 描述:举报动态Adapter
 */
public class CommunityReportAdapter extends BaseRecyclerAdapter<CommunityReportAdapter.ViewHolder, List<String>> {

    private SparseBooleanArray mCheckMap;

    public CommunityReportAdapter(@NonNull List<String> data) {
        super(data, false);
        initCheckMap();
    }

    private void initCheckMap() {
        mCheckMap = new SparseBooleanArray();
        for (int i = 0; i < mData.size(); i++) {
            mCheckMap.put(i, false);
        }
    }

    public boolean isSelector() {
        for (int i = 0; i < mCheckMap.size(); i++) {
            if (mCheckMap.get(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        UIUtils.setText(viewHolder.mTvContent, mData.get(i));
        viewHolder.mTvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                mCheckMap.get(i) ? R.mipmap.icon_circle_check : R.mipmap.icon_circle_default, 0);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mCheckMap.get(i);
                if (!isChecked) {
                    for (int j = 0; j < mCheckMap.size(); j++) {
                        if (j == i) {
                            mCheckMap.put(i, true);
                        } else {
                            mCheckMap.put(j, false);
                        }
                    }
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, i);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_community_report_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

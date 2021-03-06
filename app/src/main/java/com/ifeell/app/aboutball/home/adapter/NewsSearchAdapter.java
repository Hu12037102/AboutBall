package com.ifeell.app.aboutball.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.SpanUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 15:52
 * 更新时间: 2019/4/9 15:52
 * 描述: 资讯搜索Adapter
 */
public class NewsSearchAdapter extends RecyclerView.Adapter<NewsSearchAdapter.ViewHolder> {
    private String mSearchText;
    private List<ResultNewsBean> mData;

    private boolean mHasMore = true;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public NewsSearchAdapter(List<ResultNewsBean> data, String searchText) {
        this.mSearchText = searchText;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ResultNewsBean bean = mData.get(position);
        if (!DataUtils.isEmpty(mSearchText) && bean.title.contains(mSearchText)) {
            viewHolder.mTvContent.setText(SpanUtils.getTextColor(R.color.color_2, bean.title.indexOf(mSearchText), bean.title.indexOf(mSearchText) + mSearchText.length(), bean.title));
        } else {
            viewHolder.mTvContent.setText(bean.title);
        }
        viewHolder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickItem(view, position);
            }
        });
        if (!mHasMore && mData.size() - 1 == position) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void notifyData(String searchText, boolean hasMore) {
        this.mSearchText = searchText;
        this.mHasMore = hasMore;
        this.notifyDataSetChanged();
    }
/*
    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultNewsBean bean = mData.get(i);
        if (!DataUtils.isEmpty(mSearchText) && bean.title.contains(mSearchText)) {
            viewHolder.mTvContent.setText(SpanUtils.getTextColor(R.color.color_2, bean.title.indexOf(mSearchText), bean.title.indexOf(mSearchText) + mSearchText.length(), bean.title));
        } else {
            viewHolder.mTvContent.setText(bean.title);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_view, viewGroup, false));
    }*/

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private View mIncludeFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
        }
    }
}

package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultVersionHistoryBean;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/4 11:11
 * 更新时间: 2019/7/4 11:11
 * 描述:版本说明adapter
 */
public class VersionHistoryAdapter extends BaseRecyclerAdapter<VersionHistoryAdapter.ViewHolder, List<ResultVersionHistoryBean>> {
    private boolean mIsHasMore = true;

    public VersionHistoryAdapter(@NonNull List<ResultVersionHistoryBean> data) {
        super(data);
    }

    public void notifyData(boolean isHasMore) {
        this.mIsHasMore = isHasMore;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVersionHistoryBean bean = mData.get(i);
        viewHolder.mTvContent.setText(HtmlCompat.fromHtml(bean.updateContent, HtmlCompat.FROM_HTML_MODE_COMPACT));
        UIUtils.setText(viewHolder.mTvTime, bean.updateTime);
        UIUtils.setText(viewHolder.mTvVersion, bean.version);
        if (i == mData.size() - 1 && !mIsHasMore) {
            viewHolder.mViewFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mViewFoot.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_version_history_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvVersion;
        private TextView mTvTime;
        private TextView mTvContent;
        private View mViewFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvVersion = itemView.findViewById(R.id.tv_version);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mViewFoot = itemView.findViewById(R.id.include_foot);
        }
    }
}

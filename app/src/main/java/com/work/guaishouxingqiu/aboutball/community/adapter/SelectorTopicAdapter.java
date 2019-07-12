package com.work.guaishouxingqiu.aboutball.community.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultTopicBean;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:44
 * 更新时间: 2019/6/14 18:44
 * 描述:选择话题适配器
 */
public class SelectorTopicAdapter extends BaseRecyclerAdapter<SelectorTopicAdapter.ViewHolder, List<ResultTopicBean>> {
    public SelectorTopicAdapter(@NonNull List<ResultTopicBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultTopicBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvContent, bean.topicTitle);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_topic_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

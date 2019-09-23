package com.work.guaishouxingqiu.aboutball.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 10:42
 * 更新时间: 2019/3/22 10:42
 * 描述:
 */
public class NewsMessageAdapter extends BaseRecyclerAdapter<NewsMessageAdapter.ViewHolder, List<ResultNewsMessageBean>> {

    public NewsMessageAdapter(@NonNull List<ResultNewsMessageBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultNewsMessageBean bean = mData.get(i);
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), bean.imageUrl, viewHolder.mCivHead);
        viewHolder.mTvName.setText(bean.nickName);
        viewHolder.mTvTime.setText(bean.commentTime);
        //viewHolder.mTvContent.setText(bean.commentContent);
        UIUtils.setEmojiText( viewHolder.mTvContent,bean.commentContent.trim());

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_message_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvTime;
        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvContent = itemView.findViewById(R.id.tv_content);

        }
    }
}

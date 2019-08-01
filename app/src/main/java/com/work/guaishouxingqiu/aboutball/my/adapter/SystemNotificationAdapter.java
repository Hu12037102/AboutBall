package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultSystemNotificationBean;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/1 11:36
 * 更新时间: 2019/8/1 11:36
 * 描述:系统通知Adapter
 */
public class SystemNotificationAdapter extends BaseRecyclerAdapter<SystemNotificationAdapter.ViewHolder, List<ResultSystemNotificationBean>> {
    private boolean isShowFoot;

    public SystemNotificationAdapter(@NonNull List<ResultSystemNotificationBean> data) {
        super(data);
    }

    public void setNotifyData(boolean isShowFoot) {
        this.isShowFoot = isShowFoot;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultSystemNotificationBean bean = mData.get(i);
        String content = "  " + bean.noticeTitle;
        UIUtils.setText(viewHolder.mTvContent, SpanUtils.getTextDrawable(R.mipmap.icon_systematic_message, 0, 1, content) );
        UIUtils.setText(viewHolder.mTvTime, bean.createTime);
        if (isShowFoot && mData.size() - 1 == i) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_systematic_notification_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private TextView mTvTime;
        private View mIncludeFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
        }
    }
}

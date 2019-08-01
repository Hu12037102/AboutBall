package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultDynamicNotificationBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/1 11:49
 * 更新时间: 2019/8/1 11:49
 * 描述: 动态通知adapter
 */
public class DynamicNotificationAdapter extends BaseRecyclerAdapter<DynamicNotificationAdapter.ViewHolder, List<ResultDynamicNotificationBean>> {
    private boolean isShowFoot;

    public DynamicNotificationAdapter(@NonNull List<ResultDynamicNotificationBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultDynamicNotificationBean bean = mData.get(i);
        if (!DataUtils.isEmpty(bean.noticeTitleLink) && (bean.noticeTitle.contains(bean.noticeTitleLink))) {
            int index = bean.noticeTitle.indexOf(bean.noticeTitleLink);
            UIUtils.setText(viewHolder.mTvDescribe, SpanUtils.getTextColor(R.color.color_2, index, index + DataUtils.getTextTrimLength(bean.noticeTitleLink), bean.noticeTitle));
        } else {
            UIUtils.setText(viewHolder.mTvDescribe, bean.noticeTitle);
        }
        UIUtils.setText(viewHolder.mTvTime, bean.createTime);
        GlideManger.get().loadHeadImage(mContext, bean.imageUrl, viewHolder.mCivHead);
        UIUtils.setText(viewHolder.mTvMyName, "@" + bean.nickName);
        UIUtils.setText(viewHolder.mTvMyContent, bean.tweetContent);
        if (isShowFoot && mData.size() - 1 == i) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_dynamic_notification_view, viewGroup, false));
    }

    public void setNotifyData(boolean isShowFoot) {
        this.isShowFoot = isShowFoot;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDescribe;
        private TextView mTvTime;
        private ConstraintLayout mClContent;
        private CircleImageView mCivHead;
        private TextView mTvMyName;
        private TextView mTvMyContent;
        private View mIncludeFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvDescribe = itemView.findViewById(R.id.tv_describe);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mClContent = itemView.findViewById(R.id.cl_content);
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvMyName = itemView.findViewById(R.id.tv_my_name);
            mTvMyContent = itemView.findViewById(R.id.tv_my_content);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
        }
    }
}

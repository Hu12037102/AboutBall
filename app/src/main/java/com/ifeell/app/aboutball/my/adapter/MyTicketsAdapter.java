package com.ifeell.app.aboutball.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultMyTicketsBean;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 18:07
 * 更新时间: 2019/9/11 18:07
 * 描述:我的门票适配器
 */
public class MyTicketsAdapter extends BaseRecyclerAdapter<MyTicketsAdapter.ViewHolder, List<ResultMyTicketsBean>> {
    private boolean mHasMore = true;

    public void setOnItemClickListener(com.ifeell.app.aboutball.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private com.ifeell.app.aboutball.OnItemClickListener onItemClickListener;
    public MyTicketsAdapter(@NonNull List<ResultMyTicketsBean> data) {
        super(data);
    }

    public void notifyData(boolean hasMore) {
        this.mHasMore = hasMore;
        notifyDataSetChanged();
    }


    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyTicketsBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTitle, bean.ticketName);
        UIUtils.setText(viewHolder.mTvTime, bean.segment1);
        UIUtils.setText(viewHolder.mTvHint, bean.segment2);
        if (DataUtils.isEmpty(bean.code)) {
            viewHolder.mClRoot.setEnabled(false);
        } else {
            viewHolder.mClRoot.setEnabled(true);
        }
        UIUtils.setText(viewHolder.mTvCount, bean.num + "张");
        if (!mHasMore && i == mData.size() - 1) {
            viewHolder.includeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.includeFoot.setVisibility(View.GONE);
        }
        //1.待使用，2.已使用 3.已失效
        switch (bean.status) {
            case 1:
                viewHolder.mCvRoot.setBackgroundResource(R.mipmap.icon_tickets_usering);
                viewHolder.mClRoot.setEnabled(true);
                break;
            case 2:
                viewHolder.mCvRoot.setBackgroundResource(R.mipmap.icon_tickets_user);
                viewHolder.mClRoot.setEnabled(true);
                break;
            case 3:
                viewHolder.mCvRoot.setBackgroundResource(R.mipmap.icon_tickets_usered);
                viewHolder.mClRoot.setEnabled(false);
                break;
            default:
                break;
        }
        viewHolder.mClRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(v, i);
            }
        });

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_tickets_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvHint;
        private TextView mTvCount;
        private View includeFoot;
        private ConstraintLayout mClRoot;
        private CardView mCvRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvHint = itemView.findViewById(R.id.tv_hint);
            mTvCount = itemView.findViewById(R.id.tv_count);
            includeFoot = itemView.findViewById(R.id.include_foot);
            mClRoot = itemView.findViewById(R.id.cl_root);
            mCvRoot = itemView.findViewById(R.id.cv_root);

        }
    }

}

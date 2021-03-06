package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/10 10:35
 * 更新时间: 2019/6/10 10:35
 * 描述:赛况记录Adapter
 */
public class MatchRefereeResultAdapter extends BaseRecyclerAdapter<MatchRefereeResultAdapter.ViewHolder, List<ResultMatchRefereeResultBean>> {

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    private OnEditClickListener onEditClickListener;

    public MatchRefereeResultAdapter(@NonNull List<ResultMatchRefereeResultBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMatchRefereeResultBean bean = mData.get(i);
        if (DataUtils.isEmpty(bean.teamAndAction)) {
            viewHolder.itemView.setVisibility(View.GONE);
        } else {
            viewHolder.itemView.setVisibility(View.VISIBLE);
        }
        UIUtils.setText(viewHolder.mTvStatus, bean.teamAndAction);
        if (viewHolder.mLlContent.getChildCount() > 0) {
            viewHolder.mLlContent.removeAllViews();
        }
        /*View textInflateView = LayoutInflater.from(mContext).inflate(R.layout.item_text_view, viewHolder.mLlContent, false);
        TextView tvStatus =textInflateView.findViewById(R.id.tv_status);
        tvStatus.setText( bean.teamAndAction);
        viewHolder.mLlContent.addView(textInflateView);*/
        if (bean.agreeOutsForSimpleList != null && bean.agreeOutsForSimpleList.size() > 0) {

            for (ResultMatchRefereeResultBean.ChildBean childBean : bean.agreeOutsForSimpleList) {
                View contentInflateView = LayoutInflater.from(mContext).inflate(R.layout.item_match_referee_body_result_view, viewHolder.mLlContent, false);
                CircleImageView civHead = contentInflateView.findViewById(R.id.civ_head);
                TextView tvModification = contentInflateView.findViewById(R.id.tv_modification);
                TextView tvTime = contentInflateView.findViewById(R.id.tv_time);
                TextView tvName = contentInflateView.findViewById(R.id.tv_name);
                viewHolder.mLlContent.addView(contentInflateView);
                GlideManger.get().loadHeadImage(mContext, childBean.headerImg, civHead);
                UIUtils.setText(tvName, childBean.playerName);
                UIUtils.setText(tvTime, childBean.duration + "'");
                tvModification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEditClickListener != null) {
                            onEditClickListener.clickEdit(tvModification, childBean);
                        }
                    }
                });
            }
        }

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_match_referee_result_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvStatus;
        /* private CircleImageView mCivHead;
         private TextView mTvModification;
         private TextView mTvTime;
         private TextView mTvName;
         private View mContentInflateView;*/
        private LinearLayout mLlContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mLlContent = itemView.findViewById(R.id.item_ll);

           /* ViewGroup.LayoutParams layoutParams = mLlContent.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            mLlContent.setLayoutParams(layoutParams);*/
        }
    }

    public interface OnEditClickListener {
        void clickEdit(View view, ResultMatchRefereeResultBean.ChildBean bean);
    }
}

package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultAttentionFanBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/26 13:47
 * 更新时间: 2019/7/26 13:47
 * 描述:我的关注粉丝Adapter
 */
public class AttentionFansAdapter extends BaseRecyclerAdapter<AttentionFansAdapter.ViewHolder, List<ResultAttentionFanBean>> {
    private boolean mHasMoreData = true;

    public void setOnAttentionClickListener(AttentionFansAdapter.onAttentionClickListener onAttentionClickListener) {
        this.onAttentionClickListener = onAttentionClickListener;
    }

    private onAttentionClickListener onAttentionClickListener;

    public AttentionFansAdapter(@NonNull List<ResultAttentionFanBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultAttentionFanBean bean = mData.get(i);
        GlideManger.get().loadHeadImage(mContext, bean.headerImg, viewHolder.mCivHead);
        if (bean.isFollow == 0) {
            viewHolder.mIvAttentionStatus.setImageResource(R.mipmap.icon_blue_add_following);
        } else if (bean.isFollow == 1) {
            viewHolder.mIvAttentionStatus.setImageResource(R.mipmap.icon_followed);
        }
        UIUtils.setText(viewHolder.mTvName, bean.nickName);
        UIUtils.setText(viewHolder.mTvRefereeGrade, bean.refereeLevel);
        viewHolder.mIvAttentionStatus.setOnClickListener(v -> {
            if (onAttentionClickListener != null) {
                onAttentionClickListener.onClickAttention(v, i);
            }
        });
        viewHolder.mCivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAttentionClickListener!=null){
                    onAttentionClickListener.onClickHead(v,i);
                }
            }
        });
        if (!mHasMoreData && mData.size() - 1 == i) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
    }

    public void setNotifyData(boolean hasMoreData) {
        mHasMoreData = hasMoreData;
        this.notifyDataSetChanged();
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_attention_fans_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private ImageView mIvAttentionStatus;
        private TextView mTvName;
        private TextView mTvRefereeGrade;
        private View mIncludeFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mIvAttentionStatus = itemView.findViewById(R.id.iv_attention_status);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvRefereeGrade = itemView.findViewById(R.id.tv_referee_grade);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);

        }
    }

    public interface onAttentionClickListener {
        void onClickAttention(View view, int position);
        void onClickHead(View view,int position);
    }
}

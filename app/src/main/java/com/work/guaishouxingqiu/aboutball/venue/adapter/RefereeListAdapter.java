package com.work.guaishouxingqiu.aboutball.venue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 16:39
 * 更新时间: 2019/4/23 16:39
 * 描述:裁判列表adapter
 */
public class RefereeListAdapter extends BaseRecyclerAdapter<RefereeListAdapter.ViewHolder, List<ResultRefereeBean>> {
    public RefereeListAdapter(@NonNull List<ResultRefereeBean> data) {
        super(data);
    }

    public static final int MAX_INVITE_COUNT = 3;

    public void setOnCheckContentListener(OnCheckContentListener onCheckContentListener) {
        this.onCheckContentListener = onCheckContentListener;
    }

    public OnCheckContentListener onCheckContentListener;

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultRefereeBean bean = mData.get(i);
        GlideManger.get().loadHeadImage(mContext, bean.photo, viewHolder.mCivHead);
        viewHolder.mTvName.setText(bean.name);
        viewHolder.mTvCount.setText(bean.matchCount + "次");
        viewHolder.mTvLevel.setText(bean.level);
        if (!bean.isInvite) {
            viewHolder.mTvInvite.setEnabled(true);
            viewHolder.mTvInvite.setBackgroundResource(R.drawable.shape_click_button);
            viewHolder.mTvInvite.setText(R.string.invite);
        } else {
            viewHolder.mTvInvite.setEnabled(false);
            viewHolder.mTvInvite.setBackgroundResource(R.drawable.shape_default_button);
            viewHolder.mTvInvite.setText(R.string.inviting);
        }
        viewHolder.mTvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getInviteCount() < RefereeListAdapter.MAX_INVITE_COUNT) {
                    bean.isInvite = !bean.isInvite;
                    notifyItemChanged(i);
                    if (onCheckContentListener != null) {
                        onCheckContentListener.checkContent(v, i);
                    }
                } else {
                    UIUtils.showToast(R.string.invite_up_to_three_judges_at_time);
                }

            }
        });
    }

    public boolean isInviteReferee() {
        for (ResultRefereeBean bean : mData) {
            if (bean.isInvite) {
                return true;
            }
        }

        return false;
    }

    public Long[] getSelectorInviteReferee() {
        List<Long> idData = new ArrayList<>();
        for (ResultRefereeBean bean : mData) {
            if (bean.isInvite) {
                idData.add(bean.refereeId);
            }
        }
        Long[] idArray = new Long[idData.size()];
        return idData.toArray(idArray);
    }

    public int getInviteCount() {
        int count = 0;
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isInvite) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_referee_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvCount;
        private TextView mTvLevel;
        private TextView mTvInvite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvCount = itemView.findViewById(R.id.tv_count);
            mTvLevel = itemView.findViewById(R.id.tv_level);
            mTvInvite = itemView.findViewById(R.id.tv_invite);
        }
    }

    public interface OnCheckContentListener {
        void checkContent(View view, int position);
    }
}

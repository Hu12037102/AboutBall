package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.OnItemLongClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 11:09
 * 更新时间: 2019/4/26 11:09
 * 描述:球队成员adapter
 */
public class BallTeamMemberAdapter extends BaseRecyclerAdapter<BallTeamMemberAdapter.ViewHolder, List<ResultTeamDetailsMemberBean>> {
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemLongClickListener onItemLongClickListener;

    public BallTeamMemberAdapter(@NonNull List<ResultTeamDetailsMemberBean> data) {
        super(data);
    }



    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultTeamDetailsMemberBean bean = mData.get(i);
        GlideManger.get().loadHeadImage(mContext, bean.imageUrl, viewHolder.mCivHead);
        switch (bean.isLeader) {
            case Contast.LEADER:
                viewHolder.mTvLeader.setBackgroundResource(R.drawable.shape_orange_view);
                viewHolder.mTvLeader.setText(R.string.leader);
                break;
            case Contast.MEMBER:
                viewHolder.mTvLeader.setBackgroundResource(R.drawable.shape_stick_view);
                viewHolder.mTvLeader.setText(R.string.team_member);
                break;
            default:
                break;
        }
        viewHolder.mTvName.setText(bean.nickName);
        viewHolder.mTvPosition.setText(DataUtils.isEmpty(bean.position) ? UIUtils.getString(R.string.not_setting) : bean.position);
        viewHolder.mTvNumber.setText(bean.number == -1 ? UIUtils.getString(R.string.not_setting) : String.valueOf(bean.number));
        viewHolder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onLongClick(v, i);
            }
            return true;
        });
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_team_member_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvLeader;
        private TextView mTvName;
        private TextView mTvPosition;
        private TextView mTvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvLeader = itemView.findViewById(R.id.tv_leader);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvPosition = itemView.findViewById(R.id.tv_position);
            mTvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

}

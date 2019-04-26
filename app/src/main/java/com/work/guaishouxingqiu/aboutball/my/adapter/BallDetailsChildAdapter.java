package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 17:18
 * 更新时间: 2019/4/25 17:18
 * 描述:球队信息adapter
 */
public class BallDetailsChildAdapter extends BaseRecyclerAdapter<BallDetailsChildAdapter.ViewHolder,
        List<ResultBallDetailsBean.MatchBean>> {
    public BallDetailsChildAdapter(@NonNull List<ResultBallDetailsBean.MatchBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultBallDetailsBean.MatchBean bean = mData.get(i);
        viewHolder.mTvTime.setText(bean.startTime + " " + bean.endTime);
        GlideManger.get().loadLogoImage(mContext, bean.hostTeamLogo, viewHolder.mCivLeftLogo);
        viewHolder.mTvLeftName.setText(bean.hostTeamName);
        viewHolder.mTvGradle.setText(bean.hostScore + "  -  " + bean.guestScore);
        GlideManger.get().loadLogoImage(mContext,bean.guestTeamLogo,viewHolder.mCivRightLogo);
        viewHolder.mTvRightName.setText(bean.guestTeamName);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ball_details_child_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private CircleImageView mCivLeftLogo;
        private TextView mTvLeftName;
        private TextView mTvGradle;
        private CircleImageView mCivRightLogo;
        private TextView mTvRightName;
        private TextView mTvJudgeReferee;
        private TextView mTvJudgeOpponent;
        private TextView mTvJudgeTeam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mCivLeftLogo = itemView.findViewById(R.id.civ_left_logo);
            mTvLeftName = itemView.findViewById(R.id.tv_left_name);
            mTvGradle = itemView.findViewById(R.id.tv_gradle);
            mCivRightLogo = itemView.findViewById(R.id.civ_right_logo);
            mTvRightName = itemView.findViewById(R.id.tv_right_name);
            mTvJudgeReferee = itemView.findViewById(R.id.tv_judge_referee);
            mTvJudgeOpponent = itemView.findViewById(R.id.tv_judge_opponent);
            mTvJudgeTeam = itemView.findViewById(R.id.tv_judge_team);
        }
    }
}

package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameScheduleBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 17:05
 * 更新时间: 2019/6/27 17:05
 * 描述:比赛赛程Adapter
 */
public class GameScheduleAdapter extends BaseRecyclerAdapter<GameScheduleAdapter.ViewHolder, List<ResultGameScheduleBean>> {
    public void setOnClickGameViewListener(OnClickGameViewListener onClickGameViewListener) {
        this.onClickGameViewListener = onClickGameViewListener;
    }

    private boolean isShowFootView;

    private OnClickGameViewListener onClickGameViewListener;

    public GameScheduleAdapter(@NonNull List<ResultGameScheduleBean> data) {
        super(data);
    }

    public void notifyData(boolean isShowFootView) {
        this.isShowFootView = isShowFootView;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameScheduleBean bean = mData.get(i);
       /* if (i == 0) {
            viewHolder.mTvTime.setVisibility(View.VISIBLE);
            UIUtils.setText(viewHolder.mTvTime, bean.date);
        } else {
            viewHolder.mTvTime.setVisibility(View.GONE);
        }*/
        UIUtils.setText(viewHolder.mTvTime, DateUtils.getDate(bean.date));
        List<ResultGameScheduleBean.MatchList> matchData = bean.matchList;
        if (matchData != null && matchData.size() > 0) {
            viewHolder.mLlTeam.removeAllViews();
            for (int j = 0; j < matchData.size(); j++) {
                ResultGameScheduleBean.MatchList matchBean = matchData.get(j);
                View inflateTeamView = LayoutInflater.from(mContext).inflate(R.layout.item_game_schedule_team_view, viewHolder.mLlTeam, false);
                viewHolder.mLlTeam.addView(inflateTeamView);
                ImageView ivLogo = inflateTeamView.findViewById(R.id.iv_logo);
                ivLogo.setImageResource(R.mipmap.icon_venue_ball_rule);
                TextView tvGameName = inflateTeamView.findViewById(R.id.tv_game_name);
                UIUtils.setText(tvGameName, matchBean.gameName);
                LinearLayout llContent = inflateTeamView.findViewById(R.id.ll_content);
                ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llContent.setLayoutParams(layoutParams);
                List<ResultGameScheduleBean.MatchScheduleForMatchList> scheduleMatchData = matchBean.matchScheduleForMatchList;
                if (scheduleMatchData != null && scheduleMatchData.size() > 0) {
                    llContent.removeAllViews();
                    for (int h = 0; h < scheduleMatchData.size(); h++) {
                        ResultGameScheduleBean.MatchScheduleForMatchList matchScheduleBean = scheduleMatchData.get(h);
                        View inflateContentView = LayoutInflater.from(mContext).inflate(R.layout.item_game_schedule_content_view, viewHolder.mLlTeam, false);
                        llContent.addView(inflateContentView);
                        TextView tvGameTime = inflateContentView.findViewById(R.id.tv_game_time);
                        UIUtils.setText(tvGameTime, matchScheduleBean.startTime);
                        CircleImageView civHostLogo = inflateContentView.findViewById(R.id.civ_host_logo);
                        GlideManger.get().loadLogoImage(mContext, matchScheduleBean.hostTeamLogo, civHostLogo);
                        TextView tvHostName = inflateContentView.findViewById(R.id.tv_host_name);
                        UIUtils.setText(tvHostName, matchScheduleBean.hostTeamName);
                        CircleImageView civGustLogo = inflateContentView.findViewById(R.id.civ_guest_logo);
                        GlideManger.get().loadLogoImage(mContext, matchScheduleBean.guestTeamLogo, civGustLogo);
                        TextView tvGuestName = inflateContentView.findViewById(R.id.tv_guest_name);
                        UIUtils.setText(tvGuestName, matchScheduleBean.guestTeamName);
                        TextView tvHostScore = inflateContentView.findViewById(R.id.host_score);
                        UIUtils.setText(tvHostScore, matchScheduleBean.stateId == 1 ? "-" : matchScheduleBean.hostScore + "");
                        TextView tvGuestScore = inflateContentView.findViewById(R.id.guest_score);
                        UIUtils.setText(tvGuestScore, matchScheduleBean.stateId == 1 ? "-" : matchScheduleBean.guestScore + "");
                        TextView tvStatus = inflateContentView.findViewById(R.id.tv_status);
                        UIUtils.setText(tvStatus, matchScheduleBean.state);
                        UIUtils.setGameIconStatus(matchScheduleBean.stateId, tvStatus);
                        inflateContentView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               /* if (onClickGameViewListener!= null){
                                    onClickGameViewListener.onClickGameView(v, (int) matchScheduleBean.matchId);
                                }*/
                                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_DETAILS, ARouterConfig.Key.GAME_ID, (int) matchScheduleBean.matchId);
                            }
                        });
                    }
                }
            }
        }
        if (i == mData.size() - 1 && isShowFootView) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_schedule_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;

        private LinearLayout mLlTeam;
        private View mIncludeFoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iniView(itemView);
        }

        private void iniView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mLlTeam = itemView.findViewById(R.id.ll_team);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
            TextView tvContent = itemView.findViewById(R.id.tv_content);
            tvContent.setText("到底了,没有更多赛程");

        }
    }

    public interface OnClickGameViewListener {
        void onClickGameView(View view, int gameId);
    }
}

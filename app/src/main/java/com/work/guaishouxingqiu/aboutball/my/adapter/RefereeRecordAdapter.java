package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 13:09
 * 更新时间: 2019/5/9 13:09
 * 描述:比赛记录adapter
 */
public class RefereeRecordAdapter extends BaseRecyclerAdapter<RefereeRecordAdapter.ViewHolder, List<ResultRefereeRecordBean>> {


    public RefereeRecordAdapter(@NonNull List<ResultRefereeRecordBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultRefereeRecordBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTitle, bean.stadiumName);
        UIUtils.setText(viewHolder.mTvTime, bean.endTime);
        UIUtils.setText(viewHolder.mTvTeamName, bean.initiatingTeam);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_referee_record_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvMatchResult;
        private TextView mTvTime;
        private TextView mTvTeamName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvMatchResult = itemView.findViewById(R.id.tv_match_result);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvTeamName = itemView.findViewById(R.id.tv_team_name);
        }
    }
}

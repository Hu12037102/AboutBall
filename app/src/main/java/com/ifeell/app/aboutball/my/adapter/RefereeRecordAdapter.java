package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultRefereeRecordBean;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DateUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 13:09
 * 更新时间: 2019/5/9 13:09
 * 描述:比赛记录adapter
 */
public class RefereeRecordAdapter extends BaseRecyclerAdapter<RefereeRecordAdapter.ViewHolder, List<ResultRefereeRecordBean>> {

    private boolean mIsMySelf;

    public RefereeRecordAdapter(@NonNull List<ResultRefereeRecordBean> data, boolean isMySelf) {
        super(data);
        this.mIsMySelf = isMySelf;
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mIsMySelf) {
            viewHolder.mTvMatchResult.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTvMatchResult.setVisibility(View.INVISIBLE);
        }
        ResultRefereeRecordBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTitle, bean.stadiumName);
        UIUtils.setText(viewHolder.mTvTime, DateUtils.getStartTime2EndTime(bean.startTime, bean.endTime));
        UIUtils.setText(viewHolder.mTvTeamName, bean.hostTeamName);
        viewHolder.mTvMatchResult.setOnClickListener(v -> {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MATCH_REFEREE_RESULT, ARouterConfig.Key.PARCELABLE, bean);
        });
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

package com.ifeell.app.aboutball.venue.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.DateUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.bean.ResultMyAboutBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 9:23
 * 更新时间: 2019/5/23 9:23
 * 描述:我的约球适配器
 */
public class MyAboutBallAdapter extends BaseRecyclerAdapter<MyAboutBallAdapter.ViewHolder, List<ResultMyAboutBean>> {
    public MyAboutBallAdapter(@NonNull List<ResultMyAboutBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyAboutBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTitle, bean.stadiumName);
        UIUtils.setText(viewHolder.mTvTime, DateUtils.getStartTime2EndTime(bean.startTime, bean.endTime));
        UIUtils.setText(viewHolder.mTvTeamName, bean.hostTeamName);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyAboutBallAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_referee_record_view, viewGroup, false));
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
            mTvMatchResult.setVisibility(View.INVISIBLE);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvTeamName = itemView.findViewById(R.id.tv_team_name);
        }
    }
}

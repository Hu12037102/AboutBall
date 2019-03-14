package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/14 13:44
 * 更新时间: 2019/3/14 13:44
 * 描述:推荐首页head赛事直播Adapter
 */
public class RecommendHeadGameAdapter extends RecyclerView.Adapter<RecommendHeadGameAdapter.ViewHolder> {
    private List<ResultRecommendDataBean.Match> mData;

    public RecommendHeadGameAdapter(List<ResultRecommendDataBean.Match> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recommend_live_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultRecommendDataBean.Match match = mData.get(i);
        viewHolder.mTvTitle.setText(match.matchName);
        viewHolder.mTvLiveStatus.setText(match.matchState);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtils.dp2px(viewHolder.itemView.getContext(), 200),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if (i == 0) {
            layoutParams.leftMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);

        } else {
            layoutParams.leftMargin = 0;
        }
        if (i == mData.size() -1){
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
        }else {
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 10);
        }
        viewHolder.mClRoot.setLayoutParams(layoutParams);
        switch ((int) match.stateId) {
            case Contast.GAME_STATUS_STARTING:
                viewHolder.mTvLiveStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_live_status, 0);
                break;
            case Contast.GAME_STATUS_FINISH:
                viewHolder.mTvLiveStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_no_start, 0);
                break;
        }
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), match.hostLogoUrl, viewHolder.mIvTopIcon);
        viewHolder.mTvTopName.setText(match.hostName);
        viewHolder.mTvTopScore.setText(String.valueOf(match.hostScore));
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), match.guestLogoUrl, viewHolder.mIvBottomIcon);
        viewHolder.mTvBottomName.setText(match.gameName);
        viewHolder.mTvBottomScore.setText(String.valueOf(match.guestScore));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvLiveStatus;
        private TextView mTvTitle;
        private TextView mTvTopScore, mTvTopName;
        private TextView mTvBottomScore;
        private TextView mTvBottomName;
        private ImageView mIvBottomIcon;
        private ImageView mIvTopIcon;
        private ConstraintLayout mClRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvLiveStatus = itemView.findViewById(R.id.tv_live_status);
            mClRoot = itemView.findViewById(R.id.cl_root);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvTopScore = itemView.findViewById(R.id.tv_top_score);
            mTvTopName = itemView.findViewById(R.id.tv_top_name);
            mTvBottomScore = itemView.findViewById(R.id.tv_bottom_score);
            mTvBottomName = itemView.findViewById(R.id.tv_bottom_name);
            mIvBottomIcon = itemView.findViewById(R.id.iv_bottom_icon);
            mIvTopIcon = itemView.findViewById(R.id.iv_top_icon);
        }

    }
}

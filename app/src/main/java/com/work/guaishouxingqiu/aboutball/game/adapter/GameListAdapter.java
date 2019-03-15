package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 13:53
 * 更新时间: 2019/3/15 13:53
 * 描述:比赛列表Adapter
 */
public class GameListAdapter extends BaseRecyclerAdapter<GameListAdapter.ViewHolder, List<ResultGameBean>> {
    private List<ResultGameBean> mData;

    public GameListAdapter(@NonNull List<ResultGameBean> data) {
        super(data);
        this.mData = data;
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameBean bean = mData.get(i);
        viewHolder.mTvName.setText(bean.matchName.concat("  ").concat(bean.gameName));
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), bean.hostLogoUrl, viewHolder.mIvLeft);
        viewHolder.mTvLeft.setText(bean.hostName);
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), bean.guestLogoUrl, viewHolder.mIvRight);
        viewHolder.mTvRight.setText(bean.guestName);
        viewHolder.mTvScore.setText(String.valueOf(bean.hostScore).concat(" - ").concat(String.valueOf(bean.guestScore)));
        viewHolder.mTvStatus.setText(bean.matchState);
        try {
            UIUtils.setGameIconStatus(Integer.valueOf(bean.stateId), viewHolder.mTvStatus);
        } catch (Exception e) {
            UIUtils.setGameIconStatus(0, viewHolder.mTvStatus);
            e.printStackTrace();
        }

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_list_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private ImageView mIvLeft;
        private TextView mTvLeft;
        private ImageView mIvRight;
        private TextView mTvRight;
        private TextView mTvName;
        private TextView mTvScore;
        private TextView mTvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mIvLeft = itemView.findViewById(R.id.iv_left_name);
            mTvLeft = itemView.findViewById(R.id.tv_left_name);
            mIvRight = itemView.findViewById(R.id.iv_right_name);
            mTvRight = itemView.findViewById(R.id.tv_right_name);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvScore = itemView.findViewById(R.id.tv_score);
            mTvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}

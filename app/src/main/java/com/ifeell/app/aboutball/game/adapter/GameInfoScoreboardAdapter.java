package com.ifeell.app.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoScoreboardBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 17:55
 * 更新时间: 2019/6/25 17:55
 * 描述: 比赛- 数据-积分榜adapter
 */
public class GameInfoScoreboardAdapter extends BaseRecyclerAdapter<GameInfoScoreboardAdapter.ViewHolder, List<ResultGameInfoScoreboardBean>> {

    public GameInfoScoreboardAdapter(@NonNull List<ResultGameInfoScoreboardBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameInfoScoreboardBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvRaking, bean.rank + "");
        GlideManger.get().loadLogoImage(mContext, bean.logoUrl, viewHolder.mCivLogo);
        UIUtils.setText(viewHolder.mTvTeam, bean.teamName);
        UIUtils.setText(viewHolder.mTvChang, bean.matchTimes);
        UIUtils.setText(viewHolder.mTvFlat, bean.successTimes);
        UIUtils.setText(viewHolder.mTvLoss, bean.goldTimes);
        UIUtils.setText(viewHolder.mTvIntegral, bean.score);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_info_scoreboard_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvRaking;
        private CircleImageView mCivLogo;
        private TextView mTvTeam;
        private TextView mTvChang;
        private TextView mTvFlat;
        private TextView mTvLoss;
        private TextView mTvIntegral;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvRaking = itemView.findViewById(R.id.tv_raking);
            mCivLogo = itemView.findViewById(R.id.civ_logo);
            mTvTeam = itemView.findViewById(R.id.tv_team);
            mTvChang = itemView.findViewById(R.id.tv_chang);
            mTvFlat = itemView.findViewById(R.id.tv_flat);
            mTvLoss = itemView.findViewById(R.id.tv_loss);
            mTvIntegral = itemView.findViewById(R.id.tv_integral);
        }
    }
}

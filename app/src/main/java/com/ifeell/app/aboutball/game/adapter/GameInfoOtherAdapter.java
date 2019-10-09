package com.ifeell.app.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoOtherBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/26 9:57
 * 更新时间: 2019/6/26 9:57
 * 描述:比赛-数据-积分榜、助攻榜Adapter
 */
public class GameInfoOtherAdapter extends BaseRecyclerAdapter<GameInfoOtherAdapter.ViewHolder, List<ResultGameInfoOtherBean>> {
    public GameInfoOtherAdapter(@NonNull List<ResultGameInfoOtherBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameInfoOtherBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvRank, bean.rank + "");
        GlideManger.get().loadHeadImage(mContext, bean.imageUrl, viewHolder.mCivHead);
        UIUtils.setText(viewHolder.mTvName, bean.playerName);
        UIUtils.setText(viewHolder.mTvTeamName, bean.teamName);
        UIUtils.setText(viewHolder.mTvCount, bean.count + "");

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_info_other_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvRank;
        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvTeamName;
        private TextView mTvCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvRank = itemView.findViewById(R.id.tv_rank);
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTeamName = itemView.findViewById(R.id.tv_team_name);
            mTvCount = itemView.findViewById(R.id.tv_count);
        }

    }
}

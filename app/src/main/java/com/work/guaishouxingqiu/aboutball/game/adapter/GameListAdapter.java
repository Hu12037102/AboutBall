package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 13:53
 * 更新时间: 2019/3/15 13:53
 * 描述:比赛列表Adapter
 */
public class GameListAdapter extends BaseRecyclerAdapter<GameListAdapter.ViewHolder, List<ResultGameBean>> {
    public int itemHeight;
    private ArrayMap<Integer, String> mDateMap;

    public GameListAdapter(@NonNull List<ResultGameBean> data) {
        super(data);
        initDateMap();
    }

    private void initDateMap() {
        mDateMap = new ArrayMap<>();

    }

    public void notifyData() {
        mDateMap.clear();
        for (int i = 0; i < mData.size(); i++) {
            LogUtils.w("notifyData--", DateUtils.getDate(mData.get(i).startTime));

            if (!mDateMap.containsValue(DateUtils.getDate(mData.get(i).startTime))) {
                mDateMap.put(i, DateUtils.getDate(mData.get(i).startTime));
            }
        }
        this.notifyDataSetChanged();

    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.post(new Runnable() {
            @Override
            public void run() {
                itemHeight = viewHolder.itemView.getHeight();
            }
        });

        ResultGameBean bean = mData.get(i);
        LogUtils.w("onBindViewDataHolder--", mDateMap.containsKey(i) + "--");
        if (mDateMap.containsKey(i)) {
            viewHolder.mTvTime.setVisibility(View.VISIBLE);
            viewHolder.mTvTime.setText(mDateMap.get(i));
        } else {
            viewHolder.mTvTime.setVisibility(View.GONE);
        }
        String content = "";
        viewHolder.mTvName.setText(content.concat(bean.gameName == null ? "" : bean.gameName).concat(" ").concat(bean.matchName == null ? "" : bean.matchName));
        GlideManger.get().loadLogoImage(viewHolder.itemView.getContext(), bean.hostLogoUrl, viewHolder.mCivLeft);
        viewHolder.mTvLeft.setText(bean.hostName);
        GlideManger.get().loadLogoImage(viewHolder.itemView.getContext(), bean.guestLogoUrl, viewHolder.mCivRight);
        viewHolder.mTvRight.setText(bean.guestName);
        viewHolder.mTvScore.setText(String.valueOf(bean.hostScore).concat(" - ").concat(String.valueOf(bean.guestScore)));
        viewHolder.mTvStatus.setText(bean.matchState);
        try {
            UIUtils.setGameIconStatus(Integer.valueOf(bean.stateId), viewHolder.mTvStatus);
        } catch (NumberFormatException e) {
            UIUtils.setGameIconStatus(0, viewHolder.mTvStatus);
            e.printStackTrace();
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, i);
            }
        });

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_list_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private CircleImageView mCivLeft;
        private TextView mTvLeft;
        private CircleImageView mCivRight;
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
            mCivLeft = itemView.findViewById(R.id.civ_left_name);
            mTvLeft = itemView.findViewById(R.id.tv_left_name);
            mCivRight = itemView.findViewById(R.id.civ_right_name);
            mTvRight = itemView.findViewById(R.id.tv_right_name);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvScore = itemView.findViewById(R.id.tv_score);
            mTvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}

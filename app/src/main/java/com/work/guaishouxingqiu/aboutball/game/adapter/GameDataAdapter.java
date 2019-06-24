package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 15:17
 * 更新时间: 2019/6/24 15:17
 * 描述:比赛数据赛况详情Adapter
 */
public class GameDataAdapter extends BaseRecyclerAdapter<GameDataAdapter.ViewHolder, List<ResultGameDataResultBean>> {
    public static final int TYPE_HOST = 1;
    public static final int TYPE_GUEST = 2;

    public GameDataAdapter(@NonNull List<ResultGameDataResultBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameDataResultBean bean = mData.get(i);
        ViewGroup.LayoutParams tvTimeParams = viewHolder.mTvTime.getLayoutParams();
        if (i <= 0) {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 6);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 6);
        } else {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 25);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 25);
            viewHolder.mTvTime.setText(bean.time + "'");
        }
        if (bean.teamType == TYPE_HOST) {
            addTimeStatusView(viewHolder.mLlHost, bean.teamType, bean);
        } else if (bean.teamType == TYPE_GUEST) {
            addTimeStatusView(viewHolder.mLlGuest, bean.teamType, bean);
        }

    }

    private void addTimeStatusView(LinearLayout linearLayout, int type, ResultGameDataResultBean bean) {
        if (bean.matchSituationList == null || bean.matchSituationList.size() == 0) {
            return;
        }

        for (ResultGameDataResultBean.MatchSituation matchSituation : bean.matchSituationList) {
            TextView textView = new TextView(linearLayout.getContext());
            linearLayout.addView(textView);
            setTextViewIcon(matchSituation.actionId, textView, type);
            textView.setText(matchSituation.playerName);
        }

    }

    private void setTextViewIcon(int actionId, TextView textView, int type) {
        switch (actionId) {
            case 1:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_goals : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_goals : 0, 0);
                break;
            case 2:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_assist : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_assist : 0, 0);
                break;
            case 3:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_go_up : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_go_up : 0, 0);
                break;
            case 4:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_go_down : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_go_down : 0, 0);
                break;
            case 5:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_yellow_card : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_yellow_card : 0, 0);

                break;
            case 6:
                textView.setCompoundDrawablesWithIntrinsicBounds(type == GameDataAdapter.TYPE_GUEST ?
                        R.mipmap.icon_red_card : 0, 0, type == GameDataAdapter.TYPE_HOST ? R.mipmap.icon_red_card : 0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_data_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private LinearLayout mLlHost;
        private LinearLayout mLlGuest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mLlHost = itemView.findViewById(R.id.ll_host);
            mLlGuest = itemView.findViewById(R.id.ll_guest);
        }
    }
}

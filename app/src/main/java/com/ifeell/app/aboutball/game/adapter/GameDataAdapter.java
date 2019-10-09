package com.ifeell.app.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultGameDataResultBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 15:17
 * 更新时间: 2019/6/24 15:17
 * 描述:比赛数据赛况详情Adapter
 */
public class GameDataAdapter extends BaseRecyclerAdapter<GameDataAdapter.ViewHolder, List<ResultGameDataResultBean>> {
    private static final int TYPE_HOST = 1;
    private static final int TYPE_GUEST = 2;

    public GameDataAdapter(@NonNull List<ResultGameDataResultBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameDataResultBean bean = mData.get(i);
        ViewGroup.LayoutParams tvTimeParams = viewHolder.mTvTime.getLayoutParams();
        if (bean.isStart) {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 6);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 6);
        } else {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 25);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 25);
            viewHolder.mTvTime.setText(bean.time + "'");
        }
        if (bean.isStart) {
            viewHolder.mLineTop.setVisibility(View.VISIBLE);
            viewHolder.mLineTop.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = viewHolder.mLineTop.getLayoutParams();
                    layoutParams.height = (viewHolder.mClTopRoot.getMeasuredHeight() - viewHolder.mTvTime.getMeasuredHeight()) / 2;
                    viewHolder.mLineTop.setLayoutParams(layoutParams);
                }
            });
        } else {
            viewHolder.mLineTop.setVisibility(View.GONE);
        }


        if (bean.teamType == TYPE_HOST) {
            addTimeStatusView(viewHolder.mLlHost, bean.teamType, bean);
        } else if (bean.teamType == TYPE_GUEST) {
            addTimeStatusView(viewHolder.mLlGuest, bean.teamType, bean);
        }
        if (i == mData.size() - 1) {
            viewHolder.mViewFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mViewFoot.setVisibility(View.GONE);
        }

    }

    private void addTimeStatusView(LinearLayout linearLayout, int type, ResultGameDataResultBean bean) {
        if (bean.matchSituationList == null || bean.matchSituationList.size() == 0) {
            return;
        }
        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }
        for (int i = 0; i < bean.matchSituationList.size(); i++) {
            ResultGameDataResultBean.MatchSituation matchSituation = bean.matchSituationList.get(i);
            TextView textView = new TextView(linearLayout.getContext());
            textView.setCompoundDrawablePadding(ScreenUtils.dp2px(mContext, 10));
            linearLayout.addView(textView);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(ContextCompat.getColor(mContext, i == 0 ? R.color.color_4 : R.color.colorFFA6A6A6));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = (GameDataAdapter.TYPE_HOST == type ? Gravity.RIGHT : Gravity.LEFT);
            textView.setLayoutParams(layoutParams);
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
        private View mViewFoot;
        private View mLineTop;
        private ConstraintLayout mClTopRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mLlHost = itemView.findViewById(R.id.ll_host);
            mLlGuest = itemView.findViewById(R.id.ll_guest);
            mViewFoot = itemView.findViewById(R.id.include_foot);
            mLineTop = itemView.findViewById(R.id.line_top);
            mClTopRoot = itemView.findViewById(R.id.cl_top);
        }
    }
}

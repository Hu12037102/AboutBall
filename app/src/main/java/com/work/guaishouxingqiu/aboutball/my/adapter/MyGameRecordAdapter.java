package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameDataAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyGameRecordBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/3 18:20
 * 更新时间: 2019/7/3 18:20
 * 描述:我的球队赛况记录Adapter
 */
public class MyGameRecordAdapter  extends BaseRecyclerAdapter<MyGameRecordAdapter.ViewHolder,List<ResultMyGameRecordBean>>{
    private static final int TYPE_HOST = 1;
    private static final int TYPE_GUEST = 0;
    public MyGameRecordAdapter(@NonNull List<ResultMyGameRecordBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyGameRecordBean bean = mData.get(i);
        ViewGroup.LayoutParams tvTimeParams = viewHolder.mTvTime.getLayoutParams();
        if (bean.isStart) {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 6);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 6);
        } else {
            tvTimeParams.width = ScreenUtils.dp2px(mContext, 25);
            tvTimeParams.height = ScreenUtils.dp2px(mContext, 25);
            viewHolder.mTvTime.setText(bean.duration + "'");
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
            if (bean.isHostTeam == TYPE_HOST) {
                addTimeStatusView(viewHolder.mLlHost, TYPE_HOST, bean);
            } else if (bean.isHostTeam == TYPE_GUEST) {
                addTimeStatusView(viewHolder.mLlGuest, TYPE_GUEST, bean);
            }
        }



        if (i == mData.size() - 1) {
            viewHolder.mViewFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mViewFoot.setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyGameRecordAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_data_view, viewGroup, false));
    }

    private void addTimeStatusView(LinearLayout linearLayout, int type, ResultMyGameRecordBean bean) {

        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }
            TextView textView = new TextView(linearLayout.getContext());
            textView.setCompoundDrawablePadding(ScreenUtils.dp2px(mContext, 10));
            linearLayout.addView(textView);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_4 ));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = (MyGameRecordAdapter.TYPE_HOST == type ? Gravity.RIGHT : Gravity.LEFT);
            textView.setLayoutParams(layoutParams);
            setTextViewIcon(bean.action, textView, type);
            textView.setText(bean.nickName);

    }

    private void setTextViewIcon(String action, TextView textView, int type) {
        switch (action) {
            case "进球":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_goals : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_goals : 0, 0);
                break;
            case "助攻":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_assist : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_assist : 0, 0);
                break;
            case "上场":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_go_up : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_go_up : 0, 0);
                break;
            case"下场":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_go_down : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_go_down : 0, 0);
                break;
            case "黄牌":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_yellow_card : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_yellow_card : 0, 0);

                break;
            case "红牌":
                textView.setCompoundDrawablesWithIntrinsicBounds(type == MyGameRecordAdapter.TYPE_GUEST ?
                        R.mipmap.icon_red_card : 0, 0, type == MyGameRecordAdapter.TYPE_HOST ? R.mipmap.icon_red_card : 0, 0);
                break;
            default:
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvTime;
        private LinearLayout mLlHost;
        private LinearLayout mLlGuest;
        private View mViewFoot;
        private View mLineTop;
        private ConstraintLayout mClTopRoot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mLlHost = itemView.findViewById(R.id.ll_host);
            mLlGuest = itemView.findViewById(R.id.ll_guest);
            mViewFoot = itemView.findViewById(R.id.include_foot);
            mLineTop = itemView.findViewById(R.id.line_top);
            mClTopRoot = itemView.findViewById(R.id.cl_top);
        }
    }
}

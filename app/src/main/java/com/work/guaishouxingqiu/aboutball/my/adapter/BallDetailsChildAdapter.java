package com.work.guaishouxingqiu.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 17:18
 * 更新时间: 2019/4/25 17:18
 * 描述:球队信息adapter
 */
public class BallDetailsChildAdapter extends BaseRecyclerAdapter<BallDetailsChildAdapter.ViewHolder,
        List<ResultBallDetailsBean.MatchBean>> {
    private boolean mIsInputEvaluate = true;

    public BallDetailsChildAdapter(@NonNull List<ResultBallDetailsBean.MatchBean> data) {
        super(data);
    }

    /**
     * 要不要显示输入评价（对手评价，裁判评价）
     *
     * @param data
     * @param isInputEvaluate
     */
    public BallDetailsChildAdapter(@NonNull List<ResultBallDetailsBean.MatchBean> data, boolean isInputEvaluate) {
        super(data);
        mIsInputEvaluate = isInputEvaluate;
    }

    public void setOnBallDetailsClickListener(OnBallDetailsClickListener onBallDetailsClickListener) {
        this.onBallDetailsClickListener = onBallDetailsClickListener;
    }

    private OnBallDetailsClickListener onBallDetailsClickListener;


    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultBallDetailsBean.MatchBean bean = mData.get(i);
        viewHolder.mTvTime.setText(DateUtils.getStartTime2EndTime(bean.startTime, bean.endTime));
        GlideManger.get().loadLogoImage(mContext, bean.hostTeamLogo, viewHolder.mCivLeftLogo);
        viewHolder.mTvLeftName.setText(bean.hostTeamName);
        viewHolder.mTvGradle.setText(bean.hostScore + "  -  " + bean.guestScore);
        GlideManger.get().loadLogoImage(mContext, bean.guestTeamLogo, viewHolder.mCivRightLogo);
        viewHolder.mTvRightName.setText(bean.guestTeamName);
        if (mIsInputEvaluate) {
            viewHolder.mTvMatchRecords.setVisibility(View.VISIBLE);
            //viewHolder.mTvMatchEvaluate.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTvMatchRecords.setVisibility(View.GONE);
            //viewHolder.mTvMatchEvaluate.setVisibility(View.GONE);
        }
        viewHolder.mTvMatchRecords.setOnClickListener(v -> {
            if (onBallDetailsClickListener != null) {
                onBallDetailsClickListener.onClickRecord(v, i);
            }
        });
        viewHolder.mTvMatchEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBallDetailsClickListener != null) {
                    onBallDetailsClickListener.onClickEvaluate(v, i);
                }
            }
        });
        if (i == mData.size() - 1) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ball_details_child_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private CircleImageView mCivLeftLogo;
        private TextView mTvLeftName;
        private TextView mTvGradle;
        private CircleImageView mCivRightLogo;
        private TextView mTvRightName;
        private TextView mTvMatchRecords;
        private TextView mTvMatchEvaluate;
        private View mIncludeFoot;
        private CardView mCvCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mCivLeftLogo = itemView.findViewById(R.id.civ_left_logo);
            mTvLeftName = itemView.findViewById(R.id.tv_left_name);
            mTvGradle = itemView.findViewById(R.id.tv_gradle);
            mCivRightLogo = itemView.findViewById(R.id.civ_right_logo);
            mTvRightName = itemView.findViewById(R.id.tv_right_name);
            mTvMatchRecords = itemView.findViewById(R.id.tv_match_records);
            mTvMatchEvaluate = itemView.findViewById(R.id.tv_match_evaluate);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
            mCvCard = itemView.findViewById(R.id.cv_card);
        }
    }

    public interface OnBallDetailsClickListener {
        void onClickEvaluate(View view, int position);

        void onClickRecord(View view, int position);
       /* void onClickJudgeReferee(View view, int position);



        void onClickJudgeTeam(View view, int position);*/
    }
}

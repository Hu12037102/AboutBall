package com.work.guaishouxingqiu.aboutball.venue.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/5 15:03
 * 更新时间: 2019/5/5 15:03
 * 描述:选择球队适配器
 */
public class SelectorBallTeamAdapter extends BaseRecyclerAdapter<SelectorBallTeamAdapter.ViewHolder, List<ResultMyBallTeamBean>> {
    public SelectorBallTeamAdapter(@NonNull List<ResultMyBallTeamBean> data) {
        super(data, false);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyBallTeamBean bean = mData.get(i);
        GlideManger.get().loadLogoImage(mContext, bean.teamLogo, viewHolder.mCivLogo);
        viewHolder.mTvContent.setText(bean.teamName);
        viewHolder.mCbSelector.setChecked(bean.isCheck);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!bean.isCheck) {
                for (ResultMyBallTeamBean ballTeamBean : mData) {
                    if (ballTeamBean.equals(bean)) {
                        bean.isCheck = true;
                    } else {
                        ballTeamBean.isCheck = false;
                    }
                }
                notifyItemRangeChanged(0, mData.size());
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, i);
                }
            }

        });
    }

    public ResultMyBallTeamBean getCheckTeam() {
        if (mData != null && mData.size() > 0) {
            for (ResultMyBallTeamBean bean : mData) {
                if (bean.isCheck) {
                    return bean;
                }
            }
        }
        return null;
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_selector_ball_team_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivLogo;
        private CheckBox mCbSelector;
        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivLogo = itemView.findViewById(R.id.civ_logo);
            mCbSelector = itemView.findViewById(R.id.cb_selector);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

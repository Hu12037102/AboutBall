package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultMyBallBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:06
 * 更新时间: 2019/4/24 11:06
 * 描述:我的球队Adapter
 */
public class MyBallTeamAdapter extends BaseRecyclerAdapter<MyBallTeamAdapter.ViewHolder, List<ResultMyBallBean>> {
    public MyBallTeamAdapter(@NonNull List<ResultMyBallBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyBallBean bean = mData.get(i);
        GlideManger.get().loadLogoImage(mContext, bean.teamLogo, viewHolder.mCivLogo);
        viewHolder.mTvName.setText(bean.teamName);
        viewHolder.mTvManger.setText(UIUtils.getString(R.string.leader_s, bean.leaderName));
        switch (bean.isLeader) {
            //队员
            case 0:
                viewHolder.mTvStatus.setText(R.string.read);
                break;
            //管理者
            case 1:
                viewHolder.mTvStatus.setText(R.string.manger);
                break;
            default:
                break;
        }

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_ball_team_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCivLogo;
        private TextView mTvName;
        private TextView mTvManger;
        private TextView mTvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivLogo = itemView.findViewById(R.id.civ_logo);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvManger = itemView.findViewById(R.id.tv_manger);
            mTvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}

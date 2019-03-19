package com.work.guaishouxingqiu.aboutball.venue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 17:33
 * 更新时间: 2019/3/18 17:33
 * 描述: 约球Adapter
 */
public class AboutBallAdapter extends BaseRecyclerAdapter<AboutBallAdapter.ViewHolder, List<ResultAboutBallBean>> {
    public AboutBallAdapter(@NonNull List<ResultAboutBallBean> data) {
        super(data);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_about_ball_view, viewGroup, false));
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultAboutBallBean bean = mData.get(i);
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), bean.teamLogo, R.drawable.shape_item_recommend_preview_item,
                R.drawable.shape_item_recommend_preview_item, viewHolder.mIvData);
        viewHolder.mTvName.setText(bean.teamName);
        viewHolder.mTvTime.setText(bean.startTime);
        viewHolder.mTvAddress.setText(bean.stadiumName);
        viewHolder.mTvStatus1.setVisibility(bean.hasReferee.equals(Contast.HAS_REFEREE) ? View.VISIBLE : View.GONE);
        viewHolder.mTvStatus2.setVisibility(bean.hasOpponent.equals(Contast.HAS_RIVAL) ? View.VISIBLE : View.GONE);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvData;
        private TextView mTvName;
        private TextView mTvTime;
        private TextView mTvAddress;
        private TextView mTvStatus1;
        private TextView mTvStatus2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvData = itemView.findViewById(R.id.iv_data);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mTvStatus1 = itemView.findViewById(R.id.tv_status_1);
            mTvStatus2 = itemView.findViewById(R.id.tv_status_2);
        }
    }
}
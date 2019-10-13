package com.ifeell.app.aboutball.game.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultGameDetailsBean;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.weight.GameDataBar;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 14:31
 * 更新时间: 2019/3/26 14:31
 * 描述:比赛数据Adapter
 */
public class GameHeadDataAdapter extends RecyclerView.Adapter<GameHeadDataAdapter.ViewHolder> {
    private static final int TYPE_INTEGER = 1;

    private static final int TYPE_FLOAT = 0;
    private Context mContext;
    List<ResultGameDetailsBean.Bean> mData;

    @Override
    public long getItemId(int position) {
        return position;
    }

    public GameHeadDataAdapter(@NonNull Context context, @NonNull List<ResultGameDetailsBean.Bean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_head_data_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameDetailsBean.Bean bean = mData.get(i);

        viewHolder.mTvCenter.setText(bean.statsName);
        switch (bean.dataType) {
            case TYPE_INTEGER:
                viewHolder.mGdbLeft.setProportion(bean.hostValue == 0 ? bean.hostValue : bean.hostValue / (bean.hostValue + bean.guestValue));
                viewHolder.mGdbRight.setProportion(bean.guestValue == 0 ? bean.guestValue : bean.guestValue / (bean.hostValue + bean.guestValue));
                viewHolder.mTvLeft.setText(String.valueOf(DataUtils.getInt(bean.hostValue + "")));
                viewHolder.mTvRight.setText(String.valueOf(DataUtils.getInt(bean.guestValue + "")));
                break;
            case TYPE_FLOAT:
                viewHolder.mGdbLeft.setProportion(bean.hostValue / (bean.hostValue + bean.guestValue));
                viewHolder.mGdbRight.setProportion(bean.guestValue / (bean.hostValue + bean.guestValue));
                viewHolder.mTvLeft.setText(bean.hostValue + "%");
                viewHolder.mTvRight.setText(bean.guestValue + "%");
                break;
        }
        if (bean.hostValue == bean.guestValue) {
            viewHolder.mGdbLeft.setBarColor(ContextCompat.getColor(mContext, R.color.colorFFDB2F23));
            viewHolder.mGdbRight.setBarColor(ContextCompat.getColor(mContext, R.color.colorFFDB2F23));
        } else if (bean.hostValue > bean.guestValue) {
            viewHolder.mGdbLeft.setBarColor(ContextCompat.getColor(mContext, R.color.color_2));
            viewHolder.mGdbRight.setBarColor(ContextCompat.getColor(mContext, R.color.colorFFC0C0C0));
        } else {
            viewHolder.mGdbRight.setBarColor(ContextCompat.getColor(mContext, R.color.color_2));
            viewHolder.mGdbLeft.setBarColor(ContextCompat.getColor(mContext, R.color.colorFFC0C0C0));
        }
    }
/*
    @Override
    public long getItemId(int position) {
        return position;
    }*/

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvLeft;
        private TextView mTvRight;
        private TextView mTvCenter;
        private GameDataBar mGdbLeft;
        private GameDataBar mGdbRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvLeft = itemView.findViewById(R.id.tv_left);
            mTvRight = itemView.findViewById(R.id.tv_right);
            mTvCenter = itemView.findViewById(R.id.tv_center);
            mGdbLeft = itemView.findViewById(R.id.gdb_left);
            mGdbRight = itemView.findViewById(R.id.gdb_right);
        }
    }


}



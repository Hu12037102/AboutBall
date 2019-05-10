package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.GameDataBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 14:31
 * 更新时间: 2019/3/26 14:31
 * 描述:比赛数据Adapter
 */
public class GameDataAdapter extends BaseRecyclerAdapter<GameDataAdapter.ViewHolder, List<ResultGameDetailsBean.Bean>> {
    private static final int TYPE_INTEGER = 1;

    private static final int TYPE_FLOAT = 0;

    public GameDataAdapter(@NonNull List<ResultGameDetailsBean.Bean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
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
                viewHolder.mGdbLeft.setProportion(bean.hostValue);
                viewHolder.mGdbRight.setProportion(bean.guestValue);
                viewHolder.mTvLeft.setText(String.valueOf(DataUtils.getPercent(bean.hostValue)));
                viewHolder.mTvRight.setText(String.valueOf(DataUtils.getPercent(bean.guestValue )));
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

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_data_view, viewGroup, false));
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

    @Override
    public long getItemId(int position) {
        return position;
    }
}



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
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 19:55
 * 更新时间: 2019/3/26 19:55
 * 描述: 比赛-集锦适配器
 */
public class GameCollectionAdapter extends BaseRecyclerAdapter<GameCollectionAdapter.ViewHolder, List<ResultGameCollectionBean>> {
    public GameCollectionAdapter(@NonNull List<ResultGameCollectionBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameCollectionBean bean = mData.get(i);
        GlideManger.get().loadDefaultImage(mContext, bean.photoUrl, viewHolder.mRivData);
        viewHolder.mTvContent.setText(bean.title);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = ScreenUtils.screenWidth(mContext) / 2;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        if (i == getItemCount() - 1 || i == getItemCount() - 2) {
            layoutParams.bottomMargin = ScreenUtils.dp2px(mContext, 20);
        }
        if (i % 2 == 0) {
            viewHolder.mRivData.setPadding(ScreenUtils.dp2px(mContext, 20), ScreenUtils.dp2px(mContext, 15),
                    ScreenUtils.dp2px(mContext, 8), 0);

        } else {
            viewHolder.mRivData.setPadding(ScreenUtils.dp2px(mContext, 8), ScreenUtils.dp2px(mContext, 15),
                    ScreenUtils.dp2px(mContext, 20), 0);
        }
        viewHolder.itemView.setLayoutParams(layoutParams);


    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_collection_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivData;
        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivData = itemView.findViewById(R.id.riv_data);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

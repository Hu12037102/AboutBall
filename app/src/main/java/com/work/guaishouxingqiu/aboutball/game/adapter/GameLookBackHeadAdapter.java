package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameLiveDetailsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/26 16:43
 * 更新时间: 2019/6/26 16:43
 * 描述:回顾集锦Adapter
 */
public class GameLookBackHeadAdapter extends RecyclerView.Adapter<GameLookBackHeadAdapter.ViewHolder> {
    private Context mContext;
    private List<ResultGameLiveDetailsBean.MatchVideoBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public GameLookBackHeadAdapter(@NonNull Context context, List<ResultGameLiveDetailsBean.MatchVideoBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_look_book_head_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameLiveDetailsBean.MatchVideoBean bean = mData.get(i);
        GlideManger.get().loadBannerImage(mContext, bean.photoUrl, viewHolder.mRivContent);
        UIUtils.setText(viewHolder.mTvContent, bean.description);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams();
        marginLayoutParams.leftMargin = ScreenUtils.dp2px(mContext, 10);
        if (i == mData.size() - 1) {
            marginLayoutParams.rightMargin = ScreenUtils.dp2px(mContext, 15);
        } else {
            marginLayoutParams.rightMargin = ScreenUtils.dp2px(mContext, 0);
        }
        if (bean.isCheck) {
            viewHolder.mTvContent.setTextColor(ContextCompat.getColor(mContext, R.color.color_2));
        } else {
            viewHolder.mTvContent.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
        }
        viewHolder.itemView.setLayoutParams(marginLayoutParams);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < mData.size(); j++) {
                    if (i != j) {
                        mData.get(j).isCheck = false;
                    } else {
                        bean.isCheck = true;
                    }
                }
                notifyDataSetChanged();
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(v, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivContent;
        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivContent = itemView.findViewById(R.id.riv_content);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

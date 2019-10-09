package com.ifeell.app.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultReviewBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 11:41
 * 更新时间: 2019/6/27 11:41
 * 描述:
 */
public class MatchReviewAdapter extends BaseRecyclerAdapter<MatchReviewAdapter.ViewHolder, List<ResultReviewBean>> {
    public MatchReviewAdapter(@NonNull List<ResultReviewBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultReviewBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvContent, bean.description);
        GlideManger.get().loadBannerImage(mContext, bean.coverImg, viewHolder.mRivContent);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (i % 2 == 0) {
            layoutParams.leftMargin = ScreenUtils.dp2px(mContext, 10);
            layoutParams.rightMargin = 0;

        } else {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = ScreenUtils.dp2px(mContext, 10);
        }
        viewHolder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_look_book_head_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivContent;
        private TextView mTvDescribe;
        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivContent = itemView.findViewById(R.id.riv_content);
            mTvDescribe = itemView.findViewById(R.id.tv_describe);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvDescribe.setText(R.string.collection);
        }
    }
}

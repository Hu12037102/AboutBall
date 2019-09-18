package com.work.guaishouxingqiu.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 14:58
 * 更新时间: 2019/3/26 14:58
 * 描述:比赛评论Adapter
 */
public class GameCommentAdapter extends BaseRecyclerAdapter<GameCommentAdapter.ViewHolder, List<ResultGameCommentBean>> {


    public GameCommentAdapter(@NonNull List<ResultGameCommentBean> data) {
        super(data);

    }


    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultGameCommentBean bean = mData.get(i);
        if (bean.nickName != null) {
            String data = bean.nickName.concat(": ").concat(bean.commentContent);
          //  viewHolder.mTvContent.setText(SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, bean.nickName.length() + 1, data));
            UIUtils.setText(viewHolder.mTvContent,SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, bean.nickName.length() + 1,
                    UIUtils.getEmojiSpannable(viewHolder.mTvContent,data)));
        }
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewHolder.mTvContent.getLayoutParams();
        if (i == 0) {
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        } else if (i == mData.size() - 1) {
            layoutParams.topMargin = ScreenUtils.dp2px(mContext, 15);
            layoutParams.bottomMargin = ScreenUtils.dp2px(mContext, 15);
        } else {
            layoutParams.topMargin = ScreenUtils.dp2px(mContext, 15);
            layoutParams.bottomMargin = 0;
        }
        viewHolder.mTvContent.setLayoutParams(layoutParams);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_game_comment_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

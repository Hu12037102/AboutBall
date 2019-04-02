package com.work.guaishouxingqiu.aboutball.game.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;

import java.util.List;

import me.jessyan.autosize.utils.ScreenUtils;

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

        viewHolder.mTvContent.setText(SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, bean.nickName.length() + 1, data));
        }
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

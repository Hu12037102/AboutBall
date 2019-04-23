package com.work.guaishouxingqiu.aboutball.community.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 16:27
 * 更新时间: 2019/3/19 16:27
 * 描述: 社区动态Adapter
 */
public class CommunityDataAdapter extends BaseRecyclerAdapter<CommunityDataAdapter.ViewHolder, List<ResultCommunityDataBean>> {
    public CommunityDataAdapter(@NonNull List<ResultCommunityDataBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        Context context = viewHolder.itemView.getContext();
        ResultCommunityDataBean bean = mData.get(i);
        viewHolder.mTvName.setText(bean.nickName);
        viewHolder.mTvTime.setText(bean.releaseTime);
        if (!DataUtils.isEmpty(bean.topicTitle)) {
            String data = bean.topicTitle.concat(bean.tweetContent);
            viewHolder.mTvData.setText(data);
           /* SpanUtils.getTextColor(viewHolder.mTvData, R.color.color_2, 0, bean.topicTitle.length(),
                    view -> {
                        //点击话题列表
                    });*/
        } else {
            viewHolder.mTvData.setText(bean.tweetContent);
        }

        viewHolder.mTvLikeNum.setText(bean.praiseCount);
        viewHolder.mTvComment.setText(bean.commentCount);
        viewHolder.mTvShare.setText(bean.shareCount);
        viewHolder.mGroupImageData.setVisibility(View.VISIBLE);
        int imageCount = DataUtils.splitImagePathCount(bean.imageUrl);
        switch (imageCount) {
            case 0:
                viewHolder.mGroupImageData.setVisibility(View.GONE);
                break;
            case 1:
                ImageView imageView = new ImageView(viewHolder.itemView.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ScreenUtils.dp2px(viewHolder.itemView.getContext(), 176),
                        ScreenUtils.dp2px(viewHolder.itemView.getContext(), 176));
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String[] imagePaths = bean.imageUrl.split(",");
                GlideManger.get().loadDefaultImage(context, imagePaths[0], imageView);
                viewHolder.mGroupImageData.addView(imageView);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }


    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_community_data_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCliHead;
        private ImageView mIvMore;
        private TextView mTvAttention, mTvName, mTvTime, mTvData;
        private LinearLayout mGroupImageData;
        private LinearLayout mLlLike;
        private TextView mTvLikeNum;
        private LinearLayout mLlComment;
        private TextView mTvComment;
        private LinearLayout mLlShare;
        private TextView mTvShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCliHead = itemView.findViewById(R.id.civ_head);
            mIvMore = itemView.findViewById(R.id.iv_more);
            mTvAttention = itemView.findViewById(R.id.tv_attention);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvData = itemView.findViewById(R.id.tv_data);
            mGroupImageData = itemView.findViewById(R.id.ll_iv_data);
            mLlLike = itemView.findViewById(R.id.ll_like);
            mTvLikeNum = itemView.findViewById(R.id.tv_like_num);
            mLlComment = itemView.findViewById(R.id.ll_comment);
            mTvComment = itemView.findViewById(R.id.tv_comment);
            mLlShare = itemView.findViewById(R.id.ll_share);
            mTvShare = itemView.findViewById(R.id.tv_share);
        }
    }
}

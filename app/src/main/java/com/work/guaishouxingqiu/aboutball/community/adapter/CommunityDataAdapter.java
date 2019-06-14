package com.work.guaishouxingqiu.aboutball.community.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

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

    public static final int MAX_COMMUNITY_COUNT = 99;
    public static final String MAX_COMMUNITY_CONTENT = "99+";

    public void setOnTextContentClickListener(OnTextContentClickListener onTextContentClickListener) {
        this.onTextContentClickListener = onTextContentClickListener;
    }

    private OnTextContentClickListener onTextContentClickListener;

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        Context context = viewHolder.itemView.getContext();
        ResultCommunityDataBean bean = mData.get(i);
        viewHolder.mTvName.setText(bean.nickName);
        viewHolder.mTvTime.setText(bean.releaseTime);
        GlideManger.get().loadHeadImage(mContext, bean.headImg, viewHolder.mCliHead);
        if (!DataUtils.isEmpty(bean.topicTitle)) {
            String content = bean.topicTitle.concat(bean.tweetContent);
            viewHolder.mTvData.setText(SpanUtils.getClickText(viewHolder.mTvData, content, R.color.color_2, 0, bean.topicTitle.length(), new SpanUtils.OnClickTextListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    if (onTextContentClickListener!= null){
                        onTextContentClickListener.onClickTopic(view,i);
                    }
                    view.setEnabled(true);
                }
            }));
            viewHolder.mTvData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTextContentClickListener!= null){
                        onTextContentClickListener.onClickContent( viewHolder.mTvData,i);
                    }
                }
            });

        } else {
            viewHolder.mTvData.setText(bean.tweetContent);
        }
        /*viewHolder.mTvData.post(new Runnable() {
            @Override
            public void run() {
                if (viewHolder.mTvData.getLineCount() > 3) {
                    int length = viewHolder.mTvData.getText().length();
                    int lineEndIndex = viewHolder.mTvData.getLayout().getLineEnd(3); //设置第六行打省略号
                    String text = viewHolder.mTvData.getText().toString().subSequence(0, lineEndIndex - 1) + "...";
                    viewHolder.mTvData.setText(text);
                    LogUtils.w("getClickText--", viewHolder.mTvData.getLineCount() + "--" + viewHolder.mTvData.length() + "--" + viewHolder.mTvData.getText()+"--"+lineEndIndex);
                }
            }
        });*/

        UIUtils.setCommunityCount(viewHolder.mTvLikeNum, bean.praiseCount);
        UIUtils.setCommunityCount(viewHolder.mTvComment, bean.commentCount);
        UIUtils.setCommunityCount(viewHolder.mTvShare, bean.shareCount);
        if (bean.hasPraise == 1) {
            viewHolder.mTvLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like, 0, 0, 0);
        } else {
            viewHolder.mTvLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_no_like, 0, 0, 0);
        }
        if (bean.hasFollow == 1) {
            viewHolder.mTvAttention.setVisibility(View.GONE);
        } else {
            viewHolder.mTvAttention.setVisibility(View.VISIBLE);
        }
        viewHolder.mGroupImageData.setVisibility(View.VISIBLE);

        if (viewHolder.mGroupImageData.getChildCount() > 0) {
            viewHolder.mGroupImageData.removeAllViews();
        }
        if (bean.imageUrl != null) {
            String[] imagePathArray = bean.imageUrl.split(",");
            int imageCount = DataUtils.splitImagePathCount(bean.imageUrl);
            switch (imageCount) {
                case 0:
                    viewHolder.mGroupImageData.setVisibility(View.GONE);
                    break;
                case 1:
                    View oneImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_1, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_1_1 = oneImageInflate.findViewById(R.id.riv_child_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_1_1);
                    viewHolder.mGroupImageData.addView(oneImageInflate);
                    break;
                case 2:
                    View twoImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_2, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_2_1 = twoImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_2_2 = twoImageInflate.findViewById(R.id.riv_child_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_2_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_2_2);
                    viewHolder.mGroupImageData.addView(twoImageInflate);
                    break;
                case 3:
                    View threeImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_3, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_3_1 = threeImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_3_2 = threeImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_3_3 = threeImageInflate.findViewById(R.id.riv_child_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_3_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_3_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_3_3);
                    viewHolder.mGroupImageData.addView(threeImageInflate);
                    break;
                case 4:
                    View fourImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_4, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_4_1 = fourImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_4_2 = fourImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_4_3 = fourImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_4_4 = fourImageInflate.findViewById(R.id.riv_child_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_4_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_4_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_4_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_4_4);
                    viewHolder.mGroupImageData.addView(fourImageInflate);
                    break;
                case 5:
                    View fiveImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_5, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_5_1 = fiveImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_5_2 = fiveImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_5_3 = fiveImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_5_4 = fiveImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_5_5 = fiveImageInflate.findViewById(R.id.riv_child_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_5_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_5_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_5_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_5_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], civ_5_5);
                    viewHolder.mGroupImageData.addView(fiveImageInflate);
                    break;
                case 6:
                    View sixImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_6, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_6_1 = sixImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_6_2 = sixImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_6_3 = sixImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_6_4 = sixImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_6_5 = sixImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_6_6 = sixImageInflate.findViewById(R.id.riv_child_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_6_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_6_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_6_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_6_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], civ_6_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], civ_6_6);
                    viewHolder.mGroupImageData.addView(sixImageInflate);
                    break;
                case 7:
                    View sevenImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_7, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_7_1 = sevenImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_7_2 = sevenImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_7_3 = sevenImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_7_4 = sevenImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_7_5 = sevenImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_7_6 = sevenImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_7_7 = sevenImageInflate.findViewById(R.id.riv_child_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_7_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_7_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_7_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_7_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], civ_7_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], civ_7_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], civ_7_7);
                    viewHolder.mGroupImageData.addView(sevenImageInflate);
                    break;
                case 8:
                    View eightImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_8, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_8_1 = eightImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_8_2 = eightImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_8_3 = eightImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_8_4 = eightImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_8_5 = eightImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_8_6 = eightImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_8_7 = eightImageInflate.findViewById(R.id.riv_child_7);
                    RoundedImageView civ_8_8 = eightImageInflate.findViewById(R.id.riv_child_8);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_8_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_8_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_8_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_8_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], civ_8_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], civ_8_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], civ_8_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], civ_8_8);
                    viewHolder.mGroupImageData.addView(eightImageInflate);
                    break;
                case 9:
                    View nineImageInflate = LayoutInflater.from(mContext).inflate(R.layout.item_community_child_image_8, viewHolder.mGroupImageData, false);
                    RoundedImageView civ_9_1 = nineImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_9_2 = nineImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_9_3 = nineImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_9_4 = nineImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_9_5 = nineImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_9_6 = nineImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_9_7 = nineImageInflate.findViewById(R.id.riv_child_7);
                    RoundedImageView civ_9_8 = nineImageInflate.findViewById(R.id.riv_child_8);
                    RoundedImageView civ_9_9 = nineImageInflate.findViewById(R.id.riv_child_9);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], civ_9_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], civ_9_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], civ_9_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], civ_9_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], civ_9_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], civ_9_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], civ_9_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], civ_9_8);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[8], civ_9_9);
                    viewHolder.mGroupImageData.addView(nineImageInflate);
                    break;
                default:
                    break;
            }
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
            mTvData.setEllipsize(TextUtils.TruncateAt.END);
            mTvData.setMaxLines(3);
            mGroupImageData = itemView.findViewById(R.id.ll_iv_data);
            mLlLike = itemView.findViewById(R.id.ll_like);
            mTvLikeNum = itemView.findViewById(R.id.tv_like_num);
            mLlComment = itemView.findViewById(R.id.ll_comment);
            mTvComment = itemView.findViewById(R.id.tv_comment);
            mLlShare = itemView.findViewById(R.id.ll_share);
            mTvShare = itemView.findViewById(R.id.tv_share);
        }
    }

    public interface OnTextContentClickListener {
        void onClickContent(View view, int position);

        void onClickTopic(View view, int position);
    }
}

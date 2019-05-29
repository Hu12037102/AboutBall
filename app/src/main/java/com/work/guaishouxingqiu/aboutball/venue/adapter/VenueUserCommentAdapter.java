package com.work.guaishouxingqiu.aboutball.venue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;
import com.work.guaishouxingqiu.aboutball.weight.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 17:23
 * 更新时间: 2019/4/17 17:23
 * 描述:用户评论Adapter
 */
public class VenueUserCommentAdapter extends RecyclerView.Adapter<VenueUserCommentAdapter.ViewHolder> {
    private Context mContext;
    private List<ResultVenueDetailsBean.OrderCommentForAreaSimpleList> mData;
    private int mCommentCount;

    public VenueUserCommentAdapter(Context context, List<ResultVenueDetailsBean.OrderCommentForAreaSimpleList> data, int commentCount) {
        this.mContext = context;
        this.mData = data;
        this.mCommentCount = commentCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venue_details_comment_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVenueDetailsBean.OrderCommentForAreaSimpleList bean = mData.get(i);
        GlideManger.get().loadHeadImage(mContext, bean.headerImg, viewHolder.mCivHead);
        viewHolder.mTvName.setText(bean.nickName);
        viewHolder.mTvTime.setText(bean.commentTime);
        viewHolder.mEtvContent.setText(bean.commentContent);
        viewHolder.mRbGradle.setRating(Float.valueOf(bean.grade));

        if (i == 2) {
            viewHolder.mTvAllComment.setVisibility(View.VISIBLE);
            viewHolder.mTvAllComment.setText(UIUtils.getString(R.string.read_all_comment_s, mCommentCount));
        } else {
            viewHolder.mTvAllComment.setVisibility(View.GONE);
        }
        /*if (i == mData.size() -1){
            viewHolder.mLine.setVisibility(View.GONE);
        }else {
            viewHolder.mLine.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvTime;
        private RatingBar mRbGradle;
        private ExpandableTextView mEtvContent;
        private TextView mTvAllComment;
        private View mLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mRbGradle = itemView.findViewById(R.id.rb_gradle);
            mEtvContent = itemView.findViewById(R.id.etv_content);
            mTvAllComment = itemView.findViewById(R.id.tv_all_comment);
            mLine = itemView.findViewById(R.id.line);

        }
    }
}

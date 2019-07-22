package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.AddImageBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/13 13:53
 * 更新时间: 2019/5/13 13:53
 * 描述:意见反馈适配器
 */
public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {
    public List<AddImageBean> mData;
    private Context mContext;
    public static final int MAX_IMAGE_COUNT = 9;

    public AddImageAdapter(Context context, List<AddImageBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    private OnItemClickListener onItemClickListener;

    public void setOnDeleteClickListener(OnItemClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    private OnItemClickListener onDeleteClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_add_image_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AddImageBean bean = mData.get(i);
        if (bean.isVideo) {

            viewHolder.mIvVideo.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIvVideo.setVisibility(View.GONE);
        }
        if (bean.isAdd) {

            viewHolder.itemView.setEnabled(true);
            viewHolder.mCivDelete.setVisibility(View.GONE);
            viewHolder.itemView.setBackgroundResource(bean.resIcon);
            viewHolder.mRivData.setImageDrawable(null);
            //  GlideManger.get().loadImage(mContext, bean.resIcon, viewHolder.mRivData);

        } else {
            viewHolder.itemView.setBackground(null);
            viewHolder.itemView.setEnabled(false);
            viewHolder.mCivDelete.setVisibility(View.VISIBLE);
            GlideManger.get().loadImage(mContext, bean.path, viewHolder.mRivData);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickItem(v, i);
            }
        });
        viewHolder.mCivDelete.setOnClickListener(v -> {

            if (onDeleteClickListener != null) {
                onDeleteClickListener.onClickItem(v, i);
            }
            mData.remove(i);
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : hasVideo() ? 1
                : (mData.size() > MAX_IMAGE_COUNT ? MAX_IMAGE_COUNT : mData.size());
    }

    private boolean hasVideo() {
        for (AddImageBean bean : mData) {
            if (bean.isVideo) {
                return true;
            }
        }
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivData;
        private CircleImageView mCivDelete;
        private ImageView mIvVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivData = itemView.findViewById(R.id.riv_data);
            mCivDelete = itemView.findViewById(R.id.civ_delete);
            mIvVideo = itemView.findViewById(R.id.iv_video);
            itemView.post(() -> {
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.height = itemView.getWidth();
                LogUtils.w("LayoutParams--", layoutParams.height + "--" + layoutParams.width);
                itemView.setLayoutParams(layoutParams);
            });

        }
    }

}

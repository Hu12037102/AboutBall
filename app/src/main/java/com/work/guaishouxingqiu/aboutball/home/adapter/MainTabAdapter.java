package com.work.guaishouxingqiu.aboutball.home.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.activity.MainActivity;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;
import com.work.guaishouxingqiu.aboutball.other.SellingPointsEvent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 11:22
 * 更新时间: 2019/3/6 11:22
 * 描述: 主页面底部Tab
 */
public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.ViewHolder> {

    public List<MainTabBean> mData;
    private int mLastIndex;

    public void setOnCheckTabListener(OnCheckTabListener onCheckTabListener) {
        this.onCheckTabListener = onCheckTabListener;
    }

    public void setSelectorTab(int position) {
        if (mData != null && mData.size() > position) {
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).isChecked = i == position;
            }
        }
        notifyDataSetChanged();
    }

    private OnCheckTabListener onCheckTabListener;


    public MainTabAdapter(@NonNull List<MainTabBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_tab_view, viewGroup, false), mData.size());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvName.setText(mData.get(i).mTabName);
        viewHolder.mTvName.setTextColor(mData.get(i).isChecked ? ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_2)
                : ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_3));
        viewHolder.mLavPlay.setImageResource(mData.get(i).isChecked ? mData.get(i).mCheckResIcon : mData.get(i).mDefaultResIcon);
        if (mData.get(i).showRedPoint) {
            viewHolder.mIvRed.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIvRed.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (!mData.get(i).isChecked) {
                for (int j = 0; j < mData.size(); j++) {
                    mData.get(j).isChecked = mData.get(j).equals(mData.get(i));
                    if (i == j) {
                        viewHolder.mTvName.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_2));
                    }
                }
                notifyItemChanged(mLastIndex);
                mLastIndex = i;
                switch (i) {
                    case 0:
                        viewHolder.mLavPlay.setAnimation("home_tab_1.json");
                        break;
                    case 1:
                        viewHolder.mLavPlay.setAnimation("home_tab_2.json");
                        break;
                    case 2:
                        viewHolder.mLavPlay.setAnimation("home_tab_3.json");
                        break;
                    case 3:
                        viewHolder.mLavPlay.setAnimation("home_tab_4.json");
                        break;
                    case 4:
                        viewHolder.mLavPlay.setAnimation("home_tab_5.json");
                        break;
                    default:
                        break;
                }
                viewHolder.mLavPlay.playAnimation();
                viewHolder.mLavPlay.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        LogUtils.w("addAnimatorListener--", "onAnimationStart--");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LogUtils.w("addAnimatorListener--", "onAnimationEnd--");
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        LogUtils.w("addAnimatorListener--", "onAnimationCancel--");
                       // notifyItemChanged(i);
                        //notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        LogUtils.w("addAnimatorListener--", "onAnimationRepeat--");
                    }
                });

                if (onCheckTabListener != null) {
                    onCheckTabListener.onCheckTab(v, i);
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LottieAnimationView mLavPlay;
        private TextView mTvName;
        private ImageView mIvRed;

        ViewHolder(@NonNull View itemView, int tabSize) {
            super(itemView);
            initView(itemView, tabSize);
        }

        private void initView(View itemView, int tabSize) {
            mLavPlay = itemView.findViewById(R.id.lav_tab);
            mTvName = itemView.findViewById(R.id.tv_name);
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.width = ScreenUtils.getScreenWidth(itemView.getContext()) / tabSize;
            itemView.setLayoutParams(layoutParams);
            mIvRed = itemView.findViewById(R.id.iv_red);
        }
    }

    public interface OnCheckTabListener {
        void onCheckTab(View view, int position);
    }
}

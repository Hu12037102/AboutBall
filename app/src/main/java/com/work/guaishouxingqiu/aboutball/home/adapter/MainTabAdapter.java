package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;
import com.work.guaishouxingqiu.aboutball.util.ScreenUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 11:22
 * 更新时间: 2019/3/6 11:22
 * 描述: 主页面底部Tab
 */
public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.ViewHolder> {

    public List<MainTabBean> mData;

    public void setOnCheckTabListener(OnCheckTabListener onCheckTabListener) {
        this.onCheckTabListener = onCheckTabListener;
    }

    private OnCheckTabListener onCheckTabListener;


    public MainTabAdapter(@NonNull List<MainTabBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_tab_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvName.setText(mData.get(i).mTabName);
        viewHolder.mTvName.setTextColor(mData.get(i).isChecked ? ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_2)
                : ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.color_3));
        viewHolder.mIvTab.setImageResource(mData.get(i).isChecked ? mData.get(i).mCheckResIcon : mData.get(i).mDefaultResIcon);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!mData.get(i).isChecked) {
                for (MainTabBean bean : mData) {
                    bean.isChecked = bean.equals(mData.get(i));
                }
                notifyDataSetChanged();
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

        private ImageView mIvTab;
        private TextView mTvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvTab = itemView.findViewById(R.id.iv_tab);
            mTvName = itemView.findViewById(R.id.tv_name);
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.width = ScreenUtils.getScreenWidth(itemView.getContext()) / 5;
            itemView.setLayoutParams(layoutParams);
        }
    }

    public interface OnCheckTabListener {
        void onCheckTab(View view, int position);
    }
}

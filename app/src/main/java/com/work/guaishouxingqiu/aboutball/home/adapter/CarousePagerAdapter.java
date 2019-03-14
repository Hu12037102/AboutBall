package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 9:39
 * 更新时间: 2019/3/13 9:39
 * 描述: 自动轮播Adapter
 */
public class CarousePagerAdapter extends PagerAdapter {
    private int mChildCount;
    public List<ResultRecommendDataBean.Banner> mData;

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return PagerAdapter.POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mChildCount = getCount();
    }

    public CarousePagerAdapter(@NonNull List<ResultRecommendDataBean.Banner> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_recommend_banner_view, container, false);
        RoundedImageView rivBanner = inflate.findViewById(R.id.riv_banner);
        GlideManger.get().loadImage(container.getContext(), mData.get(position % mData.size()).coverUrl,
                R.mipmap.icon_defaulr_banner, R.mipmap.icon_defaulr_banner,
                rivBanner);
        TextView mTvTitle = inflate.findViewById(R.id.tv_title);
        mTvTitle.setText(mData.get(position % mData.size()).title);
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

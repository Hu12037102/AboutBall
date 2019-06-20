package com.work.guaishouxingqiu.aboutball.community.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 17:08
 * 更新时间: 2019/6/13 17:08
 * 描述:推挤热门头部pager
 */
public class CommunityRecommendPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<ResultRecommendHotBean> mData;
    private int mChildCount;

    public CommunityRecommendPagerAdapter(@NonNull Context context, @NonNull List<ResultRecommendHotBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        View inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_community_recommend_hot_view, container, false);
        RoundedImageView rivContent = inflateView.findViewById(R.id.riv_content);
        int selectorPosition = position % mData.size();
        GlideManger.get().loadBannerImage(mContext, mData.get(selectorPosition).imageUrl, rivContent);
        container.addView(inflateView);
        inflateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_TOPIC_DYNAMICS, ARouterConfig.Key.PARCELABLE, mData.get(selectorPosition));
            }
        });
        return inflateView;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount > 0) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }
}

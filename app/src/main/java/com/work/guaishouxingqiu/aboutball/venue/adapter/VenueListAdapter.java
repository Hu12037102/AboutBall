package com.work.guaishouxingqiu.aboutball.venue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 15:01
 * 更新时间: 2019/3/18 15:01
 * 描述:场馆列表适配器
 */
public class VenueListAdapter extends BaseRecyclerAdapter<VenueListAdapter.ViewHolder, List<ResultVenueData>> {

    public VenueListAdapter(@NonNull List<ResultVenueData> data) {
        super(data);
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue_data_view, viewGroup, false));
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultVenueData bean = mData.get(i);
        viewHolder.mTvName.setText(bean.stadiumName);
        GlideManger.get().loadImage(viewHolder.itemView.getContext(), bean.photoUrl,
                R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item,
                viewHolder.mRivData);
        viewHolder.mTvGrade.setText(String.valueOf(bean.grade).concat("分"));
        viewHolder.mRbGrade.setRating(bean.grade);
        viewHolder.mTvDistance.setText(bean.location.concat("  ").concat(bean.distance).concat("km"));
        viewHolder.mTvAddress.setText(viewHolder.itemView.getContext().getString(R.string.address_to_where, bean.address));

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivData;
        private TextView mTvName;
        private TextView mTvGrade;
        private RatingBar mRbGrade;
        private TextView mTvDistance;
        private TextView mTvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivData = itemView.findViewById(R.id.riv_data);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvGrade = itemView.findViewById(R.id.tv_grade);
            mRbGrade = itemView.findViewById(R.id.rb_grade);
            mTvDistance = itemView.findViewById(R.id.tv_distance);
            mTvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}

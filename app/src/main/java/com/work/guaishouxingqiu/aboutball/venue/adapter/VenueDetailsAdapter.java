package com.work.guaishouxingqiu.aboutball.venue.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 9:24
 * 更新时间: 2019/4/17 9:24
 * 描述:场馆详情适配器
 */
public class VenueDetailsAdapter extends BaseRecyclerAdapter<VenueDetailsAdapter.ViewHolder,List<ResultVenueData>>{


    public VenueDetailsAdapter(@NonNull List<ResultVenueData> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

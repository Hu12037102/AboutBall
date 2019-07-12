package com.work.guaishouxingqiu.aboutball.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 15:52
 * 更新时间: 2019/4/9 15:52
 * 描述: 资讯搜索Adapter
 */
public class NewsSearchAdapter extends BaseRecyclerAdapter<NewsSearchAdapter.ViewHolder, List<ResultNewsBean>> {

    private String mSearchText;

    public NewsSearchAdapter(@NonNull List<ResultNewsBean> data, String searchText) {
        super(data);
        this.mSearchText = searchText;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void notifyData(String searchText) {
        this.mSearchText = searchText;
        this.notifyDataSetChanged();

    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultNewsBean bean = mData.get(i);
        if (!DataUtils.isEmpty(mSearchText) && bean.title.contains(mSearchText)) {
            viewHolder.mTvContent.setText(SpanUtils.getTextColor(R.color.color_2, bean.title.indexOf(mSearchText), bean.title.indexOf(mSearchText) + mSearchText.length(), bean.title));
        }else {
            viewHolder.mTvContent.setText(bean.title);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

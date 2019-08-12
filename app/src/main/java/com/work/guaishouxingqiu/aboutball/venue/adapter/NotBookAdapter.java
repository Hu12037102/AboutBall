package com.work.guaishouxingqiu.aboutball.venue.adapter;

import android.nfc.FormatException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultNotBookBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/9 17:36
 * 更新时间: 2019/8/9 17:36
 * 描述: 发起约球（未完成约球）
 */
public class NotBookAdapter extends BaseRecyclerAdapter<NotBookAdapter.ViewHolder, List<ResultNotBookBean>> {

    public NotBookAdapter(@NonNull List<ResultNotBookBean> data) {
        super(data, false);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultNotBookBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTime, DateUtils.getNextCountData(bean.startTime, 0)
                + " " + DateUtils.getStartTime2EndTimeForHourMinute(bean.startTime, bean.endTime));
        UIUtils.setText(viewHolder.mTvName, bean.hostTeamName);
        viewHolder.mCbBook.setChecked(bean.isCheck);
        viewHolder.itemView.setOnClickListener(v -> {
            if (!bean.isCheck) {
                for (int j = 0; j < mData.size(); j++) {
                    if (i == j) {
                        bean.isCheck = true;
                    } else {
                        mData.get(j).isCheck = false;
                    }
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, i);
                }
                notifyDataSetChanged();
            }
        });

    }

    public int getSelectorPosition() {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isCheck) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_not_book_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private CheckBox mCbBook;
        private TextView mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvName = itemView.findViewById(R.id.tv_name);
            mCbBook = itemView.findViewById(R.id.cb_book);
        }
    }
}

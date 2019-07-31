package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyMessageBean;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/31 15:29
 * 更新时间: 2019/7/31 15:29
 * 描述:我的消息适配器
 */
public class MyMessageAdapter extends BaseRecyclerAdapter<MyMessageAdapter.ViewHolder, List<ResultMyMessageBean>> {

    private List<ResultRedPointInfoBean> mRedInfoData;

    public MyMessageAdapter(@NonNull List<ResultMyMessageBean> data) {
        super(data);
    }


    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyMessageBean bean = mData.get(i);
        UIUtils.setText(viewHolder.mTvTime, bean.createTime);
        UIUtils.setText(viewHolder.mTvTitle, bean.noticeTypeName);
        UIUtils.setText(viewHolder.mTvContent, bean.noticeTitle);
        switch (bean.noticeType) {
            case 1:
                viewHolder.mIvHead.setImageResource(R.mipmap.icon_my_message_1);
                break;
            case 2:
                viewHolder.mIvHead.setImageResource(R.mipmap.icon_my_message_2);
                break;
            case 3:
                viewHolder.mIvHead.setImageResource(R.mipmap.icon_my_message_3);
                break;
            case 4:
                viewHolder.mIvHead.setImageResource(R.mipmap.icon_my_message_4);
                break;
            case 5:
                viewHolder.mIvHead.setImageResource(R.mipmap.icon_my_message_5);
                break;
            default:
                break;
        }
        viewHolder.mTvCount.setVisibility(View.GONE);
        checkoutRedPoint(i, viewHolder.mTvCount);

    }

    private void checkoutRedPoint(int position, TextView textView) {
        UserManger userManger = UserManger.get();
        String redPointJson = userManger.getRedPointJson();
        if (!DataUtils.isEmpty(redPointJson)) {
            List<ResultRedPointInfoBean> redPointData = DataUtils.jsonToListRedPoint(redPointJson);
            for (ResultRedPointInfoBean bean : redPointData) {
                if (bean.noticeType == mData.get(position).noticeType) {
                    textView.setText(bean.unReadNum + "");
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public boolean isShowRedPoint() {
        UserManger userManger = UserManger.get();
        String redPointJson = userManger.getRedPointJson();
        if (!DataUtils.isEmpty(redPointJson)) {
            List<ResultRedPointInfoBean> redPointData = DataUtils.jsonToListRedPoint(redPointJson);
            return redPointData != null && redPointData.size() > 0 && userManger.isLogin();
        }
        return false;
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_message_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvHead;
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvContent;
        private TextView mTvCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvHead = itemView.findViewById(R.id.iv_head);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvCount = itemView.findViewById(R.id.tv_count);
        }
    }
}

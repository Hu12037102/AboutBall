package com.ifeell.app.aboutball.good.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.good.bean.ResultMyGoodBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 16:05
 * 更新时间: 2019/9/19 16:05
 * 描述:我的订单适配器
 */
public class MyGoodAdapter extends BaseRecyclerAdapter<MyGoodAdapter.ViewHolder, List<ResultMyGoodBean>> {
    private boolean mHasMore;

    public void setOnClickMyGoodListener(OnClickMyGoodListener onClickMyGoodListener) {
        this.onClickMyGoodListener = onClickMyGoodListener;
    }

    private OnClickMyGoodListener onClickMyGoodListener;

    public MyGoodAdapter(@NonNull List<ResultMyGoodBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultMyGoodBean bean = mData.get(i);
        GlideManger.get().loadBannerImage(mContext, bean.image, viewHolder.mRivContent);
        UIUtils.setText(viewHolder.mTvContent, bean.title);
        UIUtils.setGoodStatusText(viewHolder.mTvStatus, viewHolder.mTvOperation, bean.status);
        if (!mHasMore && i == mData.size() - 1) {
            viewHolder.mFootView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mFootView.setVisibility(View.GONE);
        }
        viewHolder.mClDetails.setOnClickListener(v -> {
            if (onClickMyGoodListener != null) {
                onClickMyGoodListener.onClickDetails(v, i);
            }
        });
        viewHolder.mTvOperation.setOnClickListener(v -> {
            if (onClickMyGoodListener != null) {
                onClickMyGoodListener.onClickOperation(v, i);
            }
        });
        if(bean.status == Contast.MyGoodStatus.WAIT_PAY){
            viewHolder.mTvOperation.setBackgroundResource(R.drawable.shape_stroke_color_2_view);
            UIUtils.setTextColor(viewHolder.mTvOperation,R.color.color_2);
        }else {
            viewHolder.mTvOperation.setBackgroundResource(R.drawable.shape_black_line_view);
            UIUtils.setTextColor(viewHolder.mTvOperation,R.color.color_4);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_good_view, viewGroup, false));
    }

    public void notifyData(boolean hasMore) {
        this.mHasMore = hasMore;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivContent;
        private TextView mTvStatus;
        private TextView mTvContent;
        private TextView mTvOperation;
        private View mFootView;
        private View mClDetails;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivContent = itemView.findViewById(R.id.riv_content);
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvOperation = itemView.findViewById(R.id.tv_operation);
            mFootView = itemView.findViewById(R.id.include_foot);
            mClDetails = itemView.findViewById(R.id.cl_details);
        }
    }

    public interface OnClickMyGoodListener {
        void onClickDetails(View view, int position);

        void onClickOperation(View view, int position);
    }
}

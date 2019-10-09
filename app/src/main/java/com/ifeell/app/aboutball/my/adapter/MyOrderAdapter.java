package com.ifeell.app.aboutball.my.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultMyOrderBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.SpanUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 13:26
 * 更新时间: 2019/5/14 13:26
 * 描述:我的订单适配器
 */
public class MyOrderAdapter extends BaseRecyclerAdapter<MyOrderAdapter.ViewHolder, List<ResultMyOrderBean>> {
    public void setOnItemClickListenerTv1(com.ifeell.app.aboutball.OnItemClickListener onItemClickListenerTv1) {
        this.onItemClickListenerTv1 = onItemClickListenerTv1;
    }

    public void setOnItemClickListenerTv2(com.ifeell.app.aboutball.OnItemClickListener onItemClickListenerTv2) {
        this.onItemClickListenerTv2 = onItemClickListenerTv2;
    }

    private com.ifeell.app.aboutball.OnItemClickListener onItemClickListenerTv1;
    private com.ifeell.app.aboutball.OnItemClickListener onItemClickListenerTv2;

    public MyOrderAdapter(@NonNull List<ResultMyOrderBean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i == mData.size() - 1) {
            viewHolder.mLineBottomRoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mLineBottomRoot.setVisibility(View.GONE);
        }
        ResultMyOrderBean bean = mData.get(i);
        GlideManger.get().loadImage(mContext, bean.photoUrl, viewHolder.mRivContent);
        UIUtils.setText(viewHolder.mTvName, bean.stadiumName);
        UIUtils.setText(viewHolder.mTvStatus, bean.stateName);
        //预定日期
        String timeHost = UIUtils.getString(R.string.order_item_time_host);
        UIUtils.setText(viewHolder.mTvTime, SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, timeHost.length(), UIUtils.getString(R.string.s_s, timeHost, bean.orderTime)));
        //项目地址
        String addressHost = UIUtils.getString(R.string.order_item_address_host);
        String addressContent = "";

        List<ResultMyOrderBean.DetailsOrder> orderDetailsData = mData.get(i).orderDetailForOrders;
        if (orderDetailsData != null && orderDetailsData.size() > 0) {
            for (ResultMyOrderBean.DetailsOrder details : orderDetailsData) {
                if (details == null) {
                    continue;
                }
                addressContent = addressContent.concat(DataUtils.getNotNullData(details.areaName)).concat(" ").concat(DataUtils.getNotNullData(details.calendar));
            }
        }
        UIUtils.setText(viewHolder.mTvAddress, SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, addressHost.length(), UIUtils.getString(R.string.s_s, addressHost, addressContent)));
        //总价
        String sumPrice = UIUtils.getString(R.string.order_item_price_host);
        UIUtils.setText(viewHolder.mTvSumPrices, SpanUtils.getTextColor(R.color.colorFFA6A6A6, 0, sumPrice.length(), UIUtils.getString(R.string.s_s, sumPrice, "￥" + bean.totalPrice)));
        viewHolder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_2));
        viewHolder.mRlBottom.setVisibility(View.VISIBLE);
        viewHolder.mTv1.setBackgroundResource(R.drawable.shape_black_line_view);
        viewHolder.mTv2.setBackgroundResource(R.drawable.shape_black_line_view);
        viewHolder.mTv1.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
        viewHolder.mTv2.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
        switch (bean.stateId) {
            //待付款
            case Contast.OrderStatus.WAIT_PAY:
                viewHolder.mTv1.setVisibility(View.VISIBLE);
                viewHolder.mTv1.setBackgroundResource(R.drawable.shape_stroke_color_2_view);
                viewHolder.mTv1.setTextColor(ContextCompat.getColor(mContext, R.color.color_2));
                viewHolder.mTv2.setVisibility(View.GONE);
                UIUtils.setText(viewHolder.mTv1, R.string.go_pay);
                break;
            //已取消
            case Contast.OrderStatus.CANCELED:
                viewHolder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFA6A6A6));
                viewHolder.mRlBottom.setVisibility(View.GONE);
                break;
            //待使用
            case Contast.OrderStatus.WAIT_USER:
                viewHolder.mTv1.setVisibility(View.VISIBLE);
                if (bean.orderType == Contast.OrderStatus.ORDER_STATUS_NOT_CANCEL) {
                    viewHolder.mTv2.setVisibility(View.GONE);
                } else {
                    viewHolder.mTv2.setVisibility(View.VISIBLE);
                }

                viewHolder.mTv1.setText(R.string.sure_user);
                viewHolder.mTv2.setText(R.string.application_for_drawback);
                break;
            //待评价
            case Contast.OrderStatus.WAIT_EVALUATE:
                viewHolder.mTv2.setVisibility(View.GONE);
                viewHolder.mTv1.setVisibility(View.VISIBLE);
                viewHolder.mTv1.setText(R.string.judge);
                break;
            //已完成
            case Contast.OrderStatus.COMPLETING:
                viewHolder.mRlBottom.setVisibility(View.GONE);
                break;
            //退款中
            case Contast.OrderStatus.REFUNDING:
                viewHolder.mTv1.setVisibility(View.VISIBLE);
                viewHolder.mTv2.setVisibility(View.GONE);
                viewHolder.mTv1.setText(R.string.refund_schedule);
                break;
            //已退款
            case Contast.OrderStatus.REFUNDED:
                viewHolder.mRlBottom.setVisibility(View.GONE);
                viewHolder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFA6A6A6));
                break;
            default:
                break;
        }
        viewHolder.mTv1.setOnClickListener(v -> {
            if (onItemClickListenerTv1 != null) {
                onItemClickListenerTv1.onClickItem(v, i);
            }
        });
        viewHolder.mTv2.setOnClickListener(v -> {
            if (onItemClickListenerTv2 != null) {
                onItemClickListenerTv2.onClickItem(v, i);
            }
        });

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_order_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivContent;
        private TextView mTvStatus;
        private TextView mTvName;
        private TextView mTvAddress;
        private TextView mTvTime;
        private TextView mTvSumPrices;
        private TextView mTvPayTime;
        private TextView mTv1;
        private TextView mTv2;
        private RelativeLayout mRlBottom;
        private View mClickView;
        private View mLineBottomRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivContent = itemView.findViewById(R.id.riv_content);
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvSumPrices = itemView.findViewById(R.id.tv_sum_prices);
            mTvPayTime = itemView.findViewById(R.id.tv_pay_time);
            mTv1 = itemView.findViewById(R.id.tv_1);
            mTv2 = itemView.findViewById(R.id.tv_2);
            mRlBottom = itemView.findViewById(R.id.rl_bottom);
            mClickView = itemView.findViewById(R.id.cl_top);
            mLineBottomRoot = itemView.findViewById(R.id.line_bottom_root);
        }
    }
}

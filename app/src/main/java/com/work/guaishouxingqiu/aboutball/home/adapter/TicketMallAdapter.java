package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultTicketMallBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 16:37
 * 更新时间: 2019/9/3 16:37
 * 描述:售票商城列表adapter
 */
public class TicketMallAdapter extends BaseRecyclerAdapter<TicketMallAdapter.ViewHolder, List<ResultTicketMallBean>> {

    private boolean mHasMore = true;

    public void setOnCardViewClickListener(TicketMallAdapter.onCardViewClickListener onCardViewClickListener) {
        this.onCardViewClickListener = onCardViewClickListener;
    }

    private onCardViewClickListener onCardViewClickListener;

    public TicketMallAdapter(@NonNull List<ResultTicketMallBean> data) {
        super(data);
    }

    public void notifyData(boolean hasMore) {
        this.mHasMore = hasMore;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultTicketMallBean bean = mData.get(i);
        //"内容类型: 1.比赛门票，2.泳票",
        switch (bean.contentType) {
            case 1:
                viewHolder.mClGame.setVisibility(View.VISIBLE);
                UIUtils.setText(viewHolder.mTvTime, DateUtils.getFormatDate(bean.startTime));
                UIUtils.setText(viewHolder.mTvHostName, bean.hostTeamName);
                UIUtils.setText(viewHolder.mTvGuestName, bean.guestTeamName);
                GlideManger.get().loadLogoImage(mContext, bean.hostLogoUrl, viewHolder.mCivHostLogo);
                GlideManger.get().loadLogoImage(mContext, bean.guestLogoUrl, viewHolder.mCivGuestLogo);
                break;
            case 2:
                viewHolder.mClGame.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        //"商品状态：1购票,2已售完,3未开售",
        switch (bean.saleStatus) {
            case 1:
                viewHolder.mMcvParent.setEnabled(true);
                viewHolder.mTvStatus.setBackgroundResource(R.drawable.shape_click_button);
                UIUtils.setText(viewHolder.mTvStatus, R.string.buy_ticket);
                break;
            case 2:
                viewHolder.mTvStatus.setBackgroundResource(R.drawable.shape_default_button);
                viewHolder.mMcvParent.setEnabled(false);
                UIUtils.setText(viewHolder.mTvStatus, R.string.out_of_print);
                break;
            case 3:
                viewHolder.mTvStatus.setBackgroundResource(R.drawable.shape_default_button);
                viewHolder.mMcvParent.setEnabled(false);
                UIUtils.setText(viewHolder.mTvStatus, R.string.not_of_print);
                break;
            default:
                break;
        }

        GlideManger.get().loadImage(mContext, bean.image, viewHolder.mRivBanner);

        UIUtils.setText(viewHolder.mTvTitle, bean.title);
        UIUtils.setText(viewHolder.mTvAddress, bean.subTitle);
        if (!mHasMore && mData.size() - 1 == i) {
            viewHolder.mIncludeFoot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIncludeFoot.setVisibility(View.GONE);
        }
        viewHolder.mMcvParent.setOnClickListener(v -> {
            if (onCardViewClickListener != null) {
                onCardViewClickListener.clickCard(v,i);
            }
        });
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ticket_mall_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mRivBanner;
        private TextView mTvTime;
        private TextView mTvVS;
        private CircleImageView mCivHostLogo;
        private CircleImageView mCivGuestLogo;
        private TextView mTvHostName;
        private TextView mTvGuestName;
        private TextView mTvTitle;
        private TextView mTvAddress;
        private TextView mTvStatus;
        private ConstraintLayout mClGame;
        private View mIncludeFoot;
        private MaterialCardView mMcvParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mRivBanner = itemView.findViewById(R.id.riv_banner);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvVS = itemView.findViewById(R.id.tv_vs);
            mCivHostLogo = itemView.findViewById(R.id.civ_host_logo);
            mCivGuestLogo = itemView.findViewById(R.id.civ_guest_logo);
            mTvHostName = itemView.findViewById(R.id.tv_host_name);
            mTvGuestName = itemView.findViewById(R.id.tv_guest_name);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mClGame = itemView.findViewById(R.id.cl_game);
            mIncludeFoot = itemView.findViewById(R.id.include_foot);
            mMcvParent = itemView.findViewById(R.id.mcv_parent);
        }
    }

    public interface onCardViewClickListener {
        void clickCard(@NonNull View view,int position);
    }
}

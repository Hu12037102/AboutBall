package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultInformationBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.GlideUtils;

import java.util.List;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 14:32
 * 更新时间: 2019/3/13 14:32
 * 描述: 首页推荐数据
 */
public class RecommendedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<ResultInformationBean>> {
    //纯文本内容
    public static final int TYPE_TEXT = 1000;
    //单图+文本
    public static final int TYPE_SING_IMAGE = 1001;
    //三图
    public static final int TYPE_THREE_IMAGE = 1003;
    private int mPosition;

    public RecommendedAdapter(@NonNull List<ResultInformationBean> data) {
        super(data);
    }

    @Override
    public int getItemViewType(int position) {
        this.mPosition = position;
        return super.getItemViewType(position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        int imagePathSize = DataUtils.splitImagePath(mData.get(mPosition).coverUrl);
        switch (imagePathSize) {
            case 0:
                viewHolder = new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_text_view, viewGroup, false));
                break;
            case 1:
                viewHolder = new SingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_sing_image, viewGroup, false));
                break;
            case 3:
                viewHolder = new ThreeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_three_image, viewGroup, false));
                break;
        }


        return viewHolder;
    }


    @Override
    protected void onBindViewDataHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
            textViewHolder.mTvData.setText(mData.get(i).title);
            textViewHolder.mTvFrom.setText(mData.get(i).releaseTime);

        } else if (viewHolder instanceof SingViewHolder) {
            SingViewHolder singViewHolder = (SingViewHolder) viewHolder;
            singViewHolder.mTvData.setText(mData.get(i).title);
            singViewHolder.mTvFrom.setText(mData.get(i).releaseTime);
          //  GlideUtils.loadImage();

        } else if (viewHolder instanceof ThreeViewHolder) {
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
        }

        //  viewHolder.getAdapterPosition();
    }


    static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvData, mTvFrom;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvData = itemView.findViewById(R.id.tv_data);
            mTvFrom = itemView.findViewById(R.id.tv_from);
        }
    }

    static class SingViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvData;
        private TextView mTvData;
        private TextView mTvFrom;
        private View mLine;

        public SingViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvData = itemView.findViewById(R.id.iv_data);
            mTvData = itemView.findViewById(R.id.tv_data);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
        }
    }

    static class ThreeViewHolder extends RecyclerView.ViewHolder {

        private FlowTextView mFtvData;
        private ImageView mIv1;
        private ImageView mIv2;
        private ImageView mIv3;
        private TextView mTvFrom;
        private View mLin;

        public ThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mFtvData = itemView.findViewById(R.id.ftv_data);
            mIv1 = itemView.findViewById(R.id.iv_1);
            mIv2 = itemView.findViewById(R.id.iv_2);
            mIv3 = itemView.findViewById(R.id.iv_3);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLin = itemView.findViewById(R.id.line);
        }
    }
}

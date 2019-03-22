package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 14:32
 * 更新时间: 2019/3/13 14:32
 * 描述: 首页推荐数据
 */
public class RecommendedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<ResultNewsBean>> {
    //纯文本内容
    public static final int TYPE_TEXT = 1000;
    //单图+文本
    public static final int TYPE_SING_IMAGE = 1001;
    //三图
    public static final int TYPE_THREE_IMAGE = 1003;
    private int mPosition;

    public RecommendedAdapter(@NonNull List<ResultNewsBean> data) {
        super(data);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        this.mPosition = position;
        return super.getItemViewType(position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (isHaveHeadView) {
            mPosition--;
        }
        int imagePathSize = DataUtils.splitImagePathCount(mData.get(mPosition).coverUrl);
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
            default:
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
            if (i == mData.size() - 1) {
                textViewHolder.mLine.setVisibility(View.GONE);
            } else {
                textViewHolder.mLine.setVisibility(View.VISIBLE);
            }
            textViewHolder.itemView.setOnClickListener(v -> {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(i).newsId);
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,i);
                }
            });

        } else if (viewHolder instanceof SingViewHolder) {
            SingViewHolder singViewHolder = (SingViewHolder) viewHolder;
            singViewHolder.mTvData.setText(mData.get(i).title);
            singViewHolder.mTvFrom.setText(mData.get(i).releaseTime);
            if (!DataUtils.isEmpty(mData.get(i).coverUrl)) {
                String imagePath;
                if (DataUtils.splitImagePathCount(mData.get(i).coverUrl) > 0) {
                    String[] imagePathArray = mData.get(i).coverUrl.split(",");
                    imagePath = imagePathArray[0];
                } else {
                    imagePath = mData.get(i).coverUrl;
                }
                GlideManger.get().loadImage(viewHolder.itemView.getContext(), imagePath,
                        R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, singViewHolder.mIvData);
            }
            if (i == mData.size() - 1) {
                singViewHolder.mLine.setVisibility(View.GONE);
            } else {
                singViewHolder.mLine.setVisibility(View.VISIBLE);
            }

            singViewHolder.itemView.setOnClickListener(v -> {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(i).newsId);
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,i);
                }
            });
        } else if (viewHolder instanceof ThreeViewHolder) {
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
            threeViewHolder.mFtvData.setText(mData.get(i).title);
            threeViewHolder.mTvFrom.setText(mData.get(i).releaseTime);
            if (DataUtils.splitImagePathCount(mData.get(i).coverUrl) == 3) {
                String[] imagePathArray = mData.get(i).coverUrl.split(",");
                GlideManger.get().loadImage(viewHolder.itemView.getContext(), imagePathArray[0],
                        R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv1);
                GlideManger.get().loadImage(viewHolder.itemView.getContext(), imagePathArray[1],
                        R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv2);
                GlideManger.get().loadImage(viewHolder.itemView.getContext(), imagePathArray[2],
                        R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv3);
            }
            if (i == mData.size() - 1) {
                threeViewHolder.mLine.setVisibility(View.GONE);
            } else {
                threeViewHolder.mLine.setVisibility(View.VISIBLE);
            }

            threeViewHolder.itemView.setOnClickListener(v -> {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(i).newsId);
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,i);
                }
            });
        }

        //  viewHolder.getAdapterPosition();
    }


    static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvData, mTvFrom;
        private View mLine;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvData = itemView.findViewById(R.id.tv_data);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
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
        private View mLine;

        public ThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mFtvData = itemView.findViewById(R.id.ftv_data);
            mFtvData.setTextSize(ScreenUtils.dp2px(itemView.getContext(), 17));
            mFtvData.setColor(ContextCompat.getColor(itemView.getContext(), R.color.color_4));
            mIv1 = itemView.findViewById(R.id.iv_1);
            mIv2 = itemView.findViewById(R.id.iv_2);
            mIv3 = itemView.findViewById(R.id.iv_3);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
        }
    }
}

package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 13:32
 * 更新时间: 2019/5/27 13:32
 * 描述:用户评价Adapter
 */
public class PostEvaluationAdapter extends BaseRecyclerAdapter<PostEvaluationAdapter.ViewHolder, List<ResultInputEvaluationBean>> {
    private int mFlag;

    public PostEvaluationAdapter(@NonNull List<ResultInputEvaluationBean> data, int flag) {
        super(data);
        this.mFlag = flag;
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultInputEvaluationBean bean = mData.get(i);
        switch (mFlag) {
            case Contast.InputEvaluationType.REFEREE:
                GlideManger.get().loadHeadImage(mContext, bean.photoUrl, viewHolder.mCivHead);
                break;
            case Contast.InputEvaluationType.TEAMMATE:
            case Contast.InputEvaluationType.OPPONENT:
                GlideManger.get().loadHeadImage(mContext, bean.headerImg, viewHolder.mCivHead);
                break;
            default:
                break;
        }
        UIUtils.setText(viewHolder.mTvName, bean.nickName);
        UIUtils.setText(viewHolder.mTvTime, bean.commentTime);
        viewHolder.mEtvContent.setText(bean.commentContent);
        initImageContent(viewHolder, i);


    }

    private void initImageContent(ViewHolder viewHolder, int i) {
        ResultInputEvaluationBean bean = mData.get(i);
        int imageCount = DataUtils.splitImagePathCount(bean.commentPic);
        if (imageCount > 0) {
            viewHolder.mLlImages.setVisibility(View.VISIBLE);
            String[] imagePathArray = bean.commentPic.split(",");
            View inflateView = null;
            switch (imagePathArray.length) {
                case 1:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_1, viewHolder.mLlImages, false);
                    RoundedImageView riv1_1 = inflateView.findViewById(R.id.riv_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv1_1);
                    break;
                case 2:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_2, viewHolder.mLlImages, false);
                    RoundedImageView riv2_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv2_2 = inflateView.findViewById(R.id.riv_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv2_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv2_2);
                    break;
                case 3:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_3, viewHolder.mLlImages, false);
                    RoundedImageView riv3_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv3_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv3_3 = inflateView.findViewById(R.id.riv_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv3_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv3_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv3_3);
                    break;
                case 4:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_4, viewHolder.mLlImages, false);
                    RoundedImageView riv4_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv4_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv4_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv4_4 = inflateView.findViewById(R.id.riv_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv4_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv4_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv4_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv4_4);
                    break;
                case 5:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_5, viewHolder.mLlImages, false);
                    RoundedImageView riv5_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv5_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv5_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv5_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv5_5 = inflateView.findViewById(R.id.riv_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv5_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv5_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv5_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv5_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv5_5);
                    break;
                case 6:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_6, viewHolder.mLlImages, false);
                    RoundedImageView riv6_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv6_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv6_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv6_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv6_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv6_6 = inflateView.findViewById(R.id.riv_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv6_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv6_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv6_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv6_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv6_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv6_6);
                    break;
                case 7:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_7, viewHolder.mLlImages, false);
                    RoundedImageView riv7_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv7_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv7_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv7_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv7_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv7_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv7_7 = inflateView.findViewById(R.id.riv_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv7_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv7_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv7_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv7_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv7_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv7_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv7_7);
                    break;
                case 8:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_8, viewHolder.mLlImages, false);
                    RoundedImageView riv8_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv8_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv8_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv8_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv8_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv8_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv8_7 = inflateView.findViewById(R.id.riv_7);
                    RoundedImageView riv8_8 = inflateView.findViewById(R.id.riv_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv8_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv8_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv8_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv8_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv8_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv8_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv8_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], riv8_8);
                    break;
                case 9:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_9, viewHolder.mLlImages, false);
                    RoundedImageView riv9_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv9_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv9_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv9_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv9_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv9_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv9_7 = inflateView.findViewById(R.id.riv_7);
                    RoundedImageView riv9_8 = inflateView.findViewById(R.id.riv_8);
                    RoundedImageView riv9_9 = inflateView.findViewById(R.id.riv_9);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv9_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv9_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv9_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv9_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv9_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv9_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv9_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], riv9_8);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[8], riv9_9);
                    break;
                default:
                    break;

            }
            viewHolder.mLlImages.addView(inflateView);
        }else {
            viewHolder.mLlImages.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_input_evaluation_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvTime;
        private ExpandableTextView mEtvContent;
        private LinearLayout mLlImages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mEtvContent = itemView.findViewById(R.id.rtv_content);
            mLlImages = itemView.findViewById(R.id.ll_images);
        }
    }
}

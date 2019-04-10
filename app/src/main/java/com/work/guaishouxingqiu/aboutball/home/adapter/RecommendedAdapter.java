package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;
import retrofit2.http.PUT;
import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 14:32
 * 更新时间: 2019/3/13 14:32
 * 描述: 首页推荐数据,当集合下标为4和9的时候，分别添加场馆和约球item
 */
public class RecommendedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<ResultNewsBean>> {
    //纯文本内容
    public static final int TYPE_TEXT = 1000;
    //单图+文本
    public static final int TYPE_SING_IMAGE = 1001;
    //三图
    public static final int TYPE_THREE_IMAGE = 1003;
    public static final int POSITION_VENUE_ITEM = 4;
    public static final int POSITION_BALL_ITEM = 9;
    private int mPosition;

    public RecommendedAdapter(@NonNull List<ResultNewsBean> data) {
        super(data);
        init();
    }

    private void init() {

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
        LogUtils.w("onCreateDataViewHolder--", mPosition + "--");
        // LogUtils.w("onCreateViewHolder---", mPosition + "--" + mData.get(mPosition).coverImgType + "--" + mData.get(mPosition).typeId);
        if (mPosition == RecommendedAdapter.POSITION_VENUE_ITEM) {
            //场馆ViewHolder
            viewHolder = new OtherHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_view, viewGroup, false));
        } else if (mPosition == RecommendedAdapter.POSITION_BALL_ITEM) {
            //约球ViewHolder 记得删除item_test_view
            viewHolder = new OtherHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_view, viewGroup, false));
        } else {
            if (mData.get(mPosition).typeId.equals(Contast.VIDEO_RECOMMENDED_TYPE)) {
                viewHolder = new VideoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_video_view, viewGroup, false));

            } else {
                String imageType = mData.get(mPosition).coverImgType;
                switch (imageType) {
                    case "0":
                        viewHolder = new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_text_view, viewGroup, false));
                        break;
                    case "1":
                        viewHolder = new SingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_sing_image, viewGroup, false));
                        break;
                    case "3":
                        viewHolder = new ThreeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_three_image, viewGroup, false));
                        break;
                    default:
                        break;
                }
            }
        }

        return viewHolder;
    }


    @Override
    protected void onBindViewDataHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ResultNewsBean bean = mData.get(i);
        viewHolder.setIsRecyclable(false);
        if (viewHolder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
            textViewHolder.mTvData.setText(mData.get(i).title);
            textViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
            if (i == mData.size() - 1) {
                textViewHolder.mLine.setVisibility(View.INVISIBLE);
            } else {
                textViewHolder.mLine.setVisibility(View.VISIBLE);
            }
            /*textViewHolder.itemView.setOnClickListener(v -> {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(i).newsId);
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,i);
                }
            });*/

        } else if (viewHolder instanceof SingViewHolder) {
            SingViewHolder singViewHolder = (SingViewHolder) viewHolder;
            singViewHolder.mTvData.setText(mData.get(i).title);
            singViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
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
                singViewHolder.mLine.setVisibility(View.INVISIBLE);
            } else {
                singViewHolder.mLine.setVisibility(View.VISIBLE);
            }

            /*singViewHolder.itemView.setOnClickListener(v -> {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(i).newsId);
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,i);
                }
            });*/
        } else if (viewHolder instanceof ThreeViewHolder) {
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
            threeViewHolder.mFtvData.setText(mData.get(i).title);
            threeViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
            if (mData.get(i).coverUrl != null) {
                String[] images = mData.get(i).coverUrl.split(",");
                for (int j = 0; j < images.length; j++) {
                    switch (j) {
                        case 0:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[0],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv1);
                            break;
                        case 1:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[1],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv2);
                            break;
                        case 2:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[2],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv3);
                            break;
                    }
                }
            }

            if (i == mData.size() - 1) {
                threeViewHolder.mLine.setVisibility(View.INVISIBLE);
            } else {
                threeViewHolder.mLine.setVisibility(View.VISIBLE);
            }


        } else if (viewHolder instanceof VideoHolder) {
            VideoHolder videoHolder = (VideoHolder) viewHolder;
            videoHolder.mTvContent.setText(mData.get(i).title);
            videoHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
           /* GlideManger.get().loadImageDrawable(mData.get(i).coverUrl, new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    LogUtils.w("loadImageDrawable--", resource + "--");
                    mVideoHolder.mTvVideo.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    mVideoHolder.mTvVideo.setBackground(placeholder);
                }
            });*/
            GlideManger.get().loadImage(mContext, mData.get(i).coverUrl, R.mipmap.icon_default_banner,
                    R.mipmap.icon_default_banner, videoHolder.mTvVideo);


        }

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

    static class VideoHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private RoundedImageView mTvVideo;
        private ImageView mIvPlay;
        private TextView mTvFrom;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvVideo = itemView.findViewById(R.id.tx_video);
            mIvPlay = itemView.findViewById(R.id.iv_play);
            mTvFrom = itemView.findViewById(R.id.tv_from);
        }
    }

    static class OtherHolder extends RecyclerView.ViewHolder {

        public OtherHolder(@NonNull View itemView) {
            super(itemView);
            RecyclerView recyclerView = itemView.findViewById(R.id.rv_data);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setAdapter(new TestAdapter());
        }
    }

    static class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
        private List<String> mData;

        public TestAdapter() {
            mData = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                mData.add(i + "");
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_not_more, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}

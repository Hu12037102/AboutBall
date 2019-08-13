package com.work.guaishouxingqiu.aboutball.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.item.util.ScreenUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.media.adapter.PreviewAdapter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 16:11
 * 更新时间: 2019/6/17 16:11
 * 描述:图片预览页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_IMAGE_PREVIEW)
public class PreviewImageActivity extends BaseActivity {
    @BindView(R.id.bvp_image)
    BaseViewPager mBvpImage;
    @BindView(R.id.rg_count)
    RadioGroup mRgCount;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    private int mPosition;
    private PreviewPagerAdapter mPreviewAdapter;
    private List<String> mMediaData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_image;
    }

    @Override
    public void initPermission() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.there_are_no_previews_image);
            finish();
            return;
        }
        mPosition = bundle.getInt(ARouterConfig.Key.POSITION);
        mMediaData = bundle.getStringArrayList(ARouterConfig.Key.ARRAY_LIST_STRING);

        if (mPosition < 0 || mMediaData == null || mMediaData.size() == 0) {
            UIUtils.showToast(R.string.there_are_no_previews_image);
            finish();
            return;
        }

        if (mPosition >= mMediaData.size()) {
            mPosition = 0;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        initRadioButton();
        mPreviewAdapter = new PreviewPagerAdapter(this, mMediaData);
        mBvpImage.setAdapter(mPreviewAdapter);
        mBvpImage.setPageTransformer(true, new PreviewAdapter.PreviewPageTransformer());
        mPreviewAdapter.setOnPreviewClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                finish();
            }
        });
        mBvpImage.setCurrentItem(mPosition, true);
    }

    private void initRadioButton() {
        if (mMediaData.size() <= 1) {
            mRgCount.setVisibility(View.GONE);
        } else {
            mRgCount.setVisibility(View.VISIBLE);
            for (int i = 0; i < mMediaData.size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                mRgCount.addView(radioButton);
                radioButton.setButtonDrawable(null);
                radioButton.setBackgroundResource(R.drawable.selector_image_radio_button);
                RadioGroup.LayoutParams layoutParams = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
                layoutParams.width = ScreenUtils.dp2px(this, 6);
                layoutParams.height = ScreenUtils.dp2px(this, 6);
                layoutParams.rightMargin = ScreenUtils.dp2px(this, 12);
                radioButton.setChecked(i == mPosition);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mPreviewAdapter.onRestart();
    }

    @Override
    protected void onPause() {
        mPreviewAdapter.onPause();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        mPreviewAdapter.onDestroy();
        super.onDestroy();

    }

    @Override
    protected void initEvent() {
        mBvpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton radioButton = (RadioButton) mRgCount.getChildAt(i);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideManger.get().downloadImage(mMediaData.get(mBvpImage.getCurrentItem()));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {

        return null;
    }

    static class PreviewPagerAdapter extends PagerAdapter {
        private List<String> mData;
        private Activity mActivity;
        private VideoView mVideoView;
        private Handler mVideoHandler;
        private static final int VIDEO_WHAT = 200;
        private SeekBar mSBSeek;
        private TextView mTVPlayingLength;
        private int mCurrentPosition;

        void setOnPreviewClickListener(OnItemClickListener onPreviewClickListener) {
            this.onPreviewClickListener = onPreviewClickListener;
        }

        private OnItemClickListener onPreviewClickListener;



        PreviewPagerAdapter(Activity activity, List<String> data) {
            this.mActivity = activity;
            this.mData = data;
            initVideoHandler();
        }

        private void initVideoHandler() {
            mVideoHandler = new Handler(msg -> {
                if (mVideoView != null) {
                    if (mTVPlayingLength != null) {
                        UIUtils.setText(mTVPlayingLength, DateUtils.getHourMinuteSecond(mVideoView.getCurrentPosition()));
                    }
                    if (mSBSeek != null) {
                        mSBSeek.setProgress(mVideoView.getCurrentPosition());
                    }
                    sendMessage();
                }
                return true;
            });
        }

        public void sendMessage() {
            mVideoHandler.sendEmptyMessageDelayed(PreviewPagerAdapter.VIDEO_WHAT, 200);
        }

        public void removeMessage() {
            mVideoHandler.removeMessages(PreviewPagerAdapter.VIDEO_WHAT, null);
        }

        public void onRestart() {
            if (mVideoView != null && !mVideoView.isPlaying()) {
                LogUtils.w("mVideoView--","onRestart--");
                mVideoView.start();
                mVideoView.seekTo(mCurrentPosition);
                sendMessage();
            }
        }

        public void onPause() {
            if (mVideoView != null&&mVideoView.isPlaying()) {
                LogUtils.w("mVideoView--","onPause--");
                removeMessage();
                mCurrentPosition = mVideoView.getCurrentPosition();
                mVideoView.pause();

            }
        }

        public void onDestroy() {
            removeMessage();
            if (mVideoView != null) {
                mVideoView.stopPlayback();
            }
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            String mediaUrl = mData.get(position);
            PreviewImageActivity previewImageActivity = (PreviewImageActivity) mActivity;
            if (DataUtils.isVideo(mediaUrl)) {

                previewImageActivity.mIvDownload.setVisibility(View.GONE);
                View inflateView = LayoutInflater.from(mActivity).inflate(R.layout.item_preview_video_view, container, false);
                mVideoView = inflateView.findViewById(R.id.vv_video);
                ImageView iVClose = inflateView.findViewById(R.id.iv_close);
                iVClose.setOnClickListener(v -> mActivity.finish());
                ImageView ivPlayStatus = inflateView.findViewById(R.id.iv_play_status);
                ivPlayStatus.setOnClickListener(v -> {
                });
                mTVPlayingLength = inflateView.findViewById(R.id.tv_playing_length);
                TextView tvVideoLength = inflateView.findViewById(R.id.tv_video_length);
                mSBSeek = inflateView.findViewById(R.id.sb_seek);
                SpinKitView sKVLoading = inflateView.findViewById(R.id.skv_loading);
                ConstraintLayout cLBottomController =inflateView.findViewById(R.id.cl_bottom_controller);
                mVideoView.setVideoPath(mediaUrl);
                mVideoView.suspend();
                mVideoView.setOnPreparedListener(mp -> {
                    cLBottomController.setVisibility(View.VISIBLE);
                    sKVLoading.setVisibility(View.GONE);
                    ivPlayStatus.setImageResource(R.mipmap.icon_preview_pause);
                    UIUtils.setText(mTVPlayingLength, "00:00");
                    UIUtils.setText(tvVideoLength, DateUtils.getHourMinuteSecond(mp.getDuration()));
                    mSBSeek.setMax(mp.getDuration());
                    sendMessage();
                    if (!mVideoView.isPlaying()) {
                        mVideoView.start();
                    }
                });
                mVideoView.setOnCompletionListener(mp -> {
                    ivPlayStatus.setImageResource(R.mipmap.icon_preview_play);
                    removeMessage();
                });
                mVideoView.setOnErrorListener((mp, what, extra) -> {
                    UIUtils.showToast(R.string.video_recorded_error);
                    mActivity.finish();
                    return true;
                });
                mSBSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mVideoView.seekTo(seekBar.getProgress());
                        UIUtils.setText(mTVPlayingLength, DateUtils.getHourMinuteSecond(mVideoView.getCurrentPosition()));
                    }
                });
                ivPlayStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mVideoView.isPlaying()) {
                            ivPlayStatus.setImageResource(R.mipmap.icon_preview_play);
                            mVideoView.pause();
                            removeMessage();
                        } else {
                            ivPlayStatus.setImageResource(R.mipmap.icon_preview_pause);
                            mVideoView.start();
                            sendMessage();
                        }
                    }
                });
                container.addView(inflateView);
                return inflateView;
            } else {
                previewImageActivity.mIvDownload.setVisibility(View.VISIBLE);
                PhotoView photoView = new PhotoView(mActivity);
                container.addView(photoView);
                ViewGroup.LayoutParams layoutParams = photoView.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                photoView.setLayoutParams(layoutParams);
                GlideManger.get().loadMediaImage(container.getContext(), mediaUrl, photoView, false);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPreviewClickListener != null) {
                            onPreviewClickListener.onClickItem(photoView, position);
                        }
                    }
                });
                return photoView;
            }

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }



}

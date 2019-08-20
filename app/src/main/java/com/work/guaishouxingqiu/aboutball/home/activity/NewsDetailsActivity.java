package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Handler;
import android.os.Message;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ShareWebBean;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsMessageAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.SellingPointsEvent;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseWebView;
import com.work.guaishouxingqiu.aboutball.weight.InputMessageDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 14:57
 * 更新时间: 2019/3/21 14:57
 * 描述:首页-资讯详情activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_NEW_DETAILS)
public class NewsDetailsActivity extends BaseWebActivity<NewDetailsPresenter> implements NewsDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_message_data)
    RecyclerView mRvMessage;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    @BindView(R.id.iv_video_status)
    ImageView mIvVideoStatus;
    @BindView(R.id.skv_loading)
    SpinKitView mSkvLoading;
    @BindView(R.id.tv_play_time)
    TextView mTvPlayTime;
    @BindView(R.id.tv_length_time)
    TextView mTvLengthTime;
    @BindView(R.id.sb_seek)
    SeekBar mSbSeek;
    @BindView(R.id.pb_play_length)
    ProgressBar mPbPlayLength;
    @BindView(R.id.cl_schedule)
    ConstraintLayout mClSchedule;
    @BindView(R.id.cl_video)
    ConstraintLayout mClVideo;
    @BindView(R.id.vv_video)
    VideoView mVideoView;
    private static final int VIDEO_WHAT = 100;

    private long mNewsId;
    private NewsMessageAdapter mAdapter;
    private List<ResultNewsMessageBean> mData;
    private InputMessageDialog mSendMessageDialog;
    private BaseWebView mWebView;
    private View mHeadView;
    private TextView mTvTitle;
    private TextView mTvFrom;
    private BaseDataBean<String> mContentBean;
    private boolean mIsVideoViewFocus;
    private Handler mVideoHandler;
    private boolean mVideoPrepared;//是否准备就绪

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_details;
    }

    @Override
    protected void initView() {
        initHeadView();
        super.initView();

        mNewsId = mIntent.getLongExtra(ARouterConfig.Key.NEW_DETAILS_ID, 0);
        mRvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRvMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initSellingPoint() {
        DataUtils.addSellingPoint(this, SellingPointsEvent.Key.A0102);
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_news_details_view, (ViewGroup) getWindow().getDecorView().getRootView(), false);
        mWebView = mHeadView.findViewById(R.id.bw_web);
        mTvTitle = mHeadView.findViewById(R.id.tv_title);
        mTvFrom = mHeadView.findViewById(R.id.tv_from);
        ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mWebView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new NewsMessageAdapter(mData);
        mAdapter.setNotDataView(R.mipmap.icon_not_data_message);
        mAdapter.setNotDataContentRes(R.string.not_message);
        mAdapter.addHeadView(mHeadView);
        mRvMessage.setAdapter(mAdapter);
        mPresenter.loadNewsContent(mNewsId);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlLayout.setOnLoadMoreListener(refreshLayout -> {

            mPresenter.isRefresh = false;
            mPresenter.loadMessage(mNewsId);
            refreshLayout.finishLoadMore();

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                mRvMessage.setVisibility(View.VISIBLE);
                mPresenter.loadMessage(mNewsId);
            }
        });


    }


    @Override
    protected NewDetailsPresenter createPresenter() {
        return new NewDetailsPresenter(this);
    }


    @OnClick({R.id.iv_send_message, R.id.tv_input_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_send_message:
                DataUtils.addSellingPoint(this, SellingPointsEvent.Key.A010202);
                ShareWebBean shareWebBean = new ShareWebBean();
                shareWebBean.webUrl = IApiService.H5.SHARE_NEWS_DETAILS + mNewsId;
                LogUtils.w("onViewClicked--", shareWebBean.webUrl + "\n" + mWebView.getTitle());
                shareWebBean.title = mContentBean.title;
                shareWebBean.description = "来源：" + mContentBean.source;
                showShareDialog(shareWebBean);
                break;
            case R.id.tv_input_message:
                DataUtils.addSellingPoint(this, SellingPointsEvent.Key.A010201);
                if (mSendMessageDialog == null) {
                    mSendMessageDialog = new InputMessageDialog(this);
                    mSendMessageDialog.setOnInputMessageListener(text -> {
                        RequestSendMessageBean bean = new RequestSendMessageBean();
                        bean.newsId = mNewsId;
                        bean.commentContent = text;
                        mPresenter.sendNewsMessage(bean);
                    });
                }
                if (!mSendMessageDialog.isShowing()) {
                    mSendMessageDialog.show();
                }
                break;
        }
    }

    private boolean isVideo(String content) {
        return !DataUtils.isEmpty(content) && content.endsWith(".mp4");
    }

    @Override
    public void resultNewsContent(BaseDataBean<String> dataBean) {
        if (dataBean.contentType == 1) {
            mClVideo.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            initVideo(dataBean.content);
        } else {
            mClVideo.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
        loadEditData(dataBean.content);
        mContentBean = dataBean;
        mTvTitle.setText(dataBean.title);
        mTvFrom.setText(UIUtils.getString(R.string.from_data, dataBean.source, dataBean.releaseTime));
        // mTitleView.mTvCenter.setText(dataBean.title);

    }

    private void setScheduleStatus() {
        if (mIsVideoViewFocus) {
            mClSchedule.setVisibility(View.VISIBLE);
            mPbPlayLength.setVisibility(View.GONE);
        } else {
            mClSchedule.setVisibility(View.GONE);
            mPbPlayLength.setVisibility(View.VISIBLE);

        }
    }

    private void setVideoStatus(boolean isPlay, boolean isShow) {
        if (isPlay) {
            mIvVideoStatus.setImageResource(R.mipmap.icon_video_pause);
        } else {
            mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
        }
        if (isShow) {
            mIvVideoStatus.setVisibility(View.VISIBLE);
        } else {
            mIvVideoStatus.setVisibility(View.GONE);
        }
    }

    private void setLoadingViewStatus(boolean isPlay) {
        if (isPlay) {
            mSkvLoading.setVisibility(View.GONE);
        } else {
            mSkvLoading.setVisibility(View.VISIBLE);
        }
    }

    private void sendVideoMessage() {
        mVideoHandler.sendEmptyMessageDelayed(VIDEO_WHAT, 200);
    }

    private void removeVideoMessage() {
        if (mVideoHandler != null) {
            mVideoHandler.removeMessages(VIDEO_WHAT);
        }
    }

    private void initVideo(String videoPath) {
        mVideoHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case VIDEO_WHAT:
                        mSbSeek.setProgress(mVideoView.getCurrentPosition());
                        mPbPlayLength.setProgress(mVideoView.getCurrentPosition());
                        mSbSeek.setSecondaryProgress(mVideoView.getBufferPercentage());
                        mPbPlayLength.setSecondaryProgress(mVideoView.getBufferPercentage());
                        UIUtils.setText(mTvPlayTime, DateUtils.getHourMinuteSecond(mVideoView.getCurrentPosition()));
                        UIUtils.setText(mTvLengthTime, DateUtils.getHourMinuteSecond(mVideoView.getDuration()));
                        LogUtils.w("handleMessage--", "--" + mVideoView.getCurrentPosition() + "--" + mVideoView.getDuration());
                        if (mSbSeek.getProgress() < mVideoView.getDuration() &&
                                mPbPlayLength.getProgress() < mVideoView.getDuration()) {
                            sendVideoMessage();
                        }
                        break;
                }
                return true;
            }
        });
        mVideoView.setVideoPath(videoPath);
        mVideoView.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(android.media.MediaPlayer mp) {
                mVideoPrepared = true;
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
                setVideoStatus(mp.isPlaying(), false);
                setLoadingViewStatus(mp.isPlaying());
                mSbSeek.setMax(mp.getDuration());
                mPbPlayLength.setMax(mp.getDuration());
                UIUtils.setText(mTvPlayTime, "00:00");
                UIUtils.setText(mTvLengthTime, DateUtils.getHourMinuteSecond(mp.getDuration()));
                mVideoHandler.sendEmptyMessageDelayed(VIDEO_WHAT, 200);
                LogUtils.w("mVideoView--", "准备完成" + mp.getDuration());
            }
        });
        mIvVideoStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideoStatus(mVideoView.isPlaying(), true);
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    removeVideoMessage();
                } else {
                    mVideoView.start();
                    sendVideoMessage();
                }
            }
        });
        mVideoView.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(android.media.MediaPlayer mp) {
                mVideoView.stopPlayback();
                setVideoStatus(false, true);
                LogUtils.w("mVideoView--", "完成");
            }
        });
        mVideoView.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
                LogUtils.w("mVideoView--", extra + "");
                return true;
            }
        });
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mVideoPrepared) {
                    return;
                }
                mIsVideoViewFocus = !mIsVideoViewFocus;
                setScheduleStatus();
                setVideoStatus(mVideoView.isPlaying(), mIsVideoViewFocus);
            }
        });
        mSbSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //  mVideoView.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVideoView.seekTo(seekBar.getProgress());
                //  mVideoView.resume();
            }
        });

    }

    @Override
    public void resultMessageData(List<ResultNewsMessageBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }

        mData.addAll(data);
        mSrlLayout.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void resultSendNewsMessage() {
        mPresenter.isRefresh = true;
        if (mSendMessageDialog != null) {
            mSendMessageDialog.clearEditData();
        }
        mPresenter.loadMessage(mNewsId);

    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return mPbLoading;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
            mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();

        }

    }

    @Override
    protected void onDestroy() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        removeVideoMessage();
        super.onDestroy();
    }
}

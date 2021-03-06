package com.ifeell.app.aboutball.game.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.example.item.util.ScreenUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.commonality.activity.LoginOrShareActivity;
import com.ifeell.app.aboutball.commonality.bean.ShareWebBean;
import com.ifeell.app.aboutball.game.bean.ResultGameSimpleBean;
import com.ifeell.app.aboutball.game.contract.GameDetailsContract;
import com.ifeell.app.aboutball.game.fragment.GameCommentFragment;
import com.ifeell.app.aboutball.game.fragment.GameDataFragment;
import com.ifeell.app.aboutball.game.fragment.GameLookBackFragment;
import com.ifeell.app.aboutball.game.fragment.GameResultFragment;
import com.ifeell.app.aboutball.game.presenter.GameDetailsPresenter;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.DateUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;
import com.ifeell.app.aboutball.weight.FocusableTextView;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.Toasts;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 15:11
 * 更新时间: 2019/3/22 15:11
 * 描述:比赛详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GAME_DETAILS)
public class GameDetailsActivity extends LoginOrShareActivity<GameDetailsPresenter>
        implements GameDetailsContract.View {
    @BindView(R.id.civ_left)
    CircleImageView mCivLeft;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.civ_right)
    CircleImageView mCivRight;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.ll_live_details)
    LinearLayout mLlLiveDetails;
    @BindView(R.id.cl_head_details)
    LinearLayout mClHeadDetails;
    @BindView(R.id.tb_data)
    TabLayout mTbData;
    @BindView(R.id.bv_data)
    BaseViewPager mBvData;
    @BindView(R.id.vs_live)
    ViewStub mVsLive;
    @BindView(R.id.cl_head_parent)
    ConstraintLayout mClHeadParent;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_body)
    ViewGroup mLlBody;
    @BindView(R.id.iv_share)
    ImageView mIvTitleShare;
    private FragmentPagerAdapter mPagerAdapter;
    private View mHeadLiveParent;
    private boolean mIconShowWindows = true;
    private static final int SEEK_WHAT = 101;
    private static final String RESET_TIME_LENGTH = "00:00";

    // String mLivePath = "http://5815.liveplay.myqcloud.com/live/5815_89aad37e06ff11e892905cb9018cf0d4_900.flv";
    //String mLivePath = "http://li.ifeell.com.cn/ipk/live.flv?auth_key=1555645522-0-0-87c9e12dfd362e4b3dd3698c826553f9";
    private AliyunVodPlayer mVideoPlay;
    private ImageView mIvLockVideo;
    private ImageView mIvVideoStatus;
    private Handler mVideoHandler;
    private Runnable mVideoRunnable;
    private ImageView mIvShare;
    private ImageView mIvScreen;
    private boolean mIsCanRotate = false;//默认可以旋转
    private SpinKitView mSkvLoading;
    private ResultGameSimpleBean mDataBean;
    private SeekBar mSbVideo;
    private Fragment[] mFragments;
    private boolean isLive;//是不是直播，默认false
    private View mViewTopStatus;
    private TextView mTvLengthTime;
    private TextView mTvPlayTime;
    private ProgressBar mPbVideo;
    private boolean isPortraitWindows = true;//是不是竖屏
    private ConstraintLayout mClSchedule;
    private boolean isLockVideos;
    private TextView mTvHasVideoStatus;
    private GameLookBackFragment mGameLookBackFragment;
    //private GameCollectionFragment mCollectionFragment;
    // String mLivePath = "http://player.alicdn.com/video/aliyunmedia.mp4";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_defails;
    }

    @Override
    protected void initStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    @Override
    protected void initView() {
        setHeadParentParamsScreen(false);

        mIvBack.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20) + ScreenUtils.getStatuWindowsHeight(this),
                ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20));
        mIvTitleShare.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20) + ScreenUtils.getStatuWindowsHeight(this),
                ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20));

    }

    private void setHeadParentParamsScreen(boolean isScreen) {
        ViewGroup.LayoutParams layoutParams = mClHeadParent.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = isScreen ? ViewGroup.LayoutParams.MATCH_PARENT : ScreenUtils.dp2px(this, 220) + ScreenUtils.getStatuWindowsHeight(this);
        mClHeadParent.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        int gameId = mIntent.getIntExtra(ARouterConfig.Key.GAME_ID, -1);
        if (gameId == -1) {
            Toasts.with().showToast(R.string.game_id_error);
            finish();
            return;
        }
        mPresenter.loadGameSimple(gameId);


    }


    private void initTabData() {
        if (mTbData.getTabCount() == 0) {
            String[] tabArray = getResources().getStringArray(R.array.game_details_tab_array);
            for (int i = 0; i < tabArray.length; i++) {
                //  mTbData.addTab(mTbData.newTab().setText(tabArray[i]), i == 0);
                UIUtils.setBaseCustomTabLayout(mTbData, tabArray[i], i == 0, 45);


            }
        }
        mTbData.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvData.setCurrentItem(tab.getPosition(), true);
                switch (tab.getPosition()) {
                    case 0:
                        DataUtils.addSellingPoint(GameDetailsActivity.this, SellingPointsEvent.Key.A0206);
                        break;
                    case 1:
                        DataUtils.addSellingPoint(GameDetailsActivity.this, SellingPointsEvent.Key.A0205);
                        break;
                    case 2:
                        DataUtils.addSellingPoint(GameDetailsActivity.this, SellingPointsEvent.Key.A0203);
                        break;
                    case 3:
                        DataUtils.addSellingPoint(GameDetailsActivity.this, SellingPointsEvent.Key.A0204);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //竖屏
        LogUtils.w("onConfigurationChanged--", newConfig.orientation + "--");
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setHeadParentParamsScreen(false);
            mLlBody.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (mIvLockVideo != null) {
                mIvLockVideo.setVisibility(View.GONE);
            }
            isPortraitWindows = true;
            //横屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setHeadParentParamsScreen(true);
            mLlBody.setVisibility(View.GONE);
            if (mIvLockVideo != null) {
                mIvLockVideo.setVisibility(View.VISIBLE);

            }

            isPortraitWindows = false;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlay != null) {
            mVideoPlay.pause();
            mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlay != null) {
            mVideoPlay.resume();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void initCollectionVideo(ResultGameSimpleBean bean, String videoUrl) {

        mClHeadDetails.setVisibility(View.GONE);
        mDataBean.liveAddress = videoUrl;
        isLive = false;
        if (mVideoPlay != null) {
            mSkvLoading.setVisibility(View.VISIBLE);
            if (mVideoPlay.isPlaying()) {
                mVideoPlay.stop();
                //   mVideoPlay.release();
            }
            mHeadLiveParent.setVisibility(View.VISIBLE);
            AliyunLocalSource.AliyunLocalSourceBuilder builder = new AliyunLocalSource.AliyunLocalSourceBuilder();
            builder.setSource(bean.liveAddress);
            builder.setCoverPath(bean.liveAddress);
            builder.setTitle(bean.liveAddress);
            // mVideoPlay.setAutoPlay(true);
            mVideoPlay.prepareAsync(builder.build());
        } else {
            initLiveVideoView(mDataBean);
        }
    }

    private void initPagerData(ResultGameSimpleBean bean) {
        mGameLookBackFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_LOOK_BACK, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
        mGameLookBackFragment.setOnClickLookBackListener(new GameLookBackFragment.OnClickLookBackListener() {
            @Override
            public void clickCollection(String videoUrl) {
                initCollectionVideo(bean, videoUrl);
            }

            @Override
            public void resultCollectionCount(int count) {
                if (mTvHasVideoStatus != null && !isLive) {
                    mTvHasVideoStatus.setVisibility(View.VISIBLE);
                    if (count > 0) {
                        mTvHasVideoStatus.setBackgroundResource(R.drawable.shape_watch_live_tv);
                    } else {
                        mTvHasVideoStatus.setBackground(null);
                    }
                }
            }

            @Override
            public void playCollectionVideo(String videoUrl) {
                if (videoUrl != null && !isLive) {
                    initCollectionVideo(bean, videoUrl);
                }
            }
        });
        GameCommentFragment commentFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COMMENT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
        GameResultFragment resultFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_RESULT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
        GameDataFragment dataFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_DATA, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
        /*mCollectionFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COLLECTION, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
        mCollectionFragment.setOnCollectionClickListener(new GameCollectionFragment.OnCollectionClickListener() {
            @Override
            public void clickCollection(String videoUrl) {
                initCollectionVideo(bean, videoUrl);
            }

            @Override
            public void resultVideo(String videoUrl) {
                if (videoUrl != null) {
                    initCollectionVideo(bean, videoUrl);
                }
            }

            @Override
            public void resultVideosCount(int count) {
                if (mTvHasVideoStatus != null && !isLive) {
                    mTvHasVideoStatus.setVisibility(View.VISIBLE);
                    if (count > 0) {
                        mTvHasVideoStatus.setBackgroundResource(R.drawable.shape_watch_live_tv);
                    } else {
                        mTvHasVideoStatus.setBackground(null);
                    }
                }

            }
        });*/
        mFragments = new Fragment[]{mGameLookBackFragment, commentFragment, resultFragment, dataFragment};
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments[i];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        };
        mBvData.setOffscreenPageLimit(mFragments.length);
        // mBvData.setCurrentItem(1,true);
        mBvData.setAdapter(mPagerAdapter);
        // mBvData.setCurrentItem(1, true);
        mBvData.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTbData.getTabAt(i)).select();
                // UIUtils.notifySelectorBaseTab(mTbData);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected GameDetailsPresenter createPresenter() {
        return new GameDetailsPresenter(this);
    }


    @Override
    public void resultGameSimple(@NonNull ResultGameSimpleBean bean) {
        mDataBean = bean;
        mIvTitleShare.setVisibility(View.VISIBLE);
        initTabData();
        initPagerData(bean);

        GlideManger.get().loadLogoImage(this, bean.hostLogoUrl, mCivLeft);
        GlideManger.get().loadLogoImage(this, bean.guestLogoUrl, mCivRight);
        mTvLeft.setText(bean.hostName);
        mTvRight.setText(bean.guestName);
        if (mLlLiveDetails.getChildCount() > 0) {
            mLlLiveDetails.removeAllViews();
        }
        switch (bean.stateId) {
            //比赛未开始
            case Contast.GAME_STATUS_NO_START:
                View noStartView = getLayoutInflater().inflate(R.layout.layout_live_no_start_view, mLlLiveDetails, false);
                mLlLiveDetails.addView(noStartView);
                TextView tvTime = noStartView.findViewById(R.id.tv_time);
                FocusableTextView tvStatus = noStartView.findViewById(R.id.tv_status);
                UIUtils.setText(tvStatus, bean.gameName);
                tvTime.setText(bean.startTime);
                break;
            //比赛进行中
            case Contast.GAME_STATUS_STARTING:
                //比赛结束
            case Contast.GAME_STATUS_FINISH:
                View startView = getLayoutInflater().inflate(R.layout.layout_watch_live_body_view, mLlLiveDetails, false);
                mLlLiveDetails.addView(startView);
                FocusableTextView tVTitle = startView.findViewById(R.id.tv_title);
                mTvHasVideoStatus = startView.findViewById(R.id.tv_status);
               /* if (bean.stateId == Contast.GAME_STATUS_STARTING) {
                    mTvHasVideoStatus.setText(R.string.watch_live);
                    isLive = true;
                    mTvHasVideoStatus.setVisibility(View.VISIBLE);
                } else {
                    //  mTvStatus.setText(R.string.watch_collection);
                    mTvHasVideoStatus.setText(bean.matchState);
                    isLive = false;
                    // mTvHasVideoStatus.setVisibility(View.GONE);
                }*/
                isLive = false;
                switch (bean.stateId) {
                    //直播中
                    case 2:
                        mTvHasVideoStatus.setText(R.string.watch_live);
                        mTvHasVideoStatus.setBackgroundResource(R.drawable.shape_watch_live_tv);
                        isLive = true;
                        break;
                    default:
                        mTvHasVideoStatus.setText(bean.matchState);
                        mTvHasVideoStatus.setBackgroundResource(0);
                        break;
                }
                if ("集锦/回放".equals(bean.matchState)) {
                    mTvHasVideoStatus.setText(R.string.watch_collection);
                    mTvHasVideoStatus.setBackgroundResource(R.drawable.shape_watch_live_tv);
                }


                tVTitle.setText(bean.gameName);
                TextView tVGrade = startView.findViewById(R.id.tv_grade);
                tVGrade.setText(bean.hostScore.concat(" - ").concat(bean.guestScore));
                mTvHasVideoStatus.setOnClickListener(v -> {
                    // ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_VIDEO);
                    if (bean.stateId == Contast.GAME_STATUS_STARTING) {
                        initLiveVideoView(bean);
                    } else {
                        //  mBvData.setCurrentItem(mFragments.length - 1);
                        //   mCollectionFragment.playCollectionVideo();
                        if (mGameLookBackFragment != null) {
                            mGameLookBackFragment.playCollectionVideo();
                        }
                    }

                });
                break;
        }
    }


    private void setVideoIconStatus(boolean iconShowVideo) {
        if (isLockVideos) {
            return;
        }
        if (iconShowVideo) {
            mIvBack.setVisibility(View.VISIBLE);
            mIvShare.setVisibility(View.VISIBLE);
            mIvScreen.setVisibility(View.VISIBLE);
            if (mVideoPlay != null && mVideoPlay.isPlaying()) {
                mIvVideoStatus.setVisibility(View.VISIBLE);
            }
            mPbVideo.setVisibility(View.GONE);
            mClSchedule.setVisibility(View.VISIBLE);
            //如果屏幕获取焦点且是横屏的情况下
            if (!isPortraitWindows) {
                mIvLockVideo.setVisibility(View.VISIBLE);
            } else {
                mIvLockVideo.setVisibility(View.GONE);
            }

        } else {
            mIvBack.setVisibility(View.GONE);
            mIvShare.setVisibility(View.GONE);
            mIvScreen.setVisibility(View.INVISIBLE);
            mIvVideoStatus.setVisibility(View.GONE);
            if (isPortraitWindows) {
                mPbVideo.setVisibility(View.VISIBLE);
            } else {
                mPbVideo.setVisibility(View.GONE);
            }
            mClSchedule.setVisibility(View.GONE);
        }
        if (isLive) {
            mClSchedule.setVisibility(View.GONE);
            mPbVideo.setVisibility(View.GONE);
        }

    }

    public void notifyLockVideoStatus() {
        mPbVideo.setVisibility(View.GONE);
        if (isLockVideos) {
            mIvLockVideo.setImageResource(R.mipmap.icon_video_lock);
            mIvBack.setVisibility(View.GONE);
            mIvShare.setVisibility(View.GONE);
            mIvScreen.setVisibility(View.INVISIBLE);
            mIvVideoStatus.setVisibility(View.GONE);
            mClSchedule.setVisibility(View.GONE);
        } else {
            mIvLockVideo.setImageResource(R.mipmap.icon_video_unlock);
            mIvBack.setVisibility(View.VISIBLE);
            mIvShare.setVisibility(View.VISIBLE);
            mIvScreen.setVisibility(View.VISIBLE);
            mClSchedule.setVisibility(View.VISIBLE);
            if (mVideoPlay != null && mVideoPlay.isPlaying()) {
                mIvVideoStatus.setVisibility(View.VISIBLE);
            }
        }
    }


    private void initLiveVideoView(ResultGameSimpleBean bean) {

        mClHeadDetails.setVisibility(View.GONE);
        if (mHeadLiveParent == null) {
            mIvTitleShare.setVisibility(View.GONE);
            mHeadLiveParent = mVsLive.inflate();
            mIvShare = mHeadLiveParent.findViewById(R.id.iv_share_video);
            mIvScreen = mHeadLiveParent.findViewById(R.id.iv_screen);
            mIvLockVideo = mHeadLiveParent.findViewById(R.id.iv_lock);
            mIvVideoStatus = mHeadLiveParent.findViewById(R.id.iv_video_status);
            mSkvLoading = mHeadLiveParent.findViewById(R.id.skv_loading);
            mSbVideo = mHeadLiveParent.findViewById(R.id.sb_seek);
            mViewTopStatus = mHeadLiveParent.findViewById(R.id.top_status_view);
            mTvPlayTime = mHeadLiveParent.findViewById(R.id.tv_play_time);
            mPbVideo = mHeadLiveParent.findViewById(R.id.pb_play_length);

            mTvLengthTime = mHeadLiveParent.findViewById(R.id.tv_length_time);

            ConstraintLayout.LayoutParams topStatusParams = (ConstraintLayout.LayoutParams) mViewTopStatus.getLayoutParams();
            topStatusParams.height = ScreenUtils.dp2px(this, 50) + ScreenUtils.getStatuWindowsHeight(this);
            topStatusParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mViewTopStatus.setLayoutParams(topStatusParams);
            mClSchedule = mHeadLiveParent.findViewById(R.id.cl_schedule);
            if (isLive) {
                mPbVideo.setVisibility(View.GONE);
            } else {
                mPbVideo.setVisibility(View.VISIBLE);
            }
            mIvVideoStatus.setVisibility(View.GONE);
            mIvLockVideo.setVisibility(View.GONE);
            SurfaceView svVideo = mHeadLiveParent.findViewById(R.id.sv_video);
            svVideo.setClickable(false);
            ViewGroup.LayoutParams layoutParams = svVideo.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            svVideo.setLayoutParams(layoutParams);
            mIvScreen.setOnClickListener(v -> {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                mClSchedule.setVisibility(View.VISIBLE);
                mPbVideo.setVisibility(View.GONE);
            });
            mIvShare.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20) + ScreenUtils.getStatuWindowsHeight(this),
                    ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20));
            mIvShare.setOnClickListener(v -> {
                ShareWebBean webBean = DataUtils.resultShareGameVideo(bean.matchId, bean.hostName, bean.guestName);
                showShareDialog(webBean);
            });

            mVideoPlay = new AliyunVodPlayer(this);
            mVideoRunnable = () -> {
                mIconShowWindows = !mIconShowWindows;
                setVideoIconStatus(mIconShowWindows);
                svVideo.setClickable(true);
            };
            mVideoHandler = new Handler(msg -> {
                switch (msg.what) {
                    case SEEK_WHAT:
                        mSbVideo.setProgress((int) mVideoPlay.getCurrentPosition());
                        mPbVideo.setProgress((int) mVideoPlay.getCurrentPosition());
                        mSbVideo.setSecondaryProgress(mVideoPlay.getBufferingPosition());
                        mPbVideo.setSecondaryProgress(mVideoPlay.getBufferingPosition());
                        mVideoHandler.sendEmptyMessageDelayed(SEEK_WHAT, 100);
                        mTvPlayTime.setText(DateUtils.getHourMinuteSecond((int) mVideoPlay.getCurrentPosition()));
                        break;
                }
                return true;
            });

            mSbVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    LogUtils.w("mSbVideo--", progress + "--");
                    // mVideoPlay.seekTo(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_pause);
                    mVideoHandler.removeMessages(SEEK_WHAT, null);
                    if (mVideoPlay.isPlaying()) {
                        mVideoPlay.pause();
                    } else if (mVideoPlay.getPlayerState() == IAliyunVodPlayer.PlayerState.Stopped) {
                        mVideoPlay.start();
                        mVideoPlay.pause();
                    } else if (mVideoPlay.getPlayerState() == IAliyunVodPlayer.PlayerState.Completed) {
                        mVideoPlay.replay();
                        mVideoPlay.pause();
                    }
                    LogUtils.w("mSbVideo---", mSbVideo.getProgress() + "--");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    LogUtils.w("mSbVideo----", mSbVideo.getProgress() + "--" + mSbVideo.getProgress());
                    mVideoPlay.seekTo(mSbVideo.getProgress());
                    mSbVideo.setProgress(mSbVideo.getProgress());
                    mPbVideo.setProgress(mSbVideo.getProgress());
                    //   mIvLockVideo.setVisibility(View.VISIBLE);
                }
            });
            mIvLockVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isLockVideos = !isLockVideos;
                    notifyLockVideoStatus();
                    /*if (isLockVideos){
                        mIvLockVideo.setImageResource(R.mipmap.icon_video_lock);
                        mIvBack.setVisibility(View.GONE);
                        mIvShare.setVisibility(View.GONE);
                        mIvScreen.setVisibility(View.INVISIBLE);
                        mIvVideoStatus.setVisibility(View.GONE);
                        mPbVideo.setVisibility(View.GONE);
                    }else {
                        mIvLockVideo.setImageResource(R.mipmap.icon_video_unlock);
                        mIvBack.setVisibility(View.VISIBLE);
                        mIvShare.setVisibility(View.VISIBLE);
                        mIvScreen.setVisibility(View.VISIBLE);
                        mPbVideo.setVisibility(View.VISIBLE);
                        if (mVideoPlay != null && mVideoPlay.isPlaying()) {
                            mIvVideoStatus.setVisibility(View.VISIBLE);
                        }
                    }*/
                }
            });

            svVideo.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mVideoPlay.setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    mVideoPlay.surfaceChanged();
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
            svVideo.setOnClickListener(v -> {
                mIconShowWindows = !mIconShowWindows;
                setVideoIconStatus(mIconShowWindows);

            });
            AliyunLocalSource.AliyunLocalSourceBuilder builder = new AliyunLocalSource.AliyunLocalSourceBuilder();
            builder.setSource(bean.liveAddress);
            builder.setCoverPath(bean.liveAddress);
            builder.setTitle(bean.liveAddress);
            // mVideoPlay.setAutoPlay(true);
            mVideoPlay.prepareAsync(builder.build());
            mVideoPlay.setOnPreparedListener(() -> {
                mTvLengthTime.setText(DateUtils.getHourMinuteSecond((int) mVideoPlay.getDuration()));
                mVideoPlay.start();
            });
            /**
             * 首帧显示
             */
            mVideoPlay.setOnFirstFrameStartListener(() -> {
                mSkvLoading.setVisibility(View.GONE);
                //  mIvVideoStatus.setVisibility(View.VISIBLE);
                mIvVideoStatus.setImageResource(R.mipmap.icon_video_pause);
                //mSbVideo.setVisibility(View.VISIBLE);
                mSbVideo.setMax((int) mVideoPlay.getDuration());
                mPbVideo.setMax((int) mVideoPlay.getDuration());
                mVideoHandler.postDelayed(mVideoRunnable, 5000);
                mVideoHandler.sendEmptyMessage(SEEK_WHAT);
            });
            mVideoPlay.setOnSeekCompleteListener(() -> {
                //  mIvLockVideo.setVisibility(View.GONE);
                if (mVideoPlay.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused) {
                    mVideoPlay.resume();
                }
                mVideoHandler.sendEmptyMessage(SEEK_WHAT);

            });
            /**
             * 播放器错误
             */
            mVideoPlay.setOnErrorListener((i, i1, s) -> {
                //  mVideoPlay.stop();
                mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
                HintDialog hintDialog = new HintDialog.Builder(GameDetailsActivity.this)
                        .setTitle(R.string.hint)
                        .setBody(bean.stateId == Contast.GAME_STATUS_STARTING ? R.string.not_find_live : R.string.video_recorded_error)
                        .setSure(R.string.sure)
                        .setCancelTouchOut(false)
                        .builder();
                if (!this.isFinishing()) {
                    hintDialog.show();
                }
                hintDialog.setOnItemClickListener(view -> {
                    hintDialog.dismiss();
                    finish();
                });
                mTvPlayTime.setText(GameDetailsActivity.RESET_TIME_LENGTH);

                //Toasts.with().showToast(R.string.line_video_error);
            });
            mIvVideoStatus.setOnClickListener(v -> {
                if (mVideoPlay.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
                    mVideoPlay.pause();
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
                } else if (mVideoPlay.getPlayerState() == IAliyunVodPlayer.PlayerState.Completed) {
                    mVideoPlay.replay();
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_pause);
                } else {
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_pause);
                    mVideoPlay.start();
                }


            });
            mVideoPlay.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
                @Override
                public void onStopped() {
                    mSbVideo.setProgress(0);
                    //设置到缓存进度
                    mSbVideo.setSecondaryProgress(mVideoPlay.getBufferingPosition());
                    mIvVideoStatus.setVisibility(View.VISIBLE);
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
                }
            });
          /*  mIvLockVideo.setOnClickListener(v -> {
                mIsCanRotate = !mIsCanRotate;

                if (mIsCanRotate) {
                    mIvLockVideo.setImageResource(R.mipmap.icon_video_unlock);
                } else {
                    mIvLockVideo.setImageResource(R.mipmap.icon_video_lock);
                }

            });*/
            mVideoPlay.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
                @Override
                public void onCompletion() {
                    mIvVideoStatus.setVisibility(View.VISIBLE);
                    mIvVideoStatus.setImageResource(R.mipmap.icon_video_play);
                    mVideoHandler.removeMessages(SEEK_WHAT);
                    mSbVideo.setProgress(0);
                    //设置到缓存进度
                    mSbVideo.setSecondaryProgress(mVideoPlay.getBufferingPosition());
                    mTvPlayTime.setText(GameDetailsActivity.RESET_TIME_LENGTH);

                    mClHeadDetails.setVisibility(View.VISIBLE);
                    mHeadLiveParent.setVisibility(View.GONE);
                    mIvBack.setVisibility(View.VISIBLE);
                    mIvTitleShare.setVisibility(View.VISIBLE);
                }
            });

        } else {
            mHeadLiveParent.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                clickBack();
                break;
            case R.id.iv_share:
                ShareWebBean webBean = DataUtils.resultShareGameVideo(mDataBean.matchId, mDataBean.hostName, mDataBean.guestName);
                showShareDialog(webBean);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    private void clickBack() {
     /*   if (mRotateListener != null) {
            mRotateListener.disable();
        }
        if (mIvLockVideo != null) {
            mIvLockVideo.setImageResource(R.mipmap.icon_video_lock);
        }*/
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mClHeadDetails.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoPlay != null) {
            mVideoPlay.stop();
            mVideoPlay.release();

        }


        if (mVideoHandler != null && mVideoRunnable != null) {
            mVideoHandler.removeCallbacks(mVideoRunnable);
            mVideoHandler.removeMessages(SEEK_WHAT);
        }
    }

    /*public static class WindowsRotateListener extends OrientationEventListener {
        private Context mContext;

        public WindowsRotateListener(Context context) {
            super(context);
            this.mContext = context;
        }

        @Override
        public void onOrientationChanged(int orientation) {
            LogUtils.w("onOrientationChanged--", orientation + "--");
            int screenOrientation = mContext.getResources().getConfiguration().orientation;
            if (((orientation >= 0) && (orientation < 45)) || (orientation > 315)) {//设置竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            } else if (orientation > 225 && orientation < 315) { //设置横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else if (orientation > 45 && orientation < 135) {// 设置反向横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
            } else if (orientation > 135 && orientation < 225) {
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                }
            }
        }
    }*/

}

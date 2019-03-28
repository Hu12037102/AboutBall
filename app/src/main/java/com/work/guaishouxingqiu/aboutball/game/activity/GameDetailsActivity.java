package com.work.guaishouxingqiu.aboutball.game.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameCollectionFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameCommentFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameDataFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameResultFragment;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.FocusableTextView;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

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
public class GameDetailsActivity extends PermissionActivity<GameDetailsPresenter>
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
    ConstraintLayout mClHeadDetails;
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
    private FragmentPagerAdapter mPagerAdapter;
    private View mHeadLiveParent;
    private TXCloudVideoView mTxLiveVideo;
    private TXLivePlayer mLivePlay;
    String mLivePath = "http://5815.liveplay.myqcloud.com/live/5815_89aad37e06ff11e892905cb9018cf0d4_900.flv";

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
                mTbData.addTab(mTbData.newTab().setText(tabArray[i]), i == 0);
            }

            mTbData.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mBvData.setCurrentItem(tab.getPosition(), true);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //竖屏
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setHeadParentParamsScreen(false);
            mLlBody.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //横屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setHeadParentParamsScreen(true);
            mLlBody.setVisibility(View.GONE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mLivePlay != null) {
            mLivePlay.pause();
        }
        if (mTxLiveVideo != null) {
            mTxLiveVideo.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLivePlay != null) {
            mLivePlay.resume();
        }
        if (mTxLiveVideo != null) {
            mTxLiveVideo.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mTxLiveVideo != null) {
            mTxLiveVideo.onDestroy();
        }
        if (mLivePlay != null) {
            mLivePlay.stopPlay(true);
        }
        super.onDestroy();

    }


    private void initPagerData(ResultGameSimpleBean bean) {
        if (mPagerAdapter == null) {
            GameResultFragment resultFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_RESULT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameDataFragment dataFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_DATA, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameCommentFragment commentFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COMMENT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameCollectionFragment collectionFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COLLECTION, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            Fragment[] fragments = {resultFragment, dataFragment, commentFragment, collectionFragment};
            mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int i) {
                    return fragments[i];
                }

                @Override
                public int getCount() {
                    return fragments.length;
                }
            };
            mBvData.setOffscreenPageLimit(fragments.length);
            mBvData.setAdapter(mPagerAdapter);

            mBvData.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    DataUtils.checkData(mTbData.getTabAt(i)).select();
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
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
        initTabData();
        initPagerData(bean);

        GlideManger.get().loadImage(this, bean.hostLogoUrl, R.mipmap.icon_image_background,
                R.mipmap.icon_image_background, mCivLeft);
        GlideManger.get().loadImage(this, bean.guestLogoUrl, R.mipmap.icon_image_background,
                R.mipmap.icon_image_background, mCivRight);
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
                tvTime.setText(bean.startTime);
                break;
            //比赛进行中
            case Contast.GAME_STATUS_STARTING:
                //比赛结束
            case Contast.GAME_STATUS_FINISH:
                View startView = getLayoutInflater().inflate(R.layout.layout_watch_live_body_view, mLlLiveDetails, false);
                mLlLiveDetails.addView(startView);
                FocusableTextView tVTitle = startView.findViewById(R.id.tv_title);
                TextView mTvStatus = startView.findViewById(R.id.tv_status);
                if (bean.stateId == Contast.GAME_STATUS_STARTING) {
                    mTvStatus.setText(R.string.watch_live);
                } else {
                    mTvStatus.setText(R.string.watch_collection);
                }
                tVTitle.setText(bean.gameName);
                TextView tVGrade = startView.findViewById(R.id.tv_grade);
                tVGrade.setText(bean.hostScore.concat(" - ").concat(bean.guestScore));
                mTvStatus.setOnClickListener(v -> {
                    // ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_VIDEO);
                    initLiveVideoView();
                });
                break;


        }
    }


    private void initLiveVideoView() {
        mClHeadDetails.setVisibility(View.GONE);
        if (mHeadLiveParent == null) {
            mHeadLiveParent = mVsLive.inflate();
            mTxLiveVideo = mHeadLiveParent.findViewById(R.id.tx_live_video);
            ImageView mIvShare = mHeadLiveParent.findViewById(R.id.iv_share_video);
            ImageView ivScreen = mHeadLiveParent.findViewById(R.id.iv_screen);
            ImageView ivLock = mHeadLiveParent.findViewById(R.id.iv_lock);
            ivScreen.setOnClickListener(v -> {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            });
            mIvShare.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20) + ScreenUtils.getStatuWindowsHeight(this),
                    ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20));
            mLivePlay = new TXLivePlayer(this);
            mTxLiveVideo.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            //mTxLiveVideo.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            mTxLiveVideo.setRenderRotation(TXLiveConstants.RENDER_ROTATION_0);
            mLivePlay.setPlayerView(mTxLiveVideo);
            mLivePlay.setAutoPlay(true);
            mLivePlay.startPlay(mLivePath, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
            mTxLiveVideo.setOnClickListener(v -> {

            });
        } else {
            mHeadLiveParent.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                clickBack();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    private void clickBack() {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mClHeadDetails.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            super.onBackPressed();
        }
    }
}

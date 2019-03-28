package com.work.guaishouxingqiu.aboutball.game.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.liteav.txcvodplayer.TXCVodVideoView;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tencent.rtmp.TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 16:10
 * 更新时间: 2019/3/27 16:10
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GAME_VIDEO)
public class GamePlayActivity extends PermissionActivity {
    @BindView(R.id.tx_video)
    TXCloudVideoView mTxVideo;
    //   String mLivePath = "http://129.204.27.186:8080/live/test.flv";
    String mLivePath = "http://5815.liveplay.myqcloud.com/live/5815_89aad37e06ff11e892905cb9018cf0d4_900.flv";
    private TXLivePlayer mLivePlay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_play;
    }

    @Override
    protected void initView() {
        mLivePlay = new TXLivePlayer(this);
        mLivePlay.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        //  mLivePlay.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
        mLivePlay.setPlayerView(mTxVideo);
        TXLivePlayConfig config = new TXLivePlayConfig();

        mLivePlay.startPlay(mLivePath, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.w("GamePlayActivity--", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.w("GamePlayActivity--", "onStart");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.w("GamePlayActivity--", "newConfig-" + newConfig.orientation + "---" + Configuration.ORIENTATION_LANDSCAPE
                + "--" + Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLivePlay.pause();
        mTxVideo.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLivePlay.resume();
        mTxVideo.onResume();
    }

    @Override
    protected void onDestroy() {
        mTxVideo.onDestroy();
        mLivePlay.stopPlay(true);

        super.onDestroy();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        } else {
            super.onBackPressed();
        }
    }
}

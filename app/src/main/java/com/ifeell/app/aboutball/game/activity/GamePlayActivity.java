package com.ifeell.app.aboutball.game.activity;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;

public class GamePlayActivity extends BaseActivity {
   // String mLivePath = "http://player.alicdn.com/video/aliyunmedia.mp4";
    String mLivePath = "http://li.ifeell.com.cn/ipk/live.flv?auth_key=1555645522-0-0-87c9e12dfd362e4b3dd3698c826553f9";
    private AliVcMediaPlayer mVideoPlay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_play;
    }

    @Override
    protected void initView() {
       SurfaceView svVideo =  findViewById(R.id.sv_video);

        AliyunVodPlayer mAliyunVodPlayer = new AliyunVodPlayer(this);
        mAliyunVodPlayer.enableNativeLog();
        svVideo.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mAliyunVodPlayer.setDisplay(holder);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mAliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        AliyunLocalSource.AliyunLocalSourceBuilder builder = new AliyunLocalSource.AliyunLocalSourceBuilder();
        builder.setSource(mLivePath);
        builder.setCoverPath(mLivePath);
        builder.setTitle(mLivePath);
        mAliyunVodPlayer.setAutoPlay(true);
        mAliyunVodPlayer.prepareAsync(builder.build());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}

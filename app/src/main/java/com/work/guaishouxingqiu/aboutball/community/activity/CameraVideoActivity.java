package com.work.guaishouxingqiu.aboutball.community.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConfig.Path.ACTIVITY_CAMERA_VIDEO)
public class CameraVideoActivity extends PermissionActivity {
    @BindView(R.id.jcv_camera)
    JCameraView mJcvCamera;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_video;
    }

    @Override
    protected void initStatusColor() {
        //super.initStatusColor();
      /*  if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }*/

      /*  if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =     View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initPermission() {
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                CameraVideoActivity.super.initPermission();
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {
                initPermission();
            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {

            }
        }, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);

    }

    @Override
    protected void initView() {
        mJcvCamera.setSaveVideoPath(FileUtils.createVideoFolder().getAbsolutePath());
        mJcvCamera.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);
        mJcvCamera.setFeatures(JCameraView.BUTTON_STATE_BOTH);
      /* ViewGroup.LayoutParams layoutParams =  mJcvCamera.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = getResources().getDisplayMetrics().heightPixels;
        mJcvCamera.setLayoutParams(layoutParams);*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        mJcvCamera.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJcvCamera.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mJcvCamera.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mJcvCamera.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {

                GlideManger.get().bitmapToImage(bitmap, null, new GlideManger.OnDownLoadImageListener() {
                    @Override
                    public void onDownLoadSucceed(File file) {
                        LogUtils.w("mJcvCamera--", bitmap + "--" + file.getAbsolutePath());
                    }

                    @Override
                    public void onDownLoadFailed() {
                        Toasts.with().showToast(R.string.camera_shooting_ailed_please_try_again);
                    }
                });
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                FileUtils.scanImage(CameraVideoActivity.this,new File(url));
                LogUtils.w("mJcvCamera--", url + "--" + firstFrame);
            }
        });
        mJcvCamera.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
                LogUtils.w("initEvent--", "点击了leftView");
            }
        });
        mJcvCamera.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                LogUtils.w("initEvent--", "点击了rightView");
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}

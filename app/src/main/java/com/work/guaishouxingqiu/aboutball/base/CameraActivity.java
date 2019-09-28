package com.work.guaishouxingqiu.aboutball.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.activity.CameraVideoActivity;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.other.SellingPointsEvent;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.PhotoDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;


/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 13:34
 * 更新时间: 2019/3/21 13:34
 * 描述:
 */
public abstract class CameraActivity<P extends BasePresenter> extends PermissionActivity<P> {

    public static final int REQUEST_CODE_CHOOSE = 1000;
    private PhotoDialog mPhotoDialog;
    private static final int PICK_IMAGE_REQUEST_CODE = 100;
    private static final int OPEN_CAMERA_REQUEST_CODE = 101;
    private static final int REQUEST_CODE_VIDEO_CAMERA = 1750;
    private File mCameraFile;
    private MediaSelector.MediaOptions mMediaOptions;
    private static final int REQUEST_CODE_OPEN_SCAN_CODE=1578;//打开二维码




    protected void openScanCode() {
        DataUtils.addSellingPoint(this, SellingPointsEvent.Key.A0109);
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                startActivityForResult(new Intent(CameraActivity.this, CaptureActivity.class), CameraActivity.REQUEST_CODE_OPEN_SCAN_CODE);
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {
                HintDialog hintDialog = new HintDialog.Builder(CameraActivity.this)
                        .setTitle(R.string.hint)
                        .setBody(R.string.camera_is_must_permission)
                        .setSure(R.string.sure).builder();
                hintDialog.show();
                hintDialog.setOnItemClickListener(view -> {
                    hintDialog.dismiss();
                    openScanCode();
                });
            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {
                showForbidPermissionDialog();
            }
        }, Manifest.permission.CAMERA);

    }

    private void openCamera() {

        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                onCamera();
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {
                Toasts.with().showToast("相机为必须权限！");
            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {
                showForbidPermissionDialog();

            }
        }, Manifest.permission.CAMERA);
    }

    private void onCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            mCameraFile = FileUtils.resultImageCameraFile();
            Uri cameraUri = FileUtils.fileToUri(this, mCameraFile, cameraIntent);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            startActivityForResult(cameraIntent, CameraActivity.OPEN_CAMERA_REQUEST_CODE);
        }
    }

    private void openAlbum(MediaSelector.MediaOptions options) {
        MediaSelector.with(this).setMediaOptions(options).openMediaActivity();
    }


    protected void openPhotoDialog(MediaSelector.MediaOptions options) {
        this.mMediaOptions = options;

        if (mPhotoDialog == null) {
            mPhotoDialog = new PhotoDialog(this);
        }
        if (!mPhotoDialog.isShowing()) {
            mPhotoDialog.show();
        }
        mPhotoDialog.setOnPhotoDialogItemClickListener(new PhotoDialog.OnPhotoDialogItemClickListener() {
            @Override
            public void onClickCamera(View view) {
                mPhotoDialog.dismiss();
                openCamera();
            }

            @Override
            public void onClickAlbum(View view) {
                mPhotoDialog.dismiss();
                openAlbum(options);
            }

            @Override
            public void onClickCancel(View view) {
                mPhotoDialog.dismiss();
            }

            @Override
            public void onClickCameraAndVideo(View view) {
                mPhotoDialog.dismiss();
                openVideoCamera(false);
            }
        });
    }

    protected void openCameraVideoDialog(MediaSelector.MediaOptions options, boolean hasCameraMedia) {
        this.mMediaOptions = options;

        if (mPhotoDialog == null) {
            mPhotoDialog = new PhotoDialog(this, true);
        }
        if (!mPhotoDialog.isShowing()) {
            mPhotoDialog.show();
        }
        mPhotoDialog.setOnPhotoDialogItemClickListener(new PhotoDialog.OnPhotoDialogItemClickListener() {
            @Override
            public void onClickCamera(View view) {
                mPhotoDialog.dismiss();
                openCamera();
            }

            @Override
            public void onClickAlbum(View view) {
                mPhotoDialog.dismiss();
                openAlbum(options);
            }

            @Override
            public void onClickCancel(View view) {
                mPhotoDialog.dismiss();
            }

            @Override
            public void onClickCameraAndVideo(View view) {
                mPhotoDialog.dismiss();
                openVideoCamera(hasCameraMedia);
            }
        });
    }

    private void openVideoCamera(boolean hasCameraMedia) {

        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_CAMERA_VIDEO, CameraActivity.this, CameraActivity.REQUEST_CODE_VIDEO_CAMERA, ARouterConfig.Key.HAS_CAMERA_MEDIA, hasCameraMedia);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Contast.CODE_RESULT_MEDIA && requestCode == Contast.CODE_REQUEST_MEDIA) {
            List<MediaSelectorFile> mediaList = MediaSelector.resultMediaFile(data);
            if (mediaList != null && mediaList.size() > 0) {
                resultAlbumResult(mediaList);

            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == CameraActivity.OPEN_CAMERA_REQUEST_CODE) {
            if (FileUtils.existsFile(mCameraFile.getAbsolutePath())) {
                FileUtils.scanImage(this, mCameraFile);
                if (mMediaOptions != null && mMediaOptions.isCrop) {
                    UIUtils.uCropImage(this, mCameraFile, FileUtils.resultImageCacheFile(),
                            mMediaOptions.scaleX, mMediaOptions.scaleY, mMediaOptions.cropWidth, mMediaOptions.cropHeight);
                } else {
                    resultCameraResult(mCameraFile);
                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            if (data == null) {
                return;
            }
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null && resultUri.getPath() != null) {
                File file = new File(resultUri.getPath());
                resultCameraResult(file);
            }
        } else if (resultCode == UCrop.RESULT_ERROR && requestCode == UCrop.REQUEST_CROP) {
            Toasts.with().showToast(R.string.crop_image_error);
        } else if (resultCode == Activity.RESULT_OK && requestCode == CameraActivity.REQUEST_CODE_VIDEO_CAMERA) {
            if (data == null) {
                return;
            }
            String filePath = data.getStringExtra(ARouterConfig.Key.CAMERA_VIDEO_PATH);
            resultCameraVideoResult(filePath, data.getBooleanExtra(ARouterConfig.Key.IS_CAMERA_VIDEO, false));
        }else if (resultCode == Activity.RESULT_OK && requestCode == CameraActivity.REQUEST_CODE_OPEN_SCAN_CODE){
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                if (result != null) {
                    UIUtils.parseScanCode(result);
                    resultScanCode(result);
                }

                LogUtils.w("onActivityResult--", result);//15681673146651413
                //Toasts.with().showToast(result);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toasts.with().showToast("解析失败！");
            }
        }

    }

    protected void resultScanCode(String scanCode){}
    protected void resultAlbumResult(List<MediaSelectorFile> data) {
    }

    protected void resultCameraResult(File cameraFile) {
    }

    protected void resultCameraVideoResult(String filePath, boolean isVideo) {
    }
}

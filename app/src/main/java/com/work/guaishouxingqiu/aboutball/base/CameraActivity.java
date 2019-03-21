package com.work.guaishouxingqiu.aboutball.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
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
    private File mCameraFile;
    private MediaSelector.MediaOptions mMediaOptions;

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
        });
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
        }else if (resultCode ==UCrop.RESULT_ERROR &&  requestCode == UCrop.REQUEST_CROP){
            Toasts.with().showToast(R.string.crop_image_error);
        }

    }


    protected abstract void resultAlbumResult(List<MediaSelectorFile> data);

    protected abstract void resultCameraResult(File cameraFile);

}

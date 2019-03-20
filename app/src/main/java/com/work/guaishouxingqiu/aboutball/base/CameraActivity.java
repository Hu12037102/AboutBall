package com.work.guaishouxingqiu.aboutball.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.example.media.MediaSelector;
import com.example.media.bean.MediaSelectorFile;
import com.example.media.resolver.Contast;
import com.example.media.utils.FileUtils;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.weight.PhotoDialog;

import java.io.File;
import java.util.List;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.base
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/20
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public abstract class CameraActivity<P extends BasePresenter> extends PermissionActivity<P> {
    private PhotoDialog mPhotoDialog;
    private static final int OPEN_CAMERA_REQUEST_CODE = 101;
    private File mCameraFile;

    private void openCamera() {
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                onCamera();
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {

            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {

            }
        }, Manifest.permission.CAMERA);
    }

    private void onCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            mCameraFile = FileUtils.resultImageFile(this);
            Uri cameraUri = FileUtils.fileToUri(this, mCameraFile, cameraIntent);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            startActivityForResult(cameraIntent, CameraActivity.OPEN_CAMERA_REQUEST_CODE);
        }
    }

    private void openAlbum(MediaSelector.MediaOptions options) {
        MediaSelector.with(this).setMediaOptions(options).openMediaActivity();
    }


    protected void openPhoto(MediaSelector.MediaOptions options) {
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
                onAlbumResult(mediaList);
            }
        } else if (requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (FileUtils.existsFile(mCameraFile.getAbsolutePath())) {
                FileUtils.scanImage(this, mCameraFile);
                onCameraResult(mCameraFile);
            }
        }
    }

    protected abstract void onAlbumResult(List<MediaSelectorFile> files);

    protected abstract void onCameraResult(File cameraFile);
}

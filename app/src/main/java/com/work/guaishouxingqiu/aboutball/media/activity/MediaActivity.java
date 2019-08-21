package com.work.guaishouxingqiu.aboutball.media.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.media.IntentData;
import com.work.guaishouxingqiu.aboutball.media.MediaSelector;
import com.work.guaishouxingqiu.aboutball.media.OnRecyclerItemClickListener;
import com.work.guaishouxingqiu.aboutball.media.adapter.MediaFileAdapter;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFolder;
import com.work.guaishouxingqiu.aboutball.media.resolver.MediaHelper;
import com.work.guaishouxingqiu.aboutball.media.weight.DialogHelper;
import com.work.guaishouxingqiu.aboutball.media.weight.FolderWindow;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.microshow.rxffmpeg.RxFFmpegInvoke;
import io.microshow.rxffmpeg.RxFFmpegSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import utils.task.CompressImageTask;

public class MediaActivity extends PermissionActivity {

    private TitleView mTvTop;
    private TitleView mTvBottom;
    private RecyclerView mRecyclerView;
    private MediaFileAdapter mMediaFileAdapter;
    private List<MediaSelectorFile> mMediaFileData;
    private List<MediaSelectorFolder> mMediaFolderData;
    private FolderWindow mFolderWindow;
    private List<MediaSelectorFile> mCheckMediaFileData;
    private MediaSelector.MediaOptions mOptions;
    private File mCameraFile;
    private AlertDialog mCameraPermissionDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_media;
    }

    @Override
    public void initPermission() {

        requestExternalStoragePermission();


    }


    private void requestExternalStoragePermission() {
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> list) {
                MediaActivity.super.initPermission();
            }

            @Override
            public void onNoAllow(List<String> list) {
                AlertDialog dialog = DialogHelper.with().createDialog(MediaActivity.this, getString(R.string.hint), getString(R.string.what_permission_is_must, getString(R.string.memory_card)),
                        (dialog1, which) -> finish(), (dialog12, which) -> requestExternalStoragePermission());
                dialog.show();
            }

            @Override
            public void onForbid(List<String> list) {
                showForbidPermissionDialog();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void showNoCameraAllowDialog(Context context, String title, String message) {
        if (mCameraPermissionDialog == null) {
            mCameraPermissionDialog = DialogHelper.with().
                    createDialog(context, title, message, (dialogInterface, i) ->
                            mCameraPermissionDialog.dismiss(), (dialogInterface, i) -> openCamera());
        }
        if (!mCameraPermissionDialog.isShowing()) {
            mCameraPermissionDialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }

    @Override
    protected void initView() {
        registerEventBus();
        mTvTop = findViewById(R.id.ctv_top);
        mTvBottom = findViewById(R.id.ctv_bottom);
        mRecyclerView = findViewById(R.id.ry_data);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void initData() {
        initIntent();
        MediaHelper mediaHelper = new MediaHelper(this);
        mCheckMediaFileData = new ArrayList<>();

        if (mMediaFileAdapter == null) {

            mMediaFileAdapter = new MediaFileAdapter(this, mMediaFileData, mOptions);
            mRecyclerView.setAdapter(mMediaFileAdapter);
        }
        mediaHelper.loadMedia(mOptions.isShowCamera, mOptions.isShowVideo, data -> {
            if (data != null && data.size() > 0) {
                mMediaFileData.addAll(data.get(0).fileData);
                if (mMediaFolderData == null) {
                    mMediaFolderData = data;
                } else {
                    mMediaFolderData.addAll(data);
                }
                mMediaFileAdapter.notifyDataSetChanged();
            }

        });
    }

    private void initIntent() {
        mMediaFileData = new ArrayList<>();
        Intent intent = getIntent();
        mOptions = intent.getParcelableExtra(Contast.KEY_OPEN_MEDIA);
        if (mOptions == null) {
            mOptions = MediaSelector.getDefaultOptions();
        } else {
            if (mOptions.maxChooseMedia <= 0) {
                mOptions.maxChooseMedia = 1;
            }
            mTvTop.mViewRoot.setBackgroundColor(ContextCompat.getColor(this, mOptions.themeColor));
            mTvBottom.mViewRoot.setBackgroundColor(ContextCompat.getColor(this, mOptions.themeColor));
        }

    }

    private void resultMediaData() {
        mTvTop.mTvSure.setEnabled(false);
        if (mCheckMediaFileData.size() > 0) {
            if (mViewModel.isMediaVideo(mCheckMediaFileData)) {
                compressVideo(mCheckMediaFileData);
            } else {
                compressImage();
            }
        }
    }

    private void compressImage() {
        if (mOptions.isCompress && !mOptions.isShowVideo) {
            final ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
            final View inflate = LayoutInflater.from(MediaActivity.this).inflate(R.layout.item_loading_view, viewGroup, false);
            compressImage(mCheckMediaFileData, new CompressImageTask.OnImagesResult() {
                @Override
                public void startCompress() {
                    viewGroup.addView(inflate);
                }

                @Override
                public void resultFilesSucceed(List<File> list) {
                    mTvTop.mTvSure.setEnabled(true);
                    mCheckMediaFileData.clear();
                    for (File file : list) {
                        mCheckMediaFileData.add(MediaSelectorFile.checkFileToThis(file));
                    }
                    resultMediaIntent(mCheckMediaFileData);
                    if (viewGroup.indexOfChild(inflate) != -1) {
                        viewGroup.removeView(inflate);
                    }
                }

                @Override
                public void resultFilesError() {
                    mTvTop.mTvSure.setEnabled(true);
                    if (viewGroup.indexOfChild(inflate) != -1) {
                        viewGroup.removeView(inflate);
                    }
                }
            });

        } else {
            resultMediaIntent(mCheckMediaFileData);
        }
    }

    private void resultMediaIntent(List<MediaSelectorFile> checkMediaFileData) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Contast.KEY_REQUEST_MEDIA_DATA, (ArrayList<? extends Parcelable>) checkMediaFileData);
        setResult(Contast.CODE_RESULT_MEDIA, intent);
        finish();
    }

    private void compressVideo(@NonNull List<MediaSelectorFile> checkMediaFileData) {
        for (MediaSelectorFile mediaSelectorFile : checkMediaFileData) {
            final String filePath = mediaSelectorFile.filePath;
            if (FileUtils.existsFile(filePath) && DataUtils.isVideo(filePath)) {
                File file = new File(filePath);
                if (FileUtils.isCanCompressVideo(file)) {
                    mViewModel.showLoadingView();
                    mIsCompressVideoing = true;
                    String compressVideoPath = FileUtils.createCacheVideoFile().getAbsolutePath();
                    GlideManger.get().loadImageBitmap(filePath, new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (resource.getWidth() > 0 && resource.getHeight() > 0) {
                                String[] complexCommand = new String[]{"ffmpeg", "-i", filePath, "-s",
                                        resource.getWidth() > resource.getHeight() ? "960*540" : "540*960", "-c:v",
                                        "libx264", "-crf", "30", "-preset", "ultrafast", "-y", "-acodec", "libmp3lame", compressVideoPath};
                                RxFFmpegInvoke.getInstance().runCommandRxJava(complexCommand)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new RxFFmpegSubscriber() {
                                            @Override
                                            public void onFinish() {
                                                mTvTop.mTvSure.setEnabled(true);
                                                mViewModel.dismissLoadingView();
                                                mIsCompressVideoing = false;
                                                mediaSelectorFile.filePath = compressVideoPath;
                                                resultMediaIntent(checkMediaFileData);
                                            }

                                            @Override
                                            public void onProgress(int progress) {
                                            }

                                            @Override
                                            public void onCancel() {
                                                mTvTop.mTvSure.setEnabled(true);
                                                mViewModel.dismissLoadingView();
                                                mIsCompressVideoing = false;
                                                finish();
                                            }

                                            @Override
                                            public void onError(String message) {
                                                mTvTop.mTvSure.setEnabled(true);
                                                mViewModel.dismissLoadingView();
                                                mIsCompressVideoing = false;
                                                UIUtils.showToast(R.string.video_deals_with_video);
                                                finish();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

                } else {
                    resultMediaIntent(checkMediaFileData);
                }
            }else {
                mTvTop.mTvSure.setEnabled(true);
            }
        }
    }


    private boolean hasFileCheckVideo() {
        if (mCheckMediaFileData != null && mCheckMediaFileData.size() > 0) {
            for (MediaSelectorFile mediaSelectorFile : mCheckMediaFileData) {
                if (mediaSelectorFile.isCheck && mediaSelectorFile.isVideo) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void initEvent() {
        mTvTop.setOnSureViewClickListener(new TitleView.OnSureViewClickListener() {
            @Override
            public void onSureClick(@NonNull View view) {
                resultMediaData();
            }
        });
        mTvTop.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                if (mIsCompressVideoing) {
                    return;
                }
                finish();
            }
        });
        mTvBottom.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                showMediaFolderWindows(view);
            }

            @Override
            public void onSureClick(@NonNull View view) {
                if (mCheckMediaFileData.size() > 0) {
                    toPreviewActivity(0, mCheckMediaFileData, mCheckMediaFileData);
                }

            }
        });
        mMediaFileAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void itemClick(@NonNull View view, int position) {
                if (mMediaFileData.get(position).isShowCamera) {
                    openCamera();
                } else {
                    if (mOptions.isCrop && mOptions.maxChooseMedia == 1 && mOptions.isShowVideo && mMediaFileData.get(position).isVideo) {
                        Toasts.with().showToast(R.string.video_not_crop, Toast.LENGTH_SHORT);
                    } else {
                        toPreviewActivity(position, mMediaFileData, mCheckMediaFileData);
                    }
                }
            }
        });


        mMediaFileAdapter.setOnCheckMediaListener(new MediaFileAdapter.OnCheckMediaListener() {
            @Override
            public void onChecked(boolean isCheck, int position) {
                if ((hasFileCheckVideo() || mCheckMediaFileData.size() > 0 && mMediaFileData.get(position).isVideo) && !mMediaFileData.get(position).isCheck) {
                    UIUtils.showToast(R.string.not_selector_video_and_image);
                    return;
                }
                if (isCheck) {
                    mMediaFileData.get(position).isCheck = false;
                    mCheckMediaFileData.remove(mMediaFileData.get(position));
                } else {
                    if (mCheckMediaFileData.size() < mOptions.maxChooseMedia) {
                        mMediaFileData.get(position).isCheck = true;
                        mCheckMediaFileData.add(mMediaFileData.get(position));
                    } else {
                        Toasts.with().showToast(getString(R.string.max_choose_media, String.valueOf(mOptions.maxChooseMedia)));
                    }
                }
                setSureStatus();
                mMediaFileAdapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    Glide.with(MediaActivity.this).resumeRequests();
                } else {
                    Glide.with(MediaActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> list) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //  cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    mCameraFile = FileUtils.resultImageCameraFile();
                    Uri cameraUri = FileUtils.fileToUri(MediaActivity.this, mCameraFile, cameraIntent);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    startActivityForResult(cameraIntent, Contast.REQUEST_CAMERA_CODE);
                }
            }

            @Override
            public void onNoAllow(List<String> list) {
                showNoCameraAllowDialog(MediaActivity.this, getString(R.string.hint), getString(R.string.what_permission_is_must, getString(R.string.camera)));
            }

            @Override
            public void onForbid(List<String> list) {

                showForbidPermissionDialog();

            }
        }, Manifest.permission.CAMERA);
    }

    private void toPreviewActivity(int position, @NonNull List<MediaSelectorFile> data, @NonNull List<MediaSelectorFile> checkData) {
        Intent intent = new Intent(MediaActivity.this, PreviewActivity.class);
        // intent.putParcelableArrayListExtra(Contast.KEY_PREVIEW_DATA_MEDIA, (ArrayList<? extends Parcelable>) data);
        IntentData.get().putData(data);
        intent.putParcelableArrayListExtra(Contast.KEY_PREVIEW_CHECK_MEDIA, (ArrayList<? extends Parcelable>) checkData);
        intent.putExtra(Contast.KEY_OPEN_MEDIA, mOptions);
        intent.putExtra(Contast.KEY_PREVIEW_POSITION, position);
        startActivity(intent);
    }


    private void showMediaFolderWindows(View view) {

        if (mFolderWindow == null) {
            mFolderWindow = new FolderWindow(this, mMediaFolderData);
            mFolderWindow.setOnPopupItemClickListener(new FolderWindow.OnPopupItemClickListener() {
                @Override
                public void onItemClick(@NonNull View view, int position) {
                    clickCheckFolder(position);
                }
            });
            mFolderWindow.showWindows(view);
        } else if (mFolderWindow.getFolderWindow().isShowing()) {
            mFolderWindow.dismissWindows();
        } else {
            mFolderWindow.showWindows(view);
        }


    }

    private void setSureStatus() {
        if (mCheckMediaFileData.size() > 0) {
            mTvTop.mTvSure.setClickable(true);
            mTvTop.mTvSure.setTextColor(ContextCompat.getColor(MediaActivity.this, R.color.colorTextSelector));
            mTvTop.mTvSure.setText(getString(R.string.complete_count, String.valueOf(mCheckMediaFileData.size()), String.valueOf(mOptions.maxChooseMedia)));
        } else {
            mTvTop.mTvSure.setClickable(false);
            mTvTop.mTvSure.setTextColor(ContextCompat.getColor(MediaActivity.this, R.color.colorTextUnSelector));
            mTvTop.mTvSure.setText(R.string.sure);

        }
    }

    private void clickCheckFolder(int position) {
        mTvBottom.mTvBack.setText(mMediaFolderData.get(position).folderName);
        mMediaFileData.clear();
        mMediaFileData.addAll(mMediaFolderData.get(position).fileData);
        mMediaFileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (mFolderWindow != null && mFolderWindow.getFolderWindow().isShowing()) {
            mFolderWindow.dismissWindows();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 预览图片选择发送事件
     *
     * @param mediaSelectorFile
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void previewMediaResult(@NonNull MediaSelectorFile mediaSelectorFile) {
        if (mediaSelectorFile.isCheck) {
            //首先先判断选择的媒体库
            if (!mCheckMediaFileData.contains(mediaSelectorFile)) {
                mCheckMediaFileData.add(mediaSelectorFile);
            }

        } else {
            if (mCheckMediaFileData.contains(mediaSelectorFile)) {
                mCheckMediaFileData.remove(mediaSelectorFile);
            }
        }
        for (int i = 0; i < mMediaFolderData.size(); i++) {
            if (mMediaFolderData.get(i).fileData.contains(mediaSelectorFile)) {
                mMediaFolderData.get(i).fileData.get(mMediaFolderData.get(i).fileData.indexOf(mediaSelectorFile)).isCheck = mediaSelectorFile.isCheck;
            }
        }
        setSureStatus();
        mMediaFileAdapter.notifyDataSetChanged();
    }

    /**
     * 预览图片返回
     *
     * @param checkMediaData
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void resultCheckMediaData(@NonNull List<MediaSelectorFile> checkMediaData) {
        if (checkMediaData.size() > 0) {
            mCheckMediaFileData.clear();
            mCheckMediaFileData.addAll(checkMediaData);
            resultMediaIntent(mCheckMediaFileData);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (requestCode == Contast.REQUEST_CAMERA_CODE) {
                    if (FileUtils.existsFile(mCameraFile.getAbsolutePath())) {
                        FileUtils.scanImage(this, mCameraFile);
                        MediaSelectorFile mediaSelectorFile = MediaSelectorFile.checkFileToThis(mCameraFile);
                        if (mediaSelectorFile.hasData()) {
                            mCheckMediaFileData.add(mediaSelectorFile);
                        }
                        resultMediaData();
                    }

                }
                break;

        }
    }

}

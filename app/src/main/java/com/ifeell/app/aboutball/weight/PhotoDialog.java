package com.ifeell.app.aboutball.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ifeell.app.aboutball.R;


/**
 * 项  目 :  ImageCompress
 * 包  名 :  com.baixiaohu.imagecompress.dialog
 * 类  名 :  PhotoDialog
 * 作  者 :  胡庆岭
 * 时  间 :  2018/1/30 0030 下午 2:52
 * 描  述 :  ${TODO} 相机相册dialog
 *
 * @author ：
 */

public class PhotoDialog extends Dialog {

    private TextView mItemCamera, mItemAlbum, mItemCancel, mItemCameraVideo;
    private boolean mHasVideo;

    public void setOnPhotoDialogItemClickListener(OnPhotoDialogItemClickListener onPhotoDialogItemClickListener) {
        this.onPhotoDialogItemClickListener = onPhotoDialogItemClickListener;
    }

    private OnPhotoDialogItemClickListener onPhotoDialogItemClickListener;

    public PhotoDialog(@NonNull Context context) {
        this(context, R.style.DefaultDialogStyle);
    }

    public PhotoDialog(@NonNull Context context, boolean hasVideo) {
        this(context, R.style.DefaultDialogStyle);
        this.mHasVideo = hasVideo;
    }

    public PhotoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_view);
        init();
        initView();
        initEvent();
    }

    private void initEvent() {
        mItemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPhotoDialogItemClickListener != null) {
                    onPhotoDialogItemClickListener.onClickCamera(mItemCamera);
                }
            }
        });
        mItemAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPhotoDialogItemClickListener != null) {
                    onPhotoDialogItemClickListener.onClickAlbum(mItemAlbum);
                }
            }
        });
        mItemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPhotoDialogItemClickListener != null) {
                    onPhotoDialogItemClickListener.onClickCancel(mItemCancel);
                }
            }
        });
        mItemCameraVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPhotoDialogItemClickListener != null) {
                    onPhotoDialogItemClickListener.onClickCameraAndVideo(v);
                }
            }
        });
    }

    private void initView() {
        mItemCamera = findViewById(R.id.item_camera);
        mItemAlbum = findViewById(R.id.item_album);
        mItemCancel = findViewById(R.id.item_cancel);
        mItemCameraVideo = findViewById(R.id.item_camera_video);
        if (mHasVideo) {
            mItemCameraVideo.setVisibility(View.VISIBLE);
            mItemCamera.setVisibility(View.GONE);
        } else {
            mItemCamera.setVisibility(View.VISIBLE);
            mItemCameraVideo.setVisibility(View.GONE);
        }
    }

    private void init() {
        Window window = getWindow();
        if (window == null) {
            dismiss();
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = getWindow().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dm.widthPixels;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }

    public interface OnPhotoDialogItemClickListener {
        void onClickCamera(View view);

        void onClickAlbum(View view);

        void onClickCancel(View view);

        void onClickCameraAndVideo(View view);
    }
}

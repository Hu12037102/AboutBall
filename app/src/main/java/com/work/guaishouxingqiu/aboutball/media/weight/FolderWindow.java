package com.work.guaishouxingqiu.aboutball.media.weight;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;


import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.media.OnRecyclerItemClickListener;
import com.work.guaishouxingqiu.aboutball.media.adapter.MediaFolderAdapter;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFolder;

import java.util.ArrayList;
import java.util.List;

public class FolderWindow {
    private List<MediaSelectorFolder> mFolderData;
    private PopupWindow mPopupWindow;
    private MediaFolderAdapter mFolderAdapter;
    private Context mContext;
    private View mViewRoot;
    private View mShowView;

    public void setOnPopupItemClickListener(OnPopupItemClickListener onPopupItemClickListener) {
        this.onPopupItemClickListener = onPopupItemClickListener;
    }

    private OnPopupItemClickListener onPopupItemClickListener;

    public FolderWindow(@NonNull Context context, @Nullable List<MediaSelectorFolder> folderData) {
        if (folderData == null) {
            folderData = new ArrayList<>();
        }
        this.mFolderData = folderData;
        this.mContext = context;
        createWindows();
        initEvent();
    }

    private void initEvent() {
        mViewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolderWindow.this.dismissWindows();
            }
        });
        mFolderAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void itemClick(@NonNull View view, int position) {
                if (onPopupItemClickListener != null) {
                    onPopupItemClickListener.onItemClick(view, position);
                }
                FolderWindow.this.dismissWindows();
            }
        });
    }

    public PopupWindow getFolderWindow() {
        return mPopupWindow;
    }

    public void dismissWindows() {
        windowAnimation(false);
    }

    private void createWindows() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.color80000000)));
            mPopupWindow.setClippingEnabled(false);
            //mPopupWindow.setOutsideTouchable(true);
            @SuppressLint("InflateParams")
            View inflateView = LayoutInflater.from(mContext).inflate(R.layout.popup_media_view, null, false);
            RecyclerView mRvFolder = inflateView.findViewById(R.id.rv_folder);
            mViewRoot = inflateView.findViewById(R.id.ll_root);
            mRvFolder.setLayoutManager(new LinearLayoutManager(mContext));
            mFolderAdapter = new MediaFolderAdapter(mFolderData);
            mRvFolder.setItemAnimator(new DefaultItemAnimator());
            mRvFolder.setAdapter(mFolderAdapter);
            mPopupWindow.setContentView(inflateView);


            //  mPopupWindow.setAnimationStyle(R.style.DialogAnimation);

        }
    }

    public void showWindows(@NonNull View view) {
        this.mShowView = view;
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, ScreenUtils.dp2px(view.getContext(), 55));
        windowAnimation(true);
    }


    public interface OnPopupItemClickListener {
        void onItemClick(@NonNull View view, int position);
    }

    private void windowAnimation(final boolean isOpen) {
        ObjectAnimator objectAnimator;
        if (isOpen) {
            objectAnimator = ObjectAnimator.ofFloat(mViewRoot, "translationY", (ScreenUtils.screenHeight(mContext) - ScreenUtils.dp2px(mContext, mShowView.getHeight())), 0);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(mViewRoot, "translationY", 0, (ScreenUtils.screenHeight(mContext) - ScreenUtils.dp2px(mContext, mShowView.getHeight())));
        }
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isOpen) {
                    mPopupWindow.dismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (!isOpen) {
                    mPopupWindow.dismiss();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

}

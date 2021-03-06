package com.ifeell.app.aboutball.weight;

import android.app.Activity;

import android.view.View;
import android.widget.FrameLayout;

import com.ifeell.app.aboutball.R;

/**
 * 项  目 :  ipk_android
 * 包  名 :  com.work.guaishouxingqiu.aboutball.weight
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/11
 * 描  述 :  ${TODO} loadingView
 *
 * @author ：
 */
public class LoadingView {
    private Activity mActivity;
    private View mInflateView;
    private FrameLayout mDecorGroup;


    public LoadingView(Activity activity) {
        this.mActivity = activity;
    }

    public void showLoadingView() {
        View decorView = mActivity.getWindow().getDecorView().getRootView();
        if (decorView instanceof FrameLayout && mInflateView == null) {
            mDecorGroup = (FrameLayout) decorView;
            mInflateView = mActivity.getLayoutInflater().inflate(R.layout.item_loading_view, mDecorGroup, false);
            mDecorGroup.addView(mInflateView);
        }
    }

    public void dismissLoadingView() {
        if (mInflateView != null && mDecorGroup != null) {
            if (mDecorGroup.indexOfChild(mInflateView) > 0) {
                mDecorGroup.removeView(mInflateView);
            }
        }
    }

}

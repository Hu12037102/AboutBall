package com.ifeell.app.aboutball.weight;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.ifeell.app.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 9:52
 * 更新时间: 2019/4/28 9:52
 * 描述:单个点击PopupWindows
 */
public class SingPopupWindows {
    private Context mContext;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private TextView mTvContent;
    private View mRootView;

    public SingPopupWindows(@NonNull Context context) {
        this.mContext = context;
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        ViewGroup viewGroup = null;
        if (mContext instanceof Activity) {
            viewGroup = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
        }
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.item_popup_windows_exit_team, viewGroup, false);
        mTvContent = mContentView.findViewById(R.id.tv_content);
        mRootView = mContentView.findViewById(R.id.root_view);
    }

    public PopupWindow getWindow() {
        return mPopupWindow;
    }

    public void setContent(@NonNull CharSequence content) {
        mTvContent.setText(content);
    }

    public void setContent(@StringRes int resContent) {
        mTvContent.setText(resContent);
    }

    public void setContentDrawableRes(@DrawableRes int resLeft, @DrawableRes int resTop, @DrawableRes int resRight, @DrawableRes int resBottom) {
        mTvContent.setCompoundDrawablesWithIntrinsicBounds(resLeft, resTop, resRight, resBottom);
    }

    private void initData() {
        mPopupWindow = new PopupWindow(mContentView);
        mPopupWindow.setWidth(ScreenUtils.dp2px(mContext, 110));
        mPopupWindow.setHeight(ScreenUtils.dp2px(mContext, 40));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mPopupWindow.setAttachedInDecor(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPopupWindow.setOverlapAnchor(true);
        }
        mPopupWindow.setClippingEnabled(true);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopupWindow.setElevation(ScreenUtils.dp2px(mContext, 6));
        }
    }

    public void showAtLocation(View view, int gravity, int offX, int offY) {
        mPopupWindow.showAtLocation(view, gravity, offX, offY);
    }

    public void showAsDropDown(View view, int offX, int offY) {
        mPopupWindow.showAsDropDown(view, offX, offY);

    }
    public void showAsDropDown(View view, int offX, int offY,int gravity ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(view, offX, offY,gravity);
        }

    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    private void initEvent() {

    }

    public void setPopupWindowsItemClickListener(@NonNull View.OnClickListener onClickListener) {
        mContentView.setOnClickListener(onClickListener);
    }
}

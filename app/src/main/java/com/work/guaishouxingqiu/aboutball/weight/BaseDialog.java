package com.work.guaishouxingqiu.aboutball.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 12:34
 * 更新时间: 2019/3/22 12:34
 * 描述:
 */
public abstract class BaseDialog extends AppCompatDialog {
    public void setOnDialogShowOrDismissListener(OnDialogShowOrDismissListener onDialogShowOrDismissListener) {
        this.onDialogShowOrDismissListener = onDialogShowOrDismissListener;
    }

    protected OnDialogShowOrDismissListener onDialogShowOrDismissListener;

    public BaseDialog(Context context) {
        this(context, R.style.DefaultDialogStyle);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(getContext());
        initData();
        initEvent();
        // initWindows();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
    }

    protected void initWindows() {
        Window window = getWindow();
        if (window == null) {
            dismiss();
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = window.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dm.widthPixels;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }

    @Override
    public void show() {
        if (onDialogShowOrDismissListener != null) {
            onDialogShowOrDismissListener.onShow(this);
        }
        super.show();

    }

    @Override
    public void dismiss() {
        if (onDialogShowOrDismissListener != null) {
            onDialogShowOrDismissListener.onDismiss(this);
        }
        super.dismiss();

    }

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract void initView(Context context);

    public interface OnItemClickSureAndCancelListener {
        void onClickSure(@NonNull View view);

        void onClickCancel(@NonNull View view);
    }

    public interface OnDialogShowOrDismissListener {
        void onShow(Dialog dialog);

        void onDismiss(Dialog dialog);
    }
}

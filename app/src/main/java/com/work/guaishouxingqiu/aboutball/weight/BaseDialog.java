package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
    public BaseDialog(Context context) {
        this(context, R.style.DefaultDialogStyle);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);

        initView(context);
        initWindows();
        initData();
        initEvent();
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

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract void initView(Context context);
}
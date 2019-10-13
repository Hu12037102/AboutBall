package com.ifeell.app.aboutball.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 10:27
 * 更新时间: 2019/9/12 10:27
 * 描述:
 */
public class QCodeDialog extends BaseDialog {

    private ImageView mIvContent;
    private View mLlRoot;

    public QCodeDialog(Context context) {
        super(context);
    }

    public QCodeDialog setBitmap(@NonNull Bitmap bitmap) {
        if (bitmap != null) {
            mIvContent.setImageBitmap(bitmap);
        }
        return this;
    }



    @Override
    protected void initEvent() {
        mLlRoot.setOnClickListener(v -> dismiss());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_q_code_view, null);
        setContentView(inflateView);
        mIvContent = inflateView.findViewById(R.id.iv_content);
        mLlRoot = inflateView.findViewById(R.id.ll_root);
    }

    @Override
    protected void initWindows() {
       // super.initWindows();
        Window window = getWindow();
        if (window == null) {
            dismiss();
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = window.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = dm.widthPixels;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
    }
}

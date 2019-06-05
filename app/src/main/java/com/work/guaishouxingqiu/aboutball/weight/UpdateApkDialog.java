package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/5 9:25
 * 更新时间: 2019/6/5 9:25
 * 描述:更新APPDialog
 */
public class UpdateApkDialog extends BaseDialog {

    private TextView mTvVersionsName;
    private TextView mTvUpdateContent;
    private TextView mTvUpdateNow;
    private boolean mIsForceUpdate;
    private String mUpdateContent;
    private ImageView mIvClose;

    public void setOnClickUpdateViewListener(OnClickUpdateViewListener onClickUpdateViewListener) {
        this.onClickUpdateViewListener = onClickUpdateViewListener;
    }

    private OnClickUpdateViewListener onClickUpdateViewListener;

    public UpdateApkDialog(Context context, String updateContent, boolean isForceUpdate) {
        super(context);
        this.mUpdateContent = updateContent;
        this.mIsForceUpdate = isForceUpdate;
    }

    @Override
    protected void initEvent() {
        mTvUpdateNow.setOnClickListener(v -> {
            setUpdateNowStatus(false);
            if (onClickUpdateViewListener != null) {
                onClickUpdateViewListener.clickUpdate(mTvUpdateContent);
            }
        });
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        setUpdateNowStatus(true);
    }

    private void setUpdateNowStatus(boolean isClick) {
        mTvUpdateNow.setEnabled(isClick);
        mTvUpdateNow.setBackgroundResource(isClick ? R.drawable.shape_click_button : R.drawable.shape_default_button);
        mTvUpdateNow.setText(isClick ? R.string.update_now : R.string.updating);
    }

    @Override
    protected void initData() {
        mTvVersionsName.setText(DownloadApkHelp.getVersionName(getContext()));


    }

    @Override
    protected void initView(Context context) {
        View inflaterView = LayoutInflater.from(context).inflate(R.layout.dialog_update_apk_view, null);
        setContentView(inflaterView);
        mTvVersionsName = findViewById(R.id.tv_versions_name);
        mTvUpdateContent = findViewById(R.id.tv_update_content);
        mTvUpdateNow = findViewById(R.id.tv_update_now);
        mIvClose = findViewById(R.id.iv_close);
    }

    @Override
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
        layoutParams.width = dm.widthPixels - ScreenUtils.dp2px(getContext(),80);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);

        setCanceledOnTouchOutside(!mIsForceUpdate);
        setCancelable(!mIsForceUpdate);
        if (!mIsForceUpdate) {
            mIvClose.setVisibility(View.VISIBLE);
        } else {
            mIvClose.setVisibility(View.GONE);
        }
        mTvUpdateContent.setText(mUpdateContent);
        mTvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public interface OnClickUpdateViewListener {
        void clickUpdate(View view);
    }
}

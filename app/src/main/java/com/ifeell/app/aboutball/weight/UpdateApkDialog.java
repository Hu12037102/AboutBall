package com.ifeell.app.aboutball.weight;

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

import androidx.core.text.HtmlCompat;

import com.example.item.util.ScreenUtils;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;

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
    private ImageView mIvClose;
    private ResultUpdateApkBean mUpdateBean;

    public void setOnClickUpdateViewListener(OnClickUpdateViewListener onClickUpdateViewListener) {
        this.onClickUpdateViewListener = onClickUpdateViewListener;
    }

    private OnClickUpdateViewListener onClickUpdateViewListener;

    public UpdateApkDialog(Context context, ResultUpdateApkBean updateBean) {
        super(context);
        this.mUpdateBean = updateBean;
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
        layoutParams.width = dm.widthPixels - ScreenUtils.dp2px(getContext(), 80);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
        mTvVersionsName.setText(mUpdateBean.version);
        setCanceledOnTouchOutside(mUpdateBean.isForce == 0);
        setCancelable(mUpdateBean.isForce == 0);
        if (mUpdateBean.isForce == 0) {
            mIvClose.setVisibility(View.VISIBLE);
        } else {
            mIvClose.setVisibility(View.GONE);
        }
        mTvUpdateContent.setText(HtmlCompat.fromHtml(mUpdateBean.content, HtmlCompat.FROM_HTML_MODE_COMPACT).toString());
        mTvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public interface OnClickUpdateViewListener {
        void clickUpdate(View view);
    }
}

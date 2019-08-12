package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/9 10:46
 * 更新时间: 2019/8/9 10:46
 * 描述:未确定约球dialog
 */
public class NotAboutBallDialog extends BaseDialog {

    private TextView mTvFormerBall;
    private TextView mTvNewBall;
    private ImageView mIvClose;

    public void setOnClickAboutItemClickListener(OnClickAboutItemClickListener onClickAboutItemClickListener) {
        this.onClickAboutItemClickListener = onClickAboutItemClickListener;
    }

    private OnClickAboutItemClickListener onClickAboutItemClickListener;

    public NotAboutBallDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {
        mTvFormerBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickAboutItemClickListener != null) {
                    onClickAboutItemClickListener.onClickFormerBall(mTvFormerBall);
                }
            }
        });
        mTvNewBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickAboutItemClickListener != null) {
                    onClickAboutItemClickListener.onClickNewBall(mTvNewBall);
                }
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
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View contentView = getLayoutInflater().inflate(R.layout.dialog_not_about_ball_view, (ViewGroup) DataUtils.checkData(getWindow()).getDecorView(), false);
        setContentView(contentView);
        mTvFormerBall = contentView.findViewById(R.id.tv_former_ball);
        mTvNewBall = contentView.findViewById(R.id.tv_new_ball);
        mIvClose = contentView.findViewById(R.id.iv_close);
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
        layoutParams.width = dm.widthPixels - ScreenUtils.dp2px(getContext(), 60);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }

    public interface OnClickAboutItemClickListener {
        void onClickFormerBall(View view);

        void onClickNewBall(View view);
    }
}

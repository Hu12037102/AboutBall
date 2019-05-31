package com.work.guaishouxingqiu.aboutball.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 9:39
 * 更新时间: 2019/3/12 9:39
 * 描述:
 */
public class HintDialog extends Dialog {

    private TextView mTvTitle;
    private TextView mTvBody;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvSures;
    private ViewGroup mLlGroup;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickSureAndCancelListener(BaseDialog.OnItemClickSureAndCancelListener onItemClickSureAndCancelListener) {
        this.onItemClickSureAndCancelListener = onItemClickSureAndCancelListener;
    }

    private BaseDialog.OnItemClickSureAndCancelListener onItemClickSureAndCancelListener;

    private HintDialog(Context context) {
        this(context, R.style.DefaultDialogStyle);
    }

    private HintDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_hint_dialog_view, null);
        setContentView(view);
        initView(view);
        initData();
        initEvent();

    }


    private void initEvent() {
        mTvSure.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickSure(v);
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickSureAndCancelListener != null) {
                    onItemClickSureAndCancelListener.onClickCancel(v);
                }
                dismiss();
            }
        });
        mTvSures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickSureAndCancelListener != null) {
                    onItemClickSureAndCancelListener.onClickSure(v);
                }
                dismiss();
            }
        });
    }

    private void initData() {
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.DefaultDialogAnimation);


    }

    public void setContentGravity(int gravity) {
        mTvBody.setGravity(gravity);
    }

    public void setTitle(@NonNull String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(@StringRes int resTitle) {
        mTvTitle.setText(resTitle);
    }

    public void setBody(@NonNull CharSequence body) {
        mTvBody.setText(body);
    }

    public void setBody(@StringRes int resBody) {
        mTvBody.setText(resBody);
    }

    public void setSure(@NonNull String sure) {
        mTvSure.setText(sure);
    }

    public void setSure(@StringRes int resSure) {
        mTvSure.setText(resSure);
    }


    public void setSures(@NonNull String sure) {
        mTvSures.setText(sure);
    }

    public void setSures(@StringRes int resSure) {
        mTvSures.setText(resSure);
    }

    public void setCancel(@NonNull String cancel) {
        mTvCancel.setText(cancel);
    }

    public void setCancel(@StringRes int cancelRes) {
        mTvCancel.setText(cancelRes);
    }


    public void setSingButton(boolean isShowSing) {
        if (isShowSing) {
            mLlGroup.setVisibility(View.GONE);
            mTvSure.setVisibility(View.VISIBLE);
        } else {
            mLlGroup.setVisibility(View.VISIBLE);
            mTvSure.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvBody = view.findViewById(R.id.tv_body);
        mTvSure = view.findViewById(R.id.tv_sure);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvSures = view.findViewById(R.id.tv_sures);
        mLlGroup = view.findViewById(R.id.ll_group);
    }

    public interface OnItemClickListener {
        void onClickSure(@NonNull View view);
    }


    public static class Builder {
        private HintDialog mHintDialog;

        public Builder(Context context) {
            mHintDialog = new HintDialog(context);

        }

        public Builder setTitle(@NonNull String title) {
            mHintDialog.setTitle(title);
            return this;
        }

        public Builder setTitle(@StringRes int resTitle) {
            mHintDialog.setTitle(resTitle);
            return this;
        }

        public Builder setBody(@NonNull CharSequence body) {
            mHintDialog.setBody(body);
            return this;
        }


        public Builder setBody(@StringRes int resBody) {
            mHintDialog.setBody(resBody);
            return this;
        }

        public Builder setSure(@NonNull String sure) {
            mHintDialog.setSure(sure);
            return this;
        }

        public Builder setSure(@StringRes int resSure) {
            mHintDialog.setSure(resSure);
            return this;
        }

        public Builder setCancelTouchOut(boolean cancelTouchOut) {
            mHintDialog.setCanceledOnTouchOutside(cancelTouchOut);
            return this;
        }

        public Builder setSures(@NonNull String sures) {
            mHintDialog.setSures(sures);
            return this;
        }

        public Builder setSures(@StringRes int resSures) {
            mHintDialog.setSures(resSures);
            return this;
        }

        public Builder setCancel(@NonNull String cancel) {
            mHintDialog.setCancel(cancel);
            return this;
        }

        public Builder setCancel(@StringRes int cancelRes) {
            mHintDialog.setCancel(cancelRes);
            return this;
        }


        public Builder setShowSingButton(boolean isShowSing) {
            mHintDialog.setSingButton(isShowSing);
            return this;
        }

        public HintDialog builder() {
            return mHintDialog;
        }
    }
}

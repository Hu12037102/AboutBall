package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 17:21
 * 更新时间: 2019/4/22 17:21
 * 描述:
 */
public class InputDialog extends BaseDialog {

    private TextView mTvTitle;
    private AppCompatEditText mAcetContent;
    private TextView mTvCancel;
    private TextView mTvSure;

    public void setOnItemClickSureAndCancelListener(OnItemClickSureAndCancelListener onItemClickSureAndCancelListener) {
        this.onItemClickSureAndCancelListener = onItemClickSureAndCancelListener;
    }

    private OnItemClickSureAndCancelListener onItemClickSureAndCancelListener;


    private InputDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickSureAndCancelListener != null) {
                    onItemClickSureAndCancelListener.onClickCancel(view);
                }
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickSureAndCancelListener != null) {
                    onItemClickSureAndCancelListener.onClickSure(view);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initWindows() {
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

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_input_view, null);
        mTvTitle = inflateView.findViewById(R.id.tv_title);
        mAcetContent = inflateView.findViewById(R.id.acet_content);
        mTvCancel = inflateView.findViewById(R.id.tv_cancel);
        mTvSure = inflateView.findViewById(R.id.tv_sures);
        setContentView(inflateView);
    }

    public void setTitle(String content) {
        mTvTitle.setText(content);
    }

    public void setTitle(@StringRes int resContent) {
        mTvTitle.setText(resContent);
    }

    public void setContent(String content) {
        mAcetContent.setText(content);
    }

    public void setContent(@StringRes int resContent) {
        mAcetContent.setText(resContent);
    }

    public void setInputStyle(int inputStyle) {
        mAcetContent.setInputType(inputStyle);
    }

    public void setContentHint(String hint) {
        mAcetContent.setHint(hint);
    }

    public void setContentHint(@StringRes int hintRes) {
        mAcetContent.setHint(hintRes);
    }

    public void setCancel(String content) {
        mTvCancel.setText(content);
    }

    public void setCancel(@StringRes int resContent) {
        mTvCancel.setText(resContent);
    }

    public void setSure(String content) {
        mTvSure.setText(content);
    }

    public void setSure(@StringRes int resContent) {
        mTvSure.setText(resContent);
    }

    public String getContent() {
        return mAcetContent.getText() == null ? "" : mAcetContent.getText().toString();
    }

    private void setContentLength(int length) {
        mAcetContent.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(length)});
    }

    public static class Build {

        private final InputDialog mInputDialog;

        public Build(Context context) {
            mInputDialog = new InputDialog(context);
        }

        public Build setTitle(String content) {
            mInputDialog.setTitle(content);
            return this;
        }

        public Build setTitle(@StringRes int resContent) {
            mInputDialog.setTitle(resContent);
            return this;
        }

        public Build setContent(String content) {
            mInputDialog.setContent(content);
            return this;
        }

        public Build setContent(@StringRes int resContent) {
            mInputDialog.setContent(resContent);
            return this;
        }

        public Build setInputType(int inputType) {
            mInputDialog.setInputStyle(inputType);
            return this;
        }

        public Build setContentLength(int length) {
            mInputDialog.setContentLength(length);
            return this;
        }

        public Build setContentHint(@StringRes int resHint) {
            mInputDialog.setContentHint(resHint);
            return this;
        }

        public Build setContentHint(String hint) {
            mInputDialog.setContentHint(hint);
            return this;
        }

        public Build setCancal(String content) {
            mInputDialog.setCancel(content);
            return this;
        }

        public Build setCancel(@StringRes int resContent) {
            mInputDialog.setCancel(resContent);
            return this;
        }

        public Build setSure(String content) {
            mInputDialog.setSure(content);
            return this;
        }

        public Build setSure(@StringRes int resContent) {
            mInputDialog.setSure(resContent);
            return this;
        }

        public InputDialog build() {
            return mInputDialog;
        }
    }


}

package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 11:32
 * 更新时间: 2019/3/22 11:32
 * 描述:
 */
public class InputMessageDialog extends BaseDialog {


    private TextView mTvCommit;
    private AppCompatEditText mAcetMessage;
    private static final int WHAT = 100;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT:
                    mAcetMessage.setFocusable(true);
                    mAcetMessage.setFocusableInTouchMode(true);
                    mAcetMessage.requestFocus();
                    InputMethodManager methodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (methodManager != null && methodManager.isActive()) {
                        methodManager.showSoftInput(mAcetMessage, 0);

                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    public void setOnInputMessageListener(OnInputMessageListener onInputMessageListener) {
        this.onInputMessageListener = onInputMessageListener;
    }

    private OnInputMessageListener onInputMessageListener;

    public InputMessageDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_mesage_view, null);
        setContentView(view);
        mTvCommit = findViewById(R.id.tv_commit);
        mAcetMessage = findViewById(R.id.aet_message);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void show() {

        super.show();
        mHandler.sendEmptyMessageDelayed(100, 100);
    }

    @Override
    public void dismiss() {
        InputMethodManager methodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager != null && !methodManager.isActive()) {
            methodManager.showSoftInput(mAcetMessage, 0);
        }
        super.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(WHAT, null);
    }

    public void clearEditData() {
        mAcetMessage.setText(null);
    }

    @Override
    protected void initEvent() {
        mTvCommit.setOnClickListener(v -> {
            if (DataUtils.isEmpty(DataUtils.checkData(mAcetMessage.getText()))) {
                Toasts.with().showToast(R.string.please_input_message);
            } else if (onInputMessageListener != null) {
                onInputMessageListener.onResultMessage(DataUtils.checkData(mAcetMessage.getText()).toString());
                dismiss();
            }
        });
    }

    public interface OnInputMessageListener {
        void onResultMessage(@NonNull String text);
    }

}

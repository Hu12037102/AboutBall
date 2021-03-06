package com.ifeell.app.aboutball.login.fragment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.login.bean.EventMessagePhone;
import com.ifeell.app.aboutball.login.bean.RegisterInputEditBean;
import com.ifeell.app.aboutball.login.contract.RegisterCodeContract;
import com.ifeell.app.aboutball.login.presenter.RegisterCodePresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:57
 * 更新时间: 2019/3/11 13:57
 * 描述: 注册输入验证码Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_REGISTER_CODE)
public class RegisterCodeFragment extends BaseFragment<RegisterCodePresenter> implements RegisterCodeContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_gain_message_code)
    TextView mTvGainMessageCode;
    @BindView(R.id.tiet_input_1)
    AppCompatEditText mTietInput1;
    @BindView(R.id.tiet_input_2)
    AppCompatEditText mTietInput2;
    @BindView(R.id.tiet_input_3)
    AppCompatEditText mTietInput3;
    @BindView(R.id.tiet_input_4)
    AppCompatEditText mTietInput4;
    private String mPhooneNumber;
    private boolean isFirst = true;

    public void setOnMessageCodeInputResult(OnMessageCodeInputResult mOnMessageCodeInputResult) {
        this.mOnMessageCodeInputResult = mOnMessageCodeInputResult;
    }

    private OnMessageCodeInputResult mOnMessageCodeInputResult;

    public interface OnMessageCodeInputResult {
        void onInputComplete(@NonNull String messageCode);
    }

    public static RegisterCodeFragment newInstance() {
        return new RegisterCodeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_code;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        mPresenter.start();
        registerEventBus();

    }

    private void requestEditText1() {
        mTietInput1.setFocusable(true);
        mTietInput1.setFocusableInTouchMode(true);
        mTietInput1.requestFocus();
        mTietInput2.setFocusable(false);
        mTietInput2.setFocusableInTouchMode(false);
        mTietInput3.setFocusable(false);
        mTietInput3.setFocusableInTouchMode(false);
        mTietInput4.setFocusable(false);
        mTietInput4.setFocusableInTouchMode(false);
    }

    private void requestEditText2() {
        mTietInput1.setFocusable(false);
        mTietInput1.setFocusableInTouchMode(false);
        mTietInput2.setFocusable(true);
        mTietInput2.setFocusableInTouchMode(true);
        mTietInput2.requestFocus();
        mTietInput3.setFocusable(false);
        mTietInput3.setFocusableInTouchMode(false);
        mTietInput4.setFocusable(false);
        mTietInput4.setFocusableInTouchMode(false);
    }

    private void requestEditText3() {
        mTietInput1.setFocusable(false);
        mTietInput1.setFocusableInTouchMode(false);
        mTietInput2.setFocusable(false);
        mTietInput2.setFocusableInTouchMode(false);
        mTietInput3.setFocusable(true);
        mTietInput3.setFocusableInTouchMode(true);
        mTietInput3.requestFocus();
        mTietInput4.setFocusable(false);
        mTietInput4.setFocusableInTouchMode(false);
    }

    private void requestEditText4() {
        mTietInput1.setFocusable(false);
        mTietInput1.setFocusableInTouchMode(false);
        mTietInput2.setFocusable(false);
        mTietInput2.setFocusableInTouchMode(false);
        mTietInput3.setFocusable(false);
        mTietInput3.setFocusableInTouchMode(false);
        mTietInput4.setFocusable(true);
        mTietInput4.setFocusableInTouchMode(true);
        mTietInput4.requestFocus();
    }

    @Override
    protected void initEvent() {


        mTietInput2.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL) {
                if (DataUtils.isEmpty(mTietInput2.getText())) {
                 requestEditText1();
                }
            }
            return false;
        });

        mTietInput3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL) {
                    if (DataUtils.isEmpty(mTietInput3.getText())) {
                       requestEditText2();
                    }
                }
                return false;
            }
        });
        mTietInput4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL) {
                    if (DataUtils.isEmpty(mTietInput4.getText())) {
                        requestEditText3();

                    }
                }
                return false;
            }
        });
        mTietInput1.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mTietInput1.setBackgroundResource(R.drawable.shape_register_input_check_edit);
                mTietInput2.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput3.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput4.setBackgroundResource(R.drawable.shape_register_input_default_edit);
            }

        });
        mTietInput2.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mTietInput1.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput2.setBackgroundResource(R.drawable.shape_register_input_check_edit);
                mTietInput3.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput4.setBackgroundResource(R.drawable.shape_register_input_default_edit);
            }

        });
        mTietInput3.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mTietInput1.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput2.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput3.setBackgroundResource(R.drawable.shape_register_input_check_edit);
                mTietInput4.setBackgroundResource(R.drawable.shape_register_input_default_edit);
            }

        });
        mTietInput4.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mTietInput1.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput2.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput3.setBackgroundResource(R.drawable.shape_register_input_default_edit);
                mTietInput4.setBackgroundResource(R.drawable.shape_register_input_check_edit);
            }

        });
        inputMessageCodeChangeListener(mTietInput1);
        inputMessageCodeChangeListener(mTietInput2);
        inputMessageCodeChangeListener(mTietInput3);
        inputMessageCodeChangeListener(mTietInput4);

    }

    private boolean isAllInputMessageCode() {
        return !DataUtils.isEmpty(DataUtils.checkData(mTietInput1.getText()).toString())
                && !DataUtils.isEmpty(DataUtils.checkData(mTietInput2.getText()).toString())
                && !DataUtils.isEmpty(DataUtils.checkData(mTietInput3.getText()).toString())
                && !DataUtils.isEmpty(DataUtils.checkData(mTietInput4.getText()).toString());
    }

    private void inputMessageCodeChangeListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (editText.getId()) {
                    case R.id.tiet_input_1:
                        if (DataUtils.isEmpty(mTietInput1.getText())) {
                            requestEditText1();
                        } else {
                            requestEditText2();
                        }
                        break;
                    case R.id.tiet_input_2:
                        if (DataUtils.isEmpty(mTietInput2.getText())) {
                            requestEditText2();
                        } else {
                            requestEditText3();
                        }

                        break;
                    case R.id.tiet_input_3:
                        if (DataUtils.isEmpty(mTietInput3.getText())) {
                            requestEditText3();
                        } else {
                            requestEditText4();
                        }
                        break;
                    case R.id.tiet_input_4:
                        requestEditText4();
                        break;
                }

                if (isAllInputMessageCode() && mOnMessageCodeInputResult != null) {
                    String messageCode = DataUtils.checkData(mTietInput1.getText()).toString() +
                            DataUtils.checkData(mTietInput2.getText()).toString() +
                            DataUtils.checkData(mTietInput3.getText()).toString() +
                            DataUtils.checkData(mTietInput4.getText()).toString();
                    mPresenter.judgeMessageCode(mPhooneNumber, messageCode);

                }


                LogUtils.w("editText", (editText.getId() == mTietInput1.getId()) + "--");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            mPresenter.countDownTime(Contast.MESSAGE_COUNT_DOWN_LENGTH);
            isFirst = false;
        }
        LogUtils.w("setUserVisibleHint--", isVisibleToUser + "--");
    }

    @Subscribe
    public void eventPostPhone(@NonNull EventMessagePhone bean) {
        this.mPhooneNumber = DataUtils.checkData(bean).phone;
    }

    @Override
    protected RegisterCodePresenter createPresenter() {
        return new RegisterCodePresenter(this);
    }

    @Override
    public void sendMessageCodeSucceedResult() {
        mPresenter.countDownTime(Contast.MESSAGE_COUNT_DOWN_LENGTH);
    }

    @Override
    public void countDownTimeUpdate(long time) {
        mTvGainMessageCode.setClickable(false);
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(getContext(), R.color.color_3));
        mTvGainMessageCode.setText(getString(R.string.regain_load_time, String.valueOf(time)));
    }

    @Override
    public void countDownTimeComplete() {
        mTvGainMessageCode.setText(R.string.gain_message_code);
        mTvGainMessageCode.setClickable(true);
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(getContext(), R.color.color_4));
    }

    @Override
    public void resultMessageCode() {
        String messageCode = DataUtils.checkData(mTietInput1.getText()).toString() +
                DataUtils.checkData(mTietInput2.getText()).toString() +
                DataUtils.checkData(mTietInput3.getText()).toString() +
                DataUtils.checkData(mTietInput4.getText()).toString();
        mOnMessageCodeInputResult.onInputComplete(messageCode);
    }


    @OnClick(R.id.tv_gain_message_code)
    public void onViewClicked() {
        mPresenter.sendMessageCode(mPhooneNumber, Contast.TYPE_MESSAGE_CODE_REGISTER);
    }

    @Override
    public void resultEditData(List<RegisterInputEditBean> data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    public void clearData() {
        mTietInput1.setText(null);
        mTietInput2.setText(null);
        mTietInput3.setText(null);
        mTietInput4.setText(null);
        requestEditText1();
    }
}

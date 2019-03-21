package com.work.guaishouxingqiu.aboutball.login.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.login.adapter.RegisterInputAdapter;
import com.work.guaishouxingqiu.aboutball.login.bean.EventMessagePhone;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterInputEditBean;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterCodeContract;
import com.work.guaishouxingqiu.aboutball.login.presenter.RegisterCodePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:57
 * 更新时间: 2019/3/11 13:57
 * 描述: 注册输入验证码Fragment
 */
public class RegisterCodeFragment extends BaseFragment<RegisterCodePresenter> implements RegisterCodeContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_input)
    RecyclerView mRvInput;
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
    private RegisterInputAdapter mInputAdapter;
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
        mRvInput.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        mPresenter.start();
        registerEventBus();

    }

    @Override
    protected void initEvent() {

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
                if (isAllInputMessageCode() && mOnMessageCodeInputResult != null) {
                    String messageCode = DataUtils.checkData(mTietInput1.getText()).toString() +
                            DataUtils.checkData(mTietInput2.getText()).toString() +
                            DataUtils.checkData(mTietInput3.getText()).toString() +
                            DataUtils.checkData(mTietInput4.getText()).toString();
                    mOnMessageCodeInputResult.onInputComplete(messageCode);
                }
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
        LogUtils.w("setUserVisibleHint--",isVisibleToUser+"--");
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


    @OnClick(R.id.tv_gain_message_code)
    public void onViewClicked() {
        mPresenter.sendMessageCode(mPhooneNumber, Contast.TYPE_MESSAGE_CODE_REGISTER);
    }

    @Override
    public void resultEditData(List<RegisterInputEditBean> data) {
        if (mInputAdapter == null) {
            mInputAdapter = new RegisterInputAdapter(data);
            mRvInput.setAdapter(mInputAdapter);
        }
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
    }
}

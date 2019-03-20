package com.work.guaishouxingqiu.aboutball.login.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.login.contract.LoginContract;
import com.work.guaishouxingqiu.aboutball.login.presenter.LoginPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:19
 * 更新时间: 2019/3/7 11:19
 * 描述: 密码登录Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_clear_phone)
    ImageView mIvClearPhone;
    @BindView(R.id.tet_phone)
    TextInputEditText mTietPhone;
    @BindView(R.id.tv_gain_message_code)
    TextView mTvGainMessageCode;
    @BindView(R.id.iv_clear_message_code)
    ImageView mIvClearMessageCode;
    @BindView(R.id.tit_code)
    TextInputEditText mTietCode;
    @BindView(R.id.iv_clear_password)
    ImageView mIvClearPassword;
    @BindView(R.id.et_password)
    TextInputEditText mTIetPassword;
    @BindView(R.id.rl_message_code)
    ConstraintLayout mRlMessageCode;
    @BindView(R.id.rl_password)
    ConstraintLayout mRlPassword;
    private boolean isMessageCodeLogin = true;//默认是手机验证码登录
    @BindView(R.id.tv_login)
    TextView mTvLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTvLogin.setClickable(false);
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnSureViewClickListener(view -> {
            isMessageCodeLogin = !isMessageCodeLogin;
            mTitleView.mTvSure.setText(isMessageCodeLogin ? R.string.password_login : R.string.phone_shortcut_login);
            mRlMessageCode.setVisibility(isMessageCodeLogin ? View.VISIBLE : View.GONE);
            mRlPassword.setVisibility(isMessageCodeLogin ? View.GONE : View.VISIBLE);
        });
        UIUtils.checkClearImageStatus(mTietPhone, mIvClearPhone);
        UIUtils.checkClearImageStatus(mTietCode, mIvClearMessageCode);
        UIUtils.checkClearImageStatus(mTIetPassword, mIvClearPassword);

        UIUtils.clickClearEditData(mIvClearPhone, mTietPhone);
        UIUtils.clickClearEditData(mIvClearMessageCode, mTietCode);
        UIUtils.clickClearEditData(mIvClearPassword, mTIetPassword);

        inputEditTextStatus(mTietPhone);
        inputEditTextStatus(mTietCode);
        inputEditTextStatus(mTIetPassword);


    }

    private void inputEditTextStatus(@NonNull EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isCanLogin;


                if (isMessageCodeLogin) {
                    isCanLogin = DataUtils.isPhoneNumber(mTietPhone.getText().toString())
                            && DataUtils.isMessageCode(mTietCode.getText().toString());
                } else {
                    isCanLogin = DataUtils.isPhoneNumber(mTietPhone.getText().toString())
                            && DataUtils.isPassword(mTIetPassword.getText().toString());
                }
                mTvLogin.setClickable(isCanLogin);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mTvLogin.setBackground(isCanLogin ? ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_click_button) :
                            ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_default_button));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    @OnClick({R.id.tv_gain_message_code,
            R.id.tv_forget_password, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_gain_message_code:
                clickSendMessageCode();
                break;

            case R.id.tv_forget_password:
                break;

            case R.id.tv_login:
                clickLogin();
                break;
            case R.id.tv_register:
                clickRegister();
                break;
        }
    }

    private void clickRegister() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER);
    }

    /**
     * 发送验证码
     */
    private void clickSendMessageCode() {
        if (DataUtils.isPhoneNumber(DataUtils.checkData(mTietPhone.getText()).toString())) {
            mPresenter.sendMessageCode(mTietPhone.getText().toString(), Contast.TYPE_MESSAGE_CODE_LOGIN);
        } else {
            Toasts.with().showToast(R.string.please_sure_phone_number);
        }
    }

    /**
     * 点击登录
     */
    private void clickLogin() {
        RequestLoginBean requestLoginBean = new RequestLoginBean();
        requestLoginBean.type = isMessageCodeLogin ? 2 : 1;
        requestLoginBean.phone = DataUtils.checkData(mTietPhone.getText()).toString().trim();
        if (isMessageCodeLogin) {
            requestLoginBean.verificationCode = DataUtils.checkData(mTietCode.getText()).toString().trim();
        } else {
            requestLoginBean.password = DataUtils.checkData(mTIetPassword.getText()).toString().trim();
        }

        mPresenter.login(requestLoginBean);
    }


    @Override
    public void sendMessageCodeSucceedResult() {
        mPresenter.countDownTime(Contast.MESSAGE_COUNT_DOWN_LENGTH);
    }

    @Override
    public void loginSucceedResult(BaseBean<LoginResultBean> bean) {
        //先更新token，再去登录,获取用户信息
        if (bean.code == IApi.Code.SUCCEED) {
            if (bean.result != null) {
                UserManger.get().putToken(bean.result.id_token);
                mPresenter.loadUserAccount();
                mPresenter.loadUserAccountInfo();
            }
        } else if (bean.code == IApi.Code.USER_NO_EXIST) {

        }

    }

    @Override
    public void resultUserDataSucceed() {
        finish();
    }

    @Override
    public void countDownTimeComplete() {
        mTvGainMessageCode.setClickable(true);
        mTvGainMessageCode.setText(R.string.gain_message_code);
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(this, R.color.color_4));
    }

    @Override
    public void countDownTimeUpdate(long time) {
        mTvGainMessageCode.setClickable(false);
        mTvGainMessageCode.setText(getString(R.string.regain_load_time, String.valueOf(time)));
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(this, R.color.color_3));
    }
}

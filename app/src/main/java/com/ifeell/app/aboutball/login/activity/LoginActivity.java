package com.ifeell.app.aboutball.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.commonality.activity.LoginOrShareActivity;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.bean.RequestLoginBean;
import com.ifeell.app.aboutball.login.bean.ResultThreeLoginBean;
import com.ifeell.app.aboutball.login.contract.LoginContract;
import com.ifeell.app.aboutball.login.presenter.LoginPresenter;
import com.ifeell.app.aboutball.my.activity.UpdatePhoneActivity;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.Toasts;
import com.ifeell.app.aboutball.wxapi.WXEntryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:19
 * 更新时间: 2019/3/7 11:19
 * 描述: 密码登录Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_LOGIN)
public class LoginActivity extends LoginOrShareActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_clear_phone)
    ImageView mIvClearPhone;
    @BindView(R.id.tet_phone)
    AppCompatEditText mTietPhone;
    @BindView(R.id.tv_gain_message_code)
    TextView mTvGainMessageCode;
    @BindView(R.id.iv_clear_message_code)
    ImageView mIvClearMessageCode;
    @BindView(R.id.tit_code)
    AppCompatEditText mTietCode;
    @BindView(R.id.iv_clear_password)
    ImageView mIvClearPassword;
    @BindView(R.id.et_password)
    AppCompatEditText mTIetPassword;
    @BindView(R.id.rl_message_code)
    ConstraintLayout mRlMessageCode;
    @BindView(R.id.rl_password)
    ConstraintLayout mRlPassword;
    private boolean isMessageCodeLogin = true;//默认是手机验证码登录
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    public static final int REQUEST_CODE_LOGIN = 88;
    private HintDialog mBandDialog;
    private static final int REQUEST_REGISTER_CODE = 189;

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
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                mViewModel.clickBackForResult();
            }
        });

    }

    @Override
    public void onBackPressed() {
        mViewModel.clickBackForResult();
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
            R.id.tv_forget_password, R.id.tv_login, R.id.tv_register, R.id.tv_weichat})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_gain_message_code:
                clickSendMessageCode();
                break;

            case R.id.tv_forget_password:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER, ARouterConfig.Key.LOGIN_STATUS, Contast.LoginStatus.FORGET_PASSWORD);
                break;

            case R.id.tv_login:
                clickLogin();
                break;
            case R.id.tv_register:
                clickRegister();
                break;
            case R.id.tv_weichat:
                loginWeiChat(WXEntryActivity.WeiChatStatus.LOGIN);
                break;
        }
    }

    private void clickRegister() {
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_REGISTER, this, ARouterConfig.Key.LOGIN_STATUS, Contast.LoginStatus.REGISTER, LoginActivity.REQUEST_REGISTER_CODE);
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
                // mPresenter.loadUserAccount();
                mPresenter.loadUserAccountInfo();
            }
        } else if (bean.code == IApi.Code.USER_NO_EXIST) {

        }

    }

    @Override
    public void resultUserDataSucceed() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void countDownTimeComplete() {
        mTvGainMessageCode.setClickable(true);
        mTvGainMessageCode.setText(R.string.gain_message_code);
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(this, R.color.color_4));
    }

    @Override
    public void resultMessageCode() {

    }

    @Override
    public void countDownTimeUpdate(long time) {
        mTvGainMessageCode.setClickable(false);
        mTvGainMessageCode.setText(getString(R.string.regain_load_time, String.valueOf(time)));
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(this, R.color.color_3));
    }

    /**
     * 第三方登录如果绑定了手机号，就用返回的token，如果没有绑定就要去拿绑定手机号的token
     *
     * @param bean
     * @param signCode
     */
    @Override
    public void resultOtherLogin(ResultThreeLoginBean bean, String signCode) {
        // UserManger.get().putToken(bean.id_token);

        if (bean.bingPhone == Contast.LoginStatus.LOGIN_BING_PHONE) {
            // mPresenter.loadUserAccount();
            UserManger.get().putToken(bean.id_token);
            mPresenter.loadUserAccountInfo();
        } else if (bean.bingPhone == Contast.LoginStatus.LOGIN_UNBING_PHONE) {
            UserManger.get().putTemporaryToken(bean.id_token);
            showBandPhoneDialog(signCode);
        }

    }

    public void showBandPhoneDialog(String signCode) {
        if (mBandDialog == null) {
            mBandDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_not_band_phone_number_please_band)
                    .setShowSingButton(false)
                    .setSures(R.string.go_band)
                    .builder();
            mBandDialog.setCancelable(false);
            mBandDialog.setCanceledOnTouchOutside(false);
        }
        if (!mBandDialog.isShowing()) {
            mBandDialog.show();
        }
        mBandDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mViewModel.startActivityToUpdatePhoneForResult(signCode, Contast.LoginStatus.BAND_PHONE);
                mBandDialog.dismiss();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                UserManger.get().removeTemporaryToken();
                mBandDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LoginActivity.REQUEST_REGISTER_CODE:
                case UpdatePhoneActivity.REQUEST_CODE_BAND_PHONE:
                    mPresenter.loadUserAccountInfo();
                    break;
                default:
                    break;
            }
        }

    }
}

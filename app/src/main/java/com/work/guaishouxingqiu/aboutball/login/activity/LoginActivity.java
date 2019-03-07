package com.work.guaishouxingqiu.aboutball.login.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.login.contract.LoginContract;
import com.work.guaishouxingqiu.aboutball.login.presenter.LoginPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    RelativeLayout mRlMessageCode;
    @BindView(R.id.rl_password)
    RelativeLayout mRlPassword;
    private boolean isMessageCodeLogin = true;//默认是手机验证码登录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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

        UIUtils.clickClearEditData(mIvClearPhone,mTietPhone);
        UIUtils.clickClearEditData(mIvClearMessageCode,mTietCode);
        UIUtils.clickClearEditData(mIvClearPassword,mTIetPassword);
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    @OnClick({R.id.iv_clear_phone, R.id.tv_gain_message_code, R.id.iv_clear_message_code, R.id.tv_forget_password, R.id.iv_clear_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear_phone:
                break;
            case R.id.tv_gain_message_code:
                break;
            case R.id.iv_clear_message_code:
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.iv_clear_password:
                break;
        }
    }
}

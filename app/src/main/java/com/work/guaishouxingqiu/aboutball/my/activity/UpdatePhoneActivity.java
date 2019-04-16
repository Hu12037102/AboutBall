package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePhoneBean;
import com.work.guaishouxingqiu.aboutball.my.contract.UpdatePhoneContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.UpdatePhonePresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 10:46
 * 更新时间: 2019/4/16 10:46
 * 描述:更换绑手机号
 */
@Route(path = ARouterConfig.Path.ACTIVITY_UPDATE_PHONE)
public class UpdatePhoneActivity extends BaseActivity<UpdatePhonePresenter> implements UpdatePhoneContract.View {
    @BindView(R.id.acet_phone)
    AppCompatEditText mAcetPhone;
    @BindView(R.id.iv_clear_phone)
    ImageView mIvClearPhone;
    @BindView(R.id.acet_code)
    AppCompatEditText mAcetCode;
    @BindView(R.id.iv_clear_message_code)
    ImageView mIvClearCode;
    @BindView(R.id.tv_gain_message_code)
    TextView mTvGainMessageCode;
    @BindView(R.id.tv_sures)
    TextView mTvSure;

    private boolean isCanLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void initView() {
       /* if (UserManger.get().isLogin()) {

            mAcetPhone.setText(UserManger.get().getPhone());
            mAcetPhone.setSelection(UserManger.get().getPhone().length());
        }
        mIvClearPhone.setVisibility(DataUtils.isEmpty(DataUtils.getEditDetails(mAcetPhone))?
                View.GONE:View.VISIBLE);*/
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        UIUtils.checkClearImageStatus(mAcetPhone, mIvClearPhone);
        UIUtils.checkClearImageStatus(mAcetCode, mIvClearCode);
        mAcetPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isCanLogin = DataUtils.isPhoneNumber(DataUtils.getEditDetails(mAcetPhone))
                        && DataUtils.isMessageCode(DataUtils.getEditDetails(mAcetCode));
                mTvSure.setClickable(isCanLogin);
                mTvSure.setBackgroundResource(isCanLogin ? R.drawable.shape_click_button :
                        R.drawable.shape_default_button);
                LogUtils.w("addTextChangedListener--", isCanLogin + "--");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAcetCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                isCanLogin = DataUtils.isPhoneNumber(DataUtils.getEditDetails(mAcetPhone))
                        && DataUtils.isMessageCode(DataUtils.getEditDetails(mAcetCode));
                mTvSure.setClickable(isCanLogin);
                mTvSure.setBackgroundResource(isCanLogin ? R.drawable.shape_click_button :
                        R.drawable.shape_default_button);
                LogUtils.w("addTextChangedListener---", isCanLogin + "--");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected UpdatePhonePresenter createPresenter() {
        return new UpdatePhonePresenter(this);
    }


    @OnClick({R.id.iv_clear_phone, R.id.iv_clear_message_code, R.id.tv_gain_message_code, R.id.tv_sures})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear_phone:
                UIUtils.clickClearEditData(mIvClearPhone, mAcetPhone);
                break;
            case R.id.iv_clear_message_code:
                UIUtils.clickClearEditData(mIvClearCode, mAcetCode);
                break;
            case R.id.tv_gain_message_code:
                clickSendMessage();
                break;
            case R.id.tv_sures:
                clickSure();
                break;
        }
    }

    private void clickSure() {
        if (UserManger.get().getPhone().equals(DataUtils.getEditDetails(mAcetPhone))) {
            Toasts.with().showToast(R.string.you_band_this_phone);
        } else {
            RequestUpdatePhoneBean bean = new RequestUpdatePhoneBean();
            bean.phone = DataUtils.getEditDetails(mAcetPhone);
            bean.verificationCode = DataUtils.getEditDetails(mAcetCode);
            mPresenter.updatePhone(bean);
        }
    }

    private void clickSendMessage() {
        if (DataUtils.isPhoneNumber(DataUtils.checkData(mAcetPhone.getText()).toString())) {
            if (DataUtils.getEditDetails(mAcetPhone).equals(UserManger.get().getPhone())) {
                Toasts.with().showToast(R.string.you_band_this_phone);
            } else {
                mPresenter.sendMessageCode(mAcetPhone.getText().toString(), Contast.TYPE_MESSAGE_CODE_RESET_PASSWORD);
            }

        } else {
            Toasts.with().showToast(R.string.please_sure_phone_number);
        }
    }

    @Override
    public void sendMessageCodeSucceedResult() {
        mPresenter.countDownTime(Contast.MESSAGE_COUNT_DOWN_LENGTH);
    }

    @Override
    public void countDownTimeUpdate(long time) {
        mTvGainMessageCode.setClickable(false);
        mTvGainMessageCode.setText(getString(R.string.regain_load_time, String.valueOf(time)));
        mTvGainMessageCode.setTextColor(ContextCompat.getColor(this, R.color.color_3));
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
    public void updatePhoneSucceed(String phoneNumber) {
        UserManger.get().putPhone(phoneNumber);
    }




}

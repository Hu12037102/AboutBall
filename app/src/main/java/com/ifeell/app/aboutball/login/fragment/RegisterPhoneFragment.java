package com.ifeell.app.aboutball.login.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.login.bean.EventMessagePhone;
import com.ifeell.app.aboutball.login.contract.RegisterPhoneContract;
import com.ifeell.app.aboutball.login.presenter.RegisterPhonePresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:33
 * 更新时间: 2019/3/11 13:33
 * 描述: 注册Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_REGISTER_PHONE)
public class RegisterPhoneFragment extends BaseFragment<RegisterPhonePresenter> implements
        RegisterPhoneContract.View {
    @BindView(R.id.tiet_phone)
    AppCompatEditText mTietPhone;
    @BindView(R.id.iv_clear_register_phone)
    ImageView mIvClearPhone;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private OnNextClickListener mOnNextClickListener;
    private int mStatus;

    public void setOnNextClickListener(OnNextClickListener onNextClickListener) {
        this.mOnNextClickListener = onNextClickListener;
    }


    public static RegisterPhoneFragment newInstance() {
        return new RegisterPhoneFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_phone;
    }

    @Override
    protected void initView() {
        mTvNext.setClickable(false);
    }

    @Override
    protected void initData() {
        mStatus = mBundle.getInt(ARouterConfig.Key.LOGIN_STATUS, Contast.LoginStatus.REGISTER);
        switch (mStatus) {
            case Contast.LoginStatus.REGISTER:
                mTvTitle.setText(R.string.register);
                break;
            case Contast.LoginStatus.FORGET_PASSWORD:
                mTvTitle.setText(R.string.forget_password);
                break;
            default:
                break;
        }
    }


    @Override
    protected void initEvent() {
        UIUtils.clickClearEditData(mIvClearPhone, mTietPhone);
        UIUtils.checkClearImageStatus(mTietPhone, mIvClearPhone);
        mTietPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DataUtils.isPhoneNumber(DataUtils.checkData(mTietPhone.getText()).toString().trim())) {
                    mTvNext.setBackgroundResource(R.drawable.shape_click_button);
                    mTvNext.setClickable(true);
                } else {
                    mTvNext.setBackgroundResource(R.drawable.shape_default_button);
                    mTvNext.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected RegisterPhonePresenter createPresenter() {
        return new RegisterPhonePresenter(this);
    }

    @Override
    public void sendMessageCodeSucceedResult() {
        EventBus.getDefault().post(new EventMessagePhone(DataUtils.checkData(mTietPhone.getText()).toString()));
        if (mOnNextClickListener != null) {
            mOnNextClickListener.onClickNext(DataUtils.checkData(mTietPhone.getText()).toString().trim());
        }
    }

    @Override
    public void countDownTimeUpdate(long time) {

    }

    @Override
    public void countDownTimeComplete() {

    }

    @Override
    public void resultMessageCode() {

    }


    @OnClick({R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                clickNext();
                break;
        }
    }

    private void clickNext() {
        mPresenter.sendMessageCode(DataUtils.checkData(mTietPhone.getText()).toString().trim(),
                mStatus == Contast.LoginStatus.REGISTER ? Contast.TYPE_MESSAGE_CODE_REGISTER : Contast.TYPE_MESSAGE_CODE_RESET_PASSWORD);

    }

    public interface OnNextClickListener {
        void onClickNext(@NonNull String phoneNumber);
    }

}

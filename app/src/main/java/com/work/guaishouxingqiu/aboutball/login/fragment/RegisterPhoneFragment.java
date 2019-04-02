package com.work.guaishouxingqiu.aboutball.login.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.login.bean.EventMessagePhone;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterPhoneContract;
import com.work.guaishouxingqiu.aboutball.login.presenter.RegisterPhonePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

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
    private OnNextClickListener mOnNextClickListener;

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
        mPresenter.sendMessageCode(DataUtils.checkData(mTietPhone.getText()).toString().trim(), Contast.TYPE_MESSAGE_CODE_REGISTER);

    }

    public interface OnNextClickListener {
        void onClickNext(@NonNull String phoneNumber);
    }

}

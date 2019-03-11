package com.work.guaishouxingqiu.aboutball.login.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterPasswordContract;
import com.work.guaishouxingqiu.aboutball.login.presenter.RegisterPasswordPresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 14:21
 * 更新时间: 2019/3/11 14:21
 * 描述: 注册输入密码Fragment
 */
public class RegisterPasswordFragment extends BaseFragment<RegisterPasswordPresenter>
        implements RegisterPasswordContract.View {

    @BindView(R.id.tiet_password)
    TextInputEditText mTietPassword;
    @BindView(R.id.iv_clear_password)
    ImageView mIvClearPassword;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    public void setOnInputPasswordResult(OnInputPasswordResult onInputPasswordResult) {
        this.mOnInputPasswordResult = onInputPasswordResult;
    }

    private OnInputPasswordResult mOnInputPasswordResult;

    public interface OnInputPasswordResult{
        void onInputPassword(@NonNull String password);
    }
    public static RegisterPasswordFragment newInstance() {
        return new RegisterPasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        UIUtils.checkClearImageStatus(mTietPassword,mIvClearPassword);
        UIUtils.clickClearEditData(mIvClearPassword,mTietPassword);
        mTietPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DataUtils.isPassword(DataUtils.checkData(mTietPassword.getText()).toString().trim())){
                    mTvRegister.setClickable(true);
                    mTvRegister.setBackgroundResource(R.drawable.shape_click_button);
                }else {
                    mTvRegister.setClickable(false);
                    mTvRegister.setBackgroundResource(R.drawable.shape_default_button);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected RegisterPasswordPresenter createPresenter() {
        return new RegisterPasswordPresenter(this);
    }


    @OnClick(R.id.tv_register)
    public void onViewClicked() {
        if (mOnInputPasswordResult != null){
            mOnInputPasswordResult.onInputPassword(DataUtils.checkData(mTietPassword.getText()).toString());
        }
    }
}

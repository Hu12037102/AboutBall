package com.work.guaishouxingqiu.aboutball.my.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePasswordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.UpdatePasswordContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.UpdatePasswordPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:51
 * 更新时间: 2019/4/16 14:51
 * 描述:修改密码
 */
@Route(path = ARouterConfig.Path.ACTIVITY_UPDATE_PASSWORD)
public class UpdatePasswordActivity extends BaseActivity<UpdatePasswordPresenter> implements UpdatePasswordContract.View {
    @BindView(R.id.et_password_1)
    AppCompatEditText mAcetPassword1;
    @BindView(R.id.iv_clear_password_1)
    ImageView mIvClearPassword1;
    @BindView(R.id.et_password_2)
    AppCompatEditText mAcetPassword2;
    @BindView(R.id.iv_clear_password_2)
    ImageView mIvClearPassword2;
    @BindView(R.id.et_password_3)
    AppCompatEditText mAcetPassword3;
    @BindView(R.id.iv_clear_password_3)
    ImageView mIvClearPassword3;
    @BindView(R.id.tv_sures)
    TextView mTvSure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        UIUtils.checkClearImageStatus(mAcetPassword1, mIvClearPassword1);
        UIUtils.checkClearImageStatus(mAcetPassword2, mIvClearPassword2);
        UIUtils.checkClearImageStatus(mAcetPassword3, mIvClearPassword3);
        this.setInputPasswordSucceed(mAcetPassword1);
        this.setInputPasswordSucceed(mAcetPassword2);
        this.setInputPasswordSucceed(mAcetPassword3);
    }


    private void setInputPasswordSucceed(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DataUtils.isPassword(DataUtils.getEditDetails(mAcetPassword1)) &&
                        DataUtils.isPassword(DataUtils.getEditDetails(mAcetPassword2)) &&
                        DataUtils.isPassword(DataUtils.getEditDetails(mAcetPassword3))) {
                    mTvSure.setClickable(true);
                    mTvSure.setBackgroundResource(R.drawable.shape_click_button);
                } else {
                    mTvSure.setClickable(false);
                    mTvSure.setBackgroundResource(R.drawable.shape_default_button);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected UpdatePasswordPresenter createPresenter() {
        return new UpdatePasswordPresenter(this);
    }


    @OnClick({R.id.iv_clear_password_1, R.id.iv_clear_password_2, R.id.iv_clear_password_3, R.id.tv_sures})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear_password_1:
                UIUtils.clickClearEditData(mIvClearPassword1, mAcetPassword1);
                break;
            case R.id.iv_clear_password_2:
                UIUtils.clickClearEditData(mIvClearPassword2, mAcetPassword2);
                break;
            case R.id.iv_clear_password_3:
                UIUtils.clickClearEditData(mIvClearPassword3, mAcetPassword3);
                break;
            case R.id.tv_sures:
                clickSure();
                break;
        }
    }

    private void clickSure() {
        if (DataUtils.getEditDetails(mAcetPassword2).equals(DataUtils.getEditDetails(mAcetPassword3))){
            RequestUpdatePasswordBean bean = new RequestUpdatePasswordBean();
            bean.oldPassword = DataUtils.getEditDetails(mAcetPassword1);
            bean.newPassword = DataUtils.getEditDetails(mAcetPassword2);
            mPresenter.updatePassword(bean);
        }else {
            Toasts.with().showToast(R.string.new_password_input_not_fit);
        }
    }

    @Override
    public void resultPasswordSucceed(String token) {
        UserManger.get().putToken(token);
        HintDialog hintDialog = new HintDialog.Builder(this)
                .setTitle(R.string.hint)
                .setBody(R.string.you_update_password_succeed)
                .setSure(R.string.sure)
                .setCancelTouchOut(false)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            hintDialog.dismiss();
            finish();
        });

    }
}

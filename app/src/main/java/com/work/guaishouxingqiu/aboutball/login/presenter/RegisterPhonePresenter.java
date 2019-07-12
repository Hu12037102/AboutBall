package com.work.guaishouxingqiu.aboutball.login.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.login.contract.RegisterPhoneContract;
import com.work.guaishouxingqiu.aboutball.login.model.RegisterPhoneModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:38
 * 更新时间: 2019/3/11 13:38
 * 描述: 注册输入手机P
 */
public class RegisterPhonePresenter extends MessagePresenter<RegisterPhoneContract.View, RegisterPhoneModel>
        implements RegisterPhoneContract.Presenter {
    @Override
    protected RegisterPhoneModel createModel() {
        return new RegisterPhoneModel();
    }

    public RegisterPhonePresenter(@NonNull RegisterPhoneContract.View view) {
        super(view);
    }



    @Override
    public void start() {

    }
}

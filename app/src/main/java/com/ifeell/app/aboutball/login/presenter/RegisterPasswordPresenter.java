package com.ifeell.app.aboutball.login.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.login.contract.RegisterPasswordContract;
import com.ifeell.app.aboutball.login.model.RegisterPasswordModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 14:19
 * 更新时间: 2019/3/11 14:19
 * 描述:注册输入密码P
 */
public class RegisterPasswordPresenter extends BasePresenter<RegisterPasswordContract.View,RegisterPasswordModel>
implements RegisterPasswordContract.Presenter{
    public RegisterPasswordPresenter(@NonNull RegisterPasswordContract.View view) {
        super(view);
    }

    @Override
    protected RegisterPasswordModel createModel() {
        return new RegisterPasswordModel();
    }

    @Override
    public void start() {

    }
}

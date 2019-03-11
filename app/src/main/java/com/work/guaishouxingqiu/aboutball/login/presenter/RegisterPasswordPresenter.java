package com.work.guaishouxingqiu.aboutball.login.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterContract;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterPasswordContract;
import com.work.guaishouxingqiu.aboutball.login.model.RegisterPasswordModel;

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

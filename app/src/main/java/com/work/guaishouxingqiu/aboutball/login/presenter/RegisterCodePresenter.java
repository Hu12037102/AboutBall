package com.work.guaishouxingqiu.aboutball.login.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.login.contract.RegisterCodeContract;
import com.work.guaishouxingqiu.aboutball.login.model.RegisterCodeModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:55
 * 更新时间: 2019/3/11 13:55
 * 描述:注册输入验证码P
 */
public class RegisterCodePresenter extends MessagePresenter<RegisterCodeContract.View, RegisterCodeModel>
        implements RegisterCodeContract.Presenter {
    public RegisterCodePresenter(@NonNull RegisterCodeContract.View view) {
        super(view);
    }

    @Override
    protected RegisterCodeModel createModel() {
        return new RegisterCodeModel();
    }

    @Override
    public void start() {
        mModel.start(data -> {
            if (mView != null) {
                mView.resultEditData(data);
            }
        });
    }
}

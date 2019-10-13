package com.ifeell.app.aboutball.splash.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.splash.contract.WelcomeContract;
import com.ifeell.app.aboutball.splash.model.WelcomeModel;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.start.presenter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public class WelcomePresenter extends BasePresenter<WelcomeContract.View, WelcomeModel> implements
        WelcomeContract.Presenter {
    public WelcomePresenter(@NonNull WelcomeContract.View view) {
        super(view);
    }

    @Override
    protected WelcomeModel createModel() {
        return new WelcomeModel();
    }

    @Override
    public void start() {

    }
}

package com.work.guaishouxingqiu.aboutball.start.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.start.contract.WelcomeContract;
import com.work.guaishouxingqiu.aboutball.start.model.WelcomeModel;

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

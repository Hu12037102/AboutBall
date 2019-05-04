package com.work.guaishouxingqiu.aboutball.start.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.start.contract.GuideContract;
import com.work.guaishouxingqiu.aboutball.start.model.GuideModel;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.start.presenter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}引导页P
 *
 * @author ：
 */
public class GuidePresenter extends BasePresenter<GuideContract.View, GuideModel>implements GuideContract.Presenter {

    public GuidePresenter(@NonNull GuideContract.View view) {
        super(view);
    }

    @Override
    protected GuideModel createModel() {
        return new GuideModel();
    }

    @Override
    public void start() {

    }
}

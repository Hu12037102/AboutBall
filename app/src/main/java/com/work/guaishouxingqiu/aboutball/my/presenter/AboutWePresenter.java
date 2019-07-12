package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AboutWeContract;
import com.work.guaishouxingqiu.aboutball.my.model.AboutWeModel;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my.presenter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}关于我们P
 *
 * @author ：
 */
public class AboutWePresenter extends BasePresenter<AboutWeContract.View, AboutWeModel> implements AboutWeContract.Presenter {
    public AboutWePresenter(@NonNull AboutWeContract.View view) {
        super(view);
    }

    @Override
    protected AboutWeModel createModel() {
        return new AboutWeModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void updateApkInfo(String version) {

        mModel.updateApkInfo(version, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<ResultUpdateApkBean>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<ResultUpdateApkBean>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result.code == IApi.Code.SUCCEED && t.result.result != null) {
                    mView.resultApkInfo(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}

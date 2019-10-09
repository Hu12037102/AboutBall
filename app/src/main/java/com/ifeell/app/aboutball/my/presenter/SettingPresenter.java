package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.contract.SettingContract;
import com.ifeell.app.aboutball.my.model.SettingModel;

import java.text.NumberFormat;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 15:28
 * 更新时间: 2019/4/1 15:28
 * 描述:设置P
 */
public class SettingPresenter extends BasePresenter<SettingContract.View, SettingModel>
        implements SettingContract.Presenter {
    public SettingPresenter(@NonNull SettingContract.View view) {
        super(view);
    }

    @Override
    protected SettingModel createModel() {
        return new SettingModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void getFileSize() {
        mModel.getFileSize(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Long fileSize) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(2);

                String size = numberFormat.format((double) fileSize / 1024d / 1024d);
                mView.resultFileSize(Double.valueOf(size) == 0 ? "" : size + "M");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

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
        },false));


    }
}

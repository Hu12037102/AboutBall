package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.contract.SettingContract;
import com.work.guaishouxingqiu.aboutball.my.model.SettingModel;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.io.File;
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
    public void  updateApkInfo(String version ){

    mModel.updateApkInfo(version,new BaseObserver<>(true, this, new BaseObserver.Observer<ResultUpdateApkBean>() {
        @Override
        public void onNext(BaseBean<ResultUpdateApkBean> t) {
            if (t.code == IApi.Code.SUCCEED){

            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }));


    }
}

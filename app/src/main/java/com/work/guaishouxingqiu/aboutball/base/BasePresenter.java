package com.work.guaishouxingqiu.aboutball.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:28
 * 更新时间: 2019/3/4 13:28
 * 描述:
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter, IBaseModelCallback {
    public int mPageNum = Contast.DEFAULT_PAGE_NUM;
    public int mPageSize = Contast.DEFAULT_PAGE_SIZE;
    public boolean isRefresh = true;
    protected CompositeDisposable mCompositeDisposable;
    public V mView;
    protected M mModel;
    private HintDialog mUpdateDialog;


    protected abstract M createModel();

    public BasePresenter(@NonNull V view) {
        mView = DataUtils.checkData(view);
        mCompositeDisposable = new CompositeDisposable();
        mModel = createModel();


    }

    public void loadOssToken() {
        mModel.loadOSSToken(new BaseObserver<>(true, this, new BaseObserver.Observer<OSSToken>() {
            @Override
            public void onNext(BaseBean<OSSToken> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultOSSToken(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    private void defaultShowResult(BaseBean baseBean) {
        if (mView != null) {
            mView.showToast(baseBean.message);
        }
    }

    @Override
    public void deathPresenter() {


        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        if (mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }

        if (mView != null) {
            mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
    }


    public void loadRefereeLevelData() {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        String refereeJson = sp.getString(SharedPreferencesHelp.KEY_REFEREE_CACHE_LIST);
        if (DataUtils.isEmpty(refereeJson)) {
            mModel.loadRefereeLevelData(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultRefereeLevelBean>>() {
                @Override
                public void onNext(BaseBean<List<ResultRefereeLevelBean>> t) {
                    sp.putObject(SharedPreferencesHelp.KEY_REFEREE_CACHE_LIST, new Gson().toJson(t.result));
                    LogUtils.w("SharedPreferencesHelp--", new Gson().toJson(t.result));
                    mView.resultLevelData(t.result);
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
        } else {
            LogUtils.w("SharedPreferencesHelp---", refereeJson);
            Gson gson = new Gson();
            List<ResultRefereeLevelBean> data = gson.fromJson(refereeJson, new TypeToken<List<ResultRefereeLevelBean>>() {
            }.getType());
            mView.resultLevelData(data);
        }
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
        }));
    }





}

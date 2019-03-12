package com.work.guaishouxingqiu.aboutball.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.LoadingView;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/5 14:07
 * 更新时间: 2019/3/5 14:07
 * 描述:基类Fragment
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    protected P mPresenter;
    protected View mRootView;
    private Unbinder mBinder;
    private LoadingView mLoadingView;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract P createPresenter();

    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    protected void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = createPresenter();
        initView();
        initData();
        initEvent();

    }

    @Override
    public void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(DataUtils.checkData(getActivity()));
        }
        mLoadingView.showLoadingView();
    }

    @Override
    public void dismissLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.dismissLoadingView();
        }
    }

    @Override
    public void showToast(@NonNull String text) {
        Toasts.with().showToast(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.deathPresenter();
        mBinder.unbind();
    }

    protected void $startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }

    @Override
    public void resultBaseData(@NonNull BaseBean baseBean) {
        UIUtils.resultBaseData(baseBean,DataUtils.checkData(getActivity()));
    }
}

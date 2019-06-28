package com.work.guaishouxingqiu.aboutball.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;
import com.work.guaishouxingqiu.aboutball.permission.PermissionFragment;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.LoadingView;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/5 14:07
 * 更新时间: 2019/3/5 14:07
 * 描述:基类Fragment
 */
public abstract class BaseFragment<P extends BasePresenter> extends PermissionFragment implements IBaseView {
    protected P mPresenter;
    protected View mRootView;
    private Unbinder mBinder;
    private LoadingView mLoadingView;
    protected Bundle mBundle;
    protected Context mContext;
    protected ViewModel mViewModel;

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

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
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
        mViewModel = ViewModel.createViewModel(getActivity());
        mPresenter = createPresenter();
        mContext = getContext();
        mBundle = getArguments();
        if (mBundle == null) {
            DataUtils.checkData(getActivity()).finish();
            return;
        }
        initPermission();
    }

    protected void initPermission() {
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
        mPresenter.deathPresenter();
        mViewModel.onDestroy();
        super.onDestroyView();
        mBinder.unbind();
    }

    protected void $startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }

    @Override
    public void resultBaseData(@NonNull BaseBean baseBean) {
        mViewModel.resultBaseData(baseBean);
    }

    @Override
    public void resultOSSToken(OSSToken ossBean) {

    }

    @Override
    public void resultLevelData(List<ResultRefereeLevelBean> data) {

    }

    @Override
    public void resultApkInfo(ResultUpdateApkBean result) {
        mViewModel.showUpdateDialog(mContext, result);
    }

    @Override
    public void resultRefereeStatus(Integer status) {

    }

    @Override
    public void resultWeiChatSing(ResultWeiChatSingBean bean) {
        mViewModel.weiChatPay(bean);
    }

    @Override
    public void resultRefundCauseData(List<ResultRefundCauseBean> data) {

    }

    @Override
    public void resultTeamDetails(ResultBallDetailsBean bean) {

    }

    @Override
    public void resultJoinTeamSucceed() {

    }

    @Override
    public void resultDynamicCommentsSucceed() {

    }
    @Override
    public void resultAttentionTweetStatus(int position) {

    }
    @Override
    public void resultDianZanStatus(int position) {

    }
    @Override
    public void resultDeleteDynamicSucceed(int position) {

    }
    @Override
    public void resultFansFocus(ResultFansFocusBean bean) {

    }
}

package com.ifeell.app.aboutball.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.base.bean.OSSToken;
import com.ifeell.app.aboutball.base.bean.ResultSureOrderDialogBean;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameDataResultBean;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultDynamicNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultFansFocusBean;
import com.ifeell.app.aboutball.my.bean.ResultMyMessageBean;
import com.ifeell.app.aboutball.my.bean.ResultRefereeLevelBean;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.my.bean.ResultSystemNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.bean.ResultWeiChatSingBean;
import com.ifeell.app.aboutball.permission.PermissionFragment;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.ResultNotBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.weight.LoadingView;
import com.ifeell.app.aboutball.weight.Toasts;

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
    private SmartRefreshLayout mRefreshLayout;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract P createPresenter();

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        initOther();
    }

    protected void initOther() {
        mRefreshLayout = getRefreshLayout();
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    loadRefreshData(true);
                }
            });
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    loadRefreshData(false);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    loadRefreshData(true);
                }
            });
        }

    }

    protected SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
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

    protected void loadRefreshData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (mRefreshLayout != null) {
            if (isRefresh) {
                mRefreshLayout.finishRefresh();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
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

    @Override
    public void resultShareCommunityDynamic() {

    }

    @Override
    public void resultGameResultDetails(List<ResultGameDataResultBean> data) {
    }

    @Override
    public void resultSureUseOrder(long orderId) {

    }

    @Override
    public void resultRedPointData(List<ResultRedPointInfoBean> data) {

    }

    @Override
    public void resultMyMessageList(List<ResultMyMessageBean> data) {

    }

    @Override
    public void resultSystemNotificationData(List<ResultSystemNotificationBean> data) {

    }

    @Override
    public void resultDynamicNotificationData(List<ResultDynamicNotificationBean> data) {

    }

    @Override
    public void resultClearRedPoint(boolean isSucceed) {

    }

    @Override
    public void resultCreateBallOrderId(String orderId) {

    }

    @Override
    public void resultNotBookData(List<ResultNotBookBean> data) {

    }

    @Override
    public void resultSettingPasswordSucceed(String token) {

    }

    @Override
    public void resultCanUserVenueList(List<ResultVenueData> data) {

    }

    @Override
    public void resultSureOrderDialog(BaseBean<ResultSureOrderDialogBean> bean) {

    }
    @Override
    public void resultGoodStatus(int goodStatus) {

    }
}

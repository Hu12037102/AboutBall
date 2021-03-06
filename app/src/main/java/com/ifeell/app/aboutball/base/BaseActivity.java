package com.ifeell.app.aboutball.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.MotionEvent;

import com.example.item.util.ScreenUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.bean.OSSToken;
import com.ifeell.app.aboutball.base.bean.ResultSureOrderDialogBean;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.game.bean.ResultGameDataResultBean;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.media.bean.MediaSelectorFile;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultDynamicNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultFansFocusBean;
import com.ifeell.app.aboutball.my.bean.ResultMyMessageBean;
import com.ifeell.app.aboutball.my.bean.ResultRefereeLevelBean;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.my.bean.ResultSystemNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.bean.ResultWeiChatSingBean;
import com.ifeell.app.aboutball.other.ActivityManger;
import com.ifeell.app.aboutball.venue.bean.ResultNotBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.weight.LoadingView;
import com.ifeell.app.aboutball.weight.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import utils.bean.ImageConfig;
import utils.task.CompressImageTask;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:08
 * 更新时间: 2019/3/4 13:08
 * 描述: Activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends LocationActivity implements IBaseView {
    protected P mPresenter;
    protected SystemBarTintManager mStatusBarManger;
    private Unbinder mBinder;
    private LoadingView mLoadingView;
    protected Intent mIntent;
    protected boolean mIsCompressVideoing;
    private SmartRefreshLayout mRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mIntent = getIntent();
        mBinder = ButterKnife.bind(this);

        initStatusColor();
        initPermission();
        initSellingPoint();
        ActivityManger.get().addActivity(this);


    }

    protected void initSellingPoint() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);

        //Bugtags.onRestart(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        StatService.onStop(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Bugtags.onRestart(this);
        StatService.onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    public BaseApplication getBaseApplication() {
        return (BaseApplication) getApplication();
    }

    /**
     * 设置状态栏颜色
     */
    protected void initStatusColor() {
        ScreenUtils.setStatusTextColor(getWindow().getDecorView(), true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarManger = new SystemBarTintManager(this);
            mStatusBarManger.setStatusBarTintEnabled(true);
            mStatusBarManger.setNavigationBarTintEnabled(true);
            mStatusBarManger.setStatusBarTintResource(R.color.colorWhite);
        }
    }

    public void initPermission() {

        if (mIntent == null) {
            finish();
            return;
        }
        mPresenter = createPresenter();
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

    protected SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract P createPresenter();

    @Override
    public void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(this);
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
    protected void onDestroy() {
        if (mViewModel != null) {
            mViewModel.onDestroy();
        }
        if (mPresenter != null) {
            mPresenter.deathPresenter();
        }
        super.onDestroy();
        mBinder.unbind();
        ActivityManger.get().removeActivity(this.getClass());
    }


    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            EventBus.getDefault().removeStickyEvent(this);
        }
    }

    /**
     * 通用事件回调
     *
     * @param baseBean
     */
    @Override
    public void resultBaseData(@NonNull BaseBean baseBean) {
        mViewModel.resultBaseData(baseBean);
    }

    protected void compressImage(List<MediaSelectorFile> mediaFileData, CompressImageTask.OnImagesResult onImagesResult) {
        final List<ImageConfig> configData = new ArrayList<>();
        for (int i = 0; i < mediaFileData.size(); i++) {
            configData.add(MediaSelectorFile.thisToDefaultImageConfig(mediaFileData.get(i)));
        }

        CompressImageTask.get().compressImages(this, configData, onImagesResult);

    }


    @Override
    public void onBackPressed() {
        if (mIsCompressVideoing) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void resultOSSToken(OSSToken ossBean) {

    }

    @Override
    public void resultLevelData(List<ResultRefereeLevelBean> data) {

    }

    protected void clickBackForResult() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultApkInfo(ResultUpdateApkBean result) {
        mViewModel.showUpdateDialog(this, result);
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

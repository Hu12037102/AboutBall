package com.work.guaishouxingqiu.aboutball.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.example.item.util.ScreenUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.media.bean.MediaSelectorFile;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;
import com.work.guaishouxingqiu.aboutball.other.ActivityManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.LoadingView;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

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
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView {
    protected P mPresenter;
    protected SystemBarTintManager mStatusBarManger;
    private Unbinder mBinder;
    private LoadingView mLoadingView;
    protected Intent mIntent;
    protected ViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mIntent = getIntent();
        mBinder = ButterKnife.bind(this);
        mViewModel = ViewModel.createViewModel(this);
        initStatusColor();
        initPermission();
        ActivityManger.get().addActivity(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onResume(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
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
        EventBus.getDefault().register(this);
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

    protected void compressImage(List<MediaSelectorFile> mMediaFileData, CompressImageTask.OnImagesResult onImagesResult) {
        final List<ImageConfig> configData = new ArrayList<>();
        for (int i = 0; i < mMediaFileData.size(); i++) {
            configData.add(MediaSelectorFile.thisToDefaultImageConfig(mMediaFileData.get(i)));
        }

        CompressImageTask.get().compressImages(this, configData, onImagesResult);

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
}

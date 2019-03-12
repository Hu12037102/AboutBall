package com.work.guaishouxingqiu.aboutball.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bugtags.library.Bugtags;
import com.example.item.util.ScreenUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.other.ActivityManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.LoadingView;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:08
 * 更新时间: 2019/3/4 13:08
 * 描述: Activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;
    protected SystemBarTintManager mStatusBarManger;
    private Unbinder mBinder;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBinder = ButterKnife.bind(this);
        initStatusColor();
        initPermission();
        ActivityManger.get().addActivity(this);
        registerEventBus();
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

    protected void $startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 设置状态栏颜色
     */
    private void initStatusColor() {
        ScreenUtils.setStatusTextColor(getWindow().getDecorView(), true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarManger = new SystemBarTintManager(this);
            mStatusBarManger.setStatusBarTintEnabled(true);
            mStatusBarManger.setNavigationBarTintEnabled(true);
            mStatusBarManger.setStatusBarTintResource(R.color.colorWhite);
        }
    }

    public void initPermission() {
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
        super.onDestroy();
        mBinder.unbind();
        if (mPresenter != null) {
            mPresenter.deathPresenter();
        }
        ActivityManger.get().removeActivity(this.getClass());
        unRegisterEventBus();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestEventResult(BaseBean baseBean) {
        switch (baseBean.code) {
            case IApi.Code.MESSAGES_CODE_ERROR:
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.USER_NO_EXIST:
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.SERVICE_ERROR:
                Toasts.with().showToast(baseBean.message);
                break;
            case IApi.Code.USER_EXIST:
                final HintDialog loginDialog = new HintDialog.Builder(this)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_is_register)
                        .setSure(R.string.login_immediately)
                        .builder();
                loginDialog.show();
                loginDialog.setOnItemClickListener(view -> {
                    finish();
                    loginDialog.dismiss();
                });
            default:
                break;
        }
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



}

package com.ifeell.app.aboutball.my.activity;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.base.BaseWebActivity;
import com.ifeell.app.aboutball.router.ARouterConfig;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/12 18:54
 * 更新时间: 2019/4/12 18:54
 * 描述:分享好友Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SHARE_FRIEND)
public class ShareFriendActivity extends BaseWebActivity {
    private static final String PATH = IApiService.H5.DOWNLOAD_APK;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.wv_data)
    WebView mWebView;

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return mPbLoading;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_data;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        mWebView.loadUrl(PATH);
    }

    @Override
    protected TitleView getTitleView() {
        return mTitleView;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isLoadJs() {
        return true;
    }
}

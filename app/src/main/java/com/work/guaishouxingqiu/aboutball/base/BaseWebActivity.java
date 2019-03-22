package com.work.guaishouxingqiu.aboutball.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.WebHelp;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 16:31
 * 更新时间: 2019/3/21 16:31
 * 描述:WebView基类
 */
public abstract class BaseWebActivity<P extends BasePresenter> extends PermissionActivity<P> {
    private WebView mWebView;
    private ProgressBar mPbLoading;



    @Override
    protected void initView() {
        initWebView();
        mPbLoading = getProgressBar();
    }

    protected void initWebView() {
        mWebView = DataUtils.checkData(getWebView());

        WebHelp.initSetting(mWebView);
    }

    @Override
    protected void initEvent() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mPbLoading != null) {
                    mPbLoading.setProgress(newProgress);
                    if (newProgress == 100) {
                        mPbLoading.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 加载富文本
     *
     * @param content
     */
    protected void loadEditData(String content) {
        mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.pauseTimers();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mWebView.resumeTimers();
    }

    @Override
    protected void onDestroy() {
        ViewGroup viewGroup = (ViewGroup) mWebView.getParent();
        viewGroup.removeView(mWebView);
        mWebView.destroy();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract WebView getWebView();

    protected abstract ProgressBar getProgressBar();
}

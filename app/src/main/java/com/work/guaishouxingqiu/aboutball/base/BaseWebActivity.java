package com.work.guaishouxingqiu.aboutball.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.WebHelp;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 16:31
 * 更新时间: 2019/3/21 16:31
 * 描述:WebView基类
 */
public abstract class BaseWebActivity<P extends BasePresenter> extends PermissionActivity<P> {
    private WebView mWebView;
    private ProgressBar mPbLoading;
    private TitleView mTitleView;
    private boolean mLoadJs;

    public void setSetLoadJs(boolean loadJs) {
        this.mLoadJs = loadJs;
    }


    @Override
    protected void initView() {
        initWebView();
        mPbLoading = getProgressBar();
        mTitleView = getTitleView();
    }

    protected void initWebView() {
        mWebView = DataUtils.checkData(getWebView());
        WebHelp.initSetting(mWebView, mLoadJs);
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

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mTitleView != null) {
                    mTitleView.mTvCenter.setText(title);
                }
                LogUtils.w("onReceivedTitle--", title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }


    /**
     * 加载富文本
     *
     * @param content
     */
    protected void loadEditData(String content) {
        //   content = content.replace("<img", "<img style=max-width:100%;height:auto");
        //视频宽度自适应
        content = content.replace("<video", "<video style=max-width:100%;height:auto");
        mWebView.loadDataWithBaseURL(null, getNewData(content), "text/html", "utf-8", null);
    }


    private String getNewData(String data) {
        //图片高度自适应
        Document document = Jsoup.parse(data);
        Elements pElements = document.select("p:has(img)");
        for (Element pElement : pElements) {
            pElement.attr("style", "text-align:center");
            pElement.attr("max-width", String.valueOf(ScreenUtils.getScreenWidth(this) + "px"))
                    .attr("height", "auto");
        }
        Elements imgElements = document.select("img");
        for (Element imgElement : imgElements) {
            //重新设置宽高
            imgElement.attr("max-width", "100%")
                    .attr("height", "auto");
            imgElement.attr("style", "max-width:100%;height:auto");
        }
        return document.toString();
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

    protected TitleView getTitleView() {
        return mTitleView;
    }

}

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
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.WebHelp;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

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
        mWebView.setWebViewClient(new WebViewClient(){
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
        mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
      /*  Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            if (element.className() != null && element.className().length() > 0)
                element.attr("width", "100%").attr("height", "auto");
        }*/
      // mWebView.loadData(content,"text/html","utf-8");

      //  mWebView.loadUrl(content);
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

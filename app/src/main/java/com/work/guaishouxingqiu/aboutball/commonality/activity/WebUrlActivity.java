package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alivc.player.logreport.InitEvent;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.commonality.contract.WebUrlContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.WebUrlPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 11:14
 * 更新时间: 2019/9/19 11:14
 * 描述:包含Url传递的Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_WEB_URL)
public class WebUrlActivity extends BaseWebActivity<WebUrlPresenter> implements WebUrlContract.View {
    public static final int REQUEST_CODE = 2564;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.wv_data)
    WebView mWvData;

    @Override
    protected WebView getWebView() {
        return mWvData;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return mPbLoading;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_url;
    }


    @Override
    protected void initView() {
        super.initView();

    }


    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void initData() {
        mWvData.addJavascriptInterface(new AndroidToJs(), "open");
        loadAddTokenUrl(mWebUrl);

       /* mWvData.post(new Runnable() {
            @Override
            public void run() {
                mWvData.loadUrl("javascript:openScanCode()");
            }
        });*/
      /*  mWvData.evaluateJavascript("javascript:openScanCode()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                LogUtils.w("evaluateJavascript--",value);
            }
        });*/

    }

    @Override
    protected void initEvent() {
        super.initEvent();
     /*   mWvData.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                openScanCode();
                LogUtils.w("openScanCode---", "拦截到js方法了" + url + "--" + message + "--" + result.toString());
                result.cancel();
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });*/
      /*  mWvData.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //   mWvData.loadUrl("javascript:openScanCode()");
            }
        });*/
    }

    @Override
    protected boolean onJsAlertDialog() {
       // openScanCode();
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
       // super.onPageFinished(view, url);
        //mWvData.loadUrl("javascript:openScanCode()");
    }

    @Override
    protected TitleView getTitleView() {
        return mTitleView;
    }

    @Override
    protected WebUrlPresenter createPresenter() {
        return new WebUrlPresenter(this);
    }

    @Override
    protected boolean isLoadJs() {
        return true;
    }

    public  class AndroidToJs {
        @JavascriptInterface
        public void ScanCode() {
            openScanCode();
            LogUtils.w("AndroidToJs--","JS调用了Android的hello方法"+"--");
        }

    }

}

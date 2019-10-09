package com.ifeell.app.aboutball.commonality.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseWebActivity;
import com.ifeell.app.aboutball.commonality.contract.WebUrlContract;
import com.ifeell.app.aboutball.commonality.presenter.WebUrlPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;

import butterknife.BindView;

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
    private HintDialog mLocationDialog;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageFinished(WebView view, String url) {
        // super.onPageFinished(view, url);
        //   mWvData.loadUrl("javascript:openScanCode()");
    /*    mWvData.evaluateJavascript("javascript:openScanCode()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
            LogUtils.w("onReceiveValue--",value);

            }
        });*/
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

    @Override
    protected void resultScanCode(String scanCode) {
        String url = getTokenUrl(mWebUrl) + "&code=" + scanCode + "&longitude=" + getSPLongitude() + "&latitude=" + getSPLatitude();
        LogUtils.w("resultScanCode--", url);
        loadUrl(url);
    }

    public class AndroidToJs {


        @JavascriptInterface
        public void ScanCode(String msg) {
            requestLocation();

            LogUtils.w("AndroidToJs--", "JS调用了Android的hello方法" + "--");
        }

    }

    @Override
    public void locationResult(double longitude, double latitude, String city, boolean isOpenGps) {
        super.locationResult(longitude, latitude, city, isOpenGps);
        if (isOpenGps) {
            openScanCode();
        }

    }

    private void showNotSureLocationDialog() {
        if (mLocationDialog == null) {
            mLocationDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.unable_to_obtain_location_information)
                    .setSures(R.string.setting)
                    .setCancel(R.string.cancel)
                    .setShowSingButton(false)
                    .builder();
        }
        if (!mLocationDialog.isShowing() && !isFinishing()) {
            mLocationDialog.show();
        }
        mLocationDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mLocationDialog.dismiss();
                requestLocation();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mLocationDialog.dismiss();
                clickBackForResult();
            }
        });
    }
}

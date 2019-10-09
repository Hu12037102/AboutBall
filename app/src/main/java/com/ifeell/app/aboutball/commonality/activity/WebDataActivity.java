package com.ifeell.app.aboutball.commonality.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseWebActivity;
import com.ifeell.app.aboutball.commonality.contract.WebDataContract;
import com.ifeell.app.aboutball.commonality.presenter.WebDataPresenter;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.LogUtils;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 10:30
 * 更新时间: 2019/4/11 10:30
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_WEB_DATA)
public class WebDataActivity extends BaseWebActivity<WebDataPresenter> implements WebDataContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.wv_data)
    WebView mWebView;
    // private String mWebUrl;
    private String mActionId;

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
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            return;
        }
        // mWebUrl = bundle.getString(ARouterConfig.Key.URL);
        mActionId = bundle.getString(ARouterConfig.Key.ACTION_ID);
        if (UserManger.get().isLogin()) {
            String sb = IApiService.H5.PRIZE +
                    "?token=" +
                    UserManger.get().getToken() +
                    "&" +
                    mActionId;
            LogUtils.w("WebDataActivity--", sb);

            mWebView.loadUrl(sb);
            mWebView.addJavascriptInterface(new YunYou(), "YunYou");

        } else {
            mViewModel.showLoginDialog();
        }

    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return mPbLoading;
    }

    @Override
    protected WebDataPresenter createPresenter() {
        return new WebDataPresenter(this);
    }

    @Override
    protected TitleView getTitleView() {
        return mTitleView;
    }

    public static class YunYou {
        @JavascriptInterface
        public void comment(String commentid) {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS);
        }
    }

    @Override
    protected boolean isLoadJs() {
        return true;
    }
}

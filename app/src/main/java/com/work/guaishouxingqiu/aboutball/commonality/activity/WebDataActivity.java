package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.commonality.contract.WebDataContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.WebDataPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private static final String H5_HOST = "https://ifi.bmece.com/prizeAction";

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
            String sb = H5_HOST +
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

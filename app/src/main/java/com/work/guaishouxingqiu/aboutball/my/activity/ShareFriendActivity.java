package com.work.guaishouxingqiu.aboutball.my.activity;

import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/12 18:54
 * 更新时间: 2019/4/12 18:54
 * 描述:分享好友Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SHARE_FRIEND)
public class ShareFriendActivity extends BaseWebActivity {
    private static final String PATH = "https://ifi.bmece.com/download";
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

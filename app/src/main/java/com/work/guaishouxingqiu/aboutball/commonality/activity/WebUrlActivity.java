package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.commonality.contract.WebUrlContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.WebUrlPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

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

    @Override
    protected void initData() {
        loadAddTokenUrl(mWebUrl);
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
}

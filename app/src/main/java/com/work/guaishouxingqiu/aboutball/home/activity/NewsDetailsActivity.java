package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.weight.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 14:57
 * 更新时间: 2019/3/21 14:57
 * 描述:首页-资讯详情activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_NEW_DETAILS)
public class NewsDetailsActivity extends BaseWebActivity<NewDetailsPresenter> implements NewsDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.bw_web)
    BaseWebView mWebView;
    @BindView(R.id.rv_message_data)
    RecyclerView mRvMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_details;
    }

    @Override
    protected void initView() {
        long newsId = mIntent.getLongExtra(ARouterConfig.Key.NEW_DETAILS_ID, 0);
        mPresenter.loadNewsContent(newsId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected NewDetailsPresenter createPresenter() {
        return new NewDetailsPresenter(this);
    }


    @OnClick({R.id.iv_send_message, R.id.tv_input_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_send_message:
                break;
            case R.id.tv_input_message:
                break;
        }
    }

    @Override
    public void resultNewsContent(BaseDataBean<String> dataBean) {
        loadEditData(dataBean.content);
        mTitleView.mTvCenter.setText(dataBean.title);
    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }
}

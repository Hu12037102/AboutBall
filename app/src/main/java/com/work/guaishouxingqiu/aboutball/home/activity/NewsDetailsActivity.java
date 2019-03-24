package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsMessageAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseWebView;
import com.work.guaishouxingqiu.aboutball.weight.InputMessageDialog;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.rv_message_data)
    RecyclerView mRvMessage;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    private long mNewsId;
    private NewsMessageAdapter mAdapter;
    private List<ResultNewsMessageBean> mData;
    private InputMessageDialog mSendMessageDialog;
    private BaseWebView mWebView;
    private View mHeadView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_details;
    }

    @Override
    protected void initView() {
        initHeadView();
        super.initView();
        mNewsId = mIntent.getLongExtra(ARouterConfig.Key.NEW_DETAILS_ID, 0);
        mRvMessage.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_news_details_view, null);
        mWebView = mHeadView.findViewById(R.id.bw_web);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new NewsMessageAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvMessage.setAdapter(mAdapter);
        mPresenter.loadNewsContent(mNewsId);
        mPresenter.loadMessage(mNewsId);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlLayout.setOnLoadMoreListener(refreshLayout -> {

            mPresenter.isRefresh = false;
            mPresenter.loadMessage(mNewsId);
            refreshLayout.finishLoadMore();

        });

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
                if (mSendMessageDialog == null) {
                    mSendMessageDialog = new InputMessageDialog(this);
                    mSendMessageDialog.setOnInputMessageListener(text -> {
                        RequestSendMessageBean bean = new RequestSendMessageBean();
                        bean.newsId = mNewsId;
                        bean.commentContent = text;
                        mPresenter.sendNewsMessage(bean);
                    });
                }
                if (!mSendMessageDialog.isShowing()) {
                    mSendMessageDialog.show();
                }
                break;
        }
    }

    @Override
    public void resultNewsContent(BaseDataBean<String> dataBean) {
        loadEditData(dataBean.content);
        mTitleView.mTvCenter.setText(dataBean.title);
    }

    @Override
    public void resultMessageData(List<ResultNewsMessageBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mSrlLayout.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();
        mRvMessage.scrollToPosition(1);
    }

    @Override
    public void resultSendNewsMessage() {
        mPresenter.isRefresh = true;
        if (mSendMessageDialog != null) {
            mSendMessageDialog.clearEditData();
        }
        mPresenter.loadMessage(mNewsId);

    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return mPbLoading;
    }
}

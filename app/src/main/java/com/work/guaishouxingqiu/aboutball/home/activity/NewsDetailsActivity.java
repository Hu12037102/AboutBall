package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ShareWebBean;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsMessageAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
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
    @BindView(R.id.sv_video)
    SurfaceView mSvVideo;
    @BindView(R.id.iv_video_status)
    ImageView mIvVideoStatus;
    @BindView(R.id.skv_loading)
    SpinKitView mSkvLoading;
    @BindView(R.id.tv_play_time)
    TextView mTvPlayTime;
    @BindView(R.id.tv_length_time)
    TextView mTvLengthTime;
    @BindView(R.id.sb_seek)
    SeekBar mSbSeek;
    @BindView(R.id.pb_play_length)
    ProgressBar mPbPlayLength;
    @BindView(R.id.cl_schedule)
    ConstraintLayout mClSchedule;
    @BindView(R.id.cl_video)
    ConstraintLayout mClVideo;

    private long mNewsId;
    private NewsMessageAdapter mAdapter;
    private List<ResultNewsMessageBean> mData;
    private InputMessageDialog mSendMessageDialog;
    private BaseWebView mWebView;
    private View mHeadView;
    private TextView mTvTitle;
    private TextView mTvFrom;
    private BaseDataBean<String> mContentBean;


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
        mRvMessage.setVisibility(View.INVISIBLE);
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_news_details_view, (ViewGroup) getWindow().getDecorView().getRootView(), false);
        mWebView = mHeadView.findViewById(R.id.bw_web);
        mTvTitle = mHeadView.findViewById(R.id.tv_title);
        mTvFrom = mHeadView.findViewById(R.id.tv_from);
        ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mWebView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new NewsMessageAdapter(mData);
        mAdapter.setNotDataView(R.mipmap.icon_not_data_message);
        mAdapter.setNotDataContentRes(R.string.not_message);
        mAdapter.addHeadView(mHeadView);
        mRvMessage.setAdapter(mAdapter);
        mPresenter.loadNewsContent(mNewsId);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlLayout.setOnLoadMoreListener(refreshLayout -> {

            mPresenter.isRefresh = false;
            mPresenter.loadMessage(mNewsId);
            refreshLayout.finishLoadMore();

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                mRvMessage.setVisibility(View.VISIBLE);
                mPresenter.loadMessage(mNewsId);
            }
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
                ShareWebBean shareWebBean = new ShareWebBean();
                shareWebBean.webUrl = IApiService.H5.SHARE_NEWS_DETAILS + mNewsId;
                LogUtils.w("onViewClicked--", shareWebBean.webUrl + "\n" + mWebView.getTitle());
                shareWebBean.title = mContentBean.title;
                shareWebBean.description = "来源：" + mContentBean.source;
                showShareDialog(shareWebBean);
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
        mContentBean = dataBean;
        mTvTitle.setText(dataBean.title);
        mTvFrom.setText(UIUtils.getString(R.string.from_data, dataBean.source, dataBean.releaseTime));
        // mTitleView.mTvCenter.setText(dataBean.title);

    }

    @Override
    public void resultMessageData(List<ResultNewsMessageBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }

        mData.addAll(data);
        mSrlLayout.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();

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

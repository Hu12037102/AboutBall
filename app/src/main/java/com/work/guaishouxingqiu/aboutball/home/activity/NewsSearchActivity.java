package com.work.guaishouxingqiu.aboutball.home.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsSearchAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsSearchContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewsSearchPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 14:33
 * 更新时间: 2019/4/9 14:33
 * 描述:搜索页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_NEWS_SEARCH)
public class NewsSearchActivity extends BaseActivity<NewsSearchPresenter> implements NewsSearchContract.View {
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private String mSearchContent;
    private List<ResultNewsBean> mData;
    private NewsSearchAdapter mSearchAdapter;
    private View mInflateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mSearchAdapter = new NewsSearchAdapter(mData, mSearchContent);
        mSearchAdapter.setHasStableIds(true);
        mRvData.setAdapter(mSearchAdapter);

        // mInflateView = LayoutInflater.from(this).inflate(R.layout.item_not_more, mRvData, false);
    }

    @Override
    protected void initEvent() {
        mAcetContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mSearchContent = DataUtils.checkData(mAcetContent.getText()).toString().trim();
                if (DataUtils.isEmpty(mSearchContent)) {
                    Toasts.with().showToast(R.string.please_input_search_content);
                } else {
                    mPresenter.searchNews(mSearchContent);
                    return true;
                }

                //搜索逻辑
            }
            return false;
        });
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(refreshLayout, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(refreshLayout, true);
            }
        });
        mSearchAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(position).newsId);
            }
        });
    }

    private void loadData(RefreshLayout refreshLayout, boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        if (DataUtils.isEmpty(mSearchContent)) {
            Toasts.with().showToast(R.string.please_input_search_content);
        } else {
            mPresenter.searchNews(mSearchContent);
        }

    }

    @Override
    protected NewsSearchPresenter createPresenter() {
        return new NewsSearchPresenter(this);
    }


    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void resultSearchNewsData(List<ResultNewsBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mSrlRefresh.setEnableLoadMore(data.size() >= mPresenter.mPageSize);
     /*   if (mSearchAdapter.isHaveFootView) {
            mSearchAdapter.removeFootView();
        }
        if (data.size() < mPresenter.mPageSize) {
            mSearchAdapter.addFootView(UIUtils.loadNotMoreView(mRvData));
        }*/
        mSearchAdapter.notifyData(mSearchContent);
    }
}

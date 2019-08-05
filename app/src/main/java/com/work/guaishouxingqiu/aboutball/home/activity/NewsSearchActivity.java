package com.work.guaishouxingqiu.aboutball.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsSearchAdapter;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsSearchBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsSearchContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.NewsSearchPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    private List<ResultNewsBean> mHeadData;
    private NewsSearchAdapter mHeadAdapter;
    private View mInflateView;
    private View mHeadView;
    private LinearLayout mLlHeadNot;
    private RecyclerView mRvHeadData;
    private RecommendedAdapter mAdapter;
    private List<ResultNewsBean> mData;
    private TextView mTvHeadNotHint;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_news_search_view, mRvData,false);
        mLlHeadNot = mHeadView.findViewById(R.id.ll_head_not);
        mRvHeadData = mHeadView.findViewById(R.id.rv_head_data);
        mTvHeadNotHint = mHeadView.findViewById(R.id.tv_head_not_data_hint);
        mRvHeadData.setLayoutManager(new LinearLayoutManager(this));
        mHeadData = new ArrayList<>();
        mHeadAdapter = new NewsSearchAdapter(mHeadData, mSearchContent);
        mRvHeadData.setAdapter(mHeadAdapter);
    }

    @Override
    protected void initData() {
        initHeadView();
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mAdapter.setShowNotDataView(false);
        mAdapter.setHasStableIds(true);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);

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
                    mData.clear();
                    mHeadData.clear();
                    mHeadAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
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
        mHeadAdapter.setOnItemClickListener((view, position) -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                ARouterConfig.Key.NEW_DETAILS_ID, mHeadData.get(position).newsId));
    }

    private void notifyNotHeadHint() {
        String notContent = UIUtils.getString(R.string.the_content_has_not_been_found_yet, mSearchContent);
        int index = notContent.indexOf(mSearchContent);
        UIUtils.setText(mTvHeadNotHint, SpanUtils.getTextColor(R.color.color_2, index, index + mSearchContent.length(), notContent));
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
    public void resultSearchNewsData(ResultNewsSearchBean bean) {
        if (bean.newsForSearchList != null) {
            mData.clear();
            mLlHeadNot.setVisibility(View.GONE);
            mRvHeadData.setVisibility(View.VISIBLE);
            if (mPresenter.isRefresh && mHeadData.size() > 0) {
                mHeadData.clear();
            }
            if (bean.newsForSearchList.size() > 0) {
                mHeadData.addAll(bean.newsForSearchList);
            }
            mViewModel.setRefreshViewMoreStatus(mSrlRefresh, bean.newsForSearchList, mPresenter.mPageSize);
        } else if (bean.newsForRecommendList != null) {
            notifyNotHeadHint();
            mHeadData.clear();
            mLlHeadNot.setVisibility(View.VISIBLE);
            mRvHeadData.setVisibility(View.GONE);
            if (mData.size() > 0) {
                mData.clear();
            }
            if (bean.newsForRecommendList.size() > 0) {
                mData.addAll(bean.newsForRecommendList);
            }
        }
        mHeadAdapter.notifyData(mSearchContent);
        mAdapter.notifyDataSetChanged();

    }
}

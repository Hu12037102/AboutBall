package com.work.guaishouxingqiu.aboutball.community.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityReportAdapter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityReportContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityReportPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 11:42
 * 更新时间: 2019/6/17 11:42
 * 描述:动态举报Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_COMMUNITY_REPORT)
public class CommunityReportActivity extends BaseActivity<CommunityReportPresenter> implements CommunityReportContract.View {

    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    private CommunityReportAdapter mAdapter;
    String mReportCase;
    private List<String> mData;
    private long mTweetId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_report;
    }

    @Override
    public void initPermission() {
        mTweetId = mIntent.getLongExtra(ARouterConfig.Key.TWEET_ID, -1);
        if (mTweetId == -1) {
            UIUtils.showToast(R.string.no_dynamic_were_obtained);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = Arrays.asList(getResources().getStringArray(R.array.dynamic_report_array));
        mAdapter = new CommunityReportAdapter(mData);
        mRvData.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                mReportCase = mData.get(position);
            }
        });
    }

    @Override
    protected CommunityReportPresenter createPresenter() {
        return new CommunityReportPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (mReportCase == null) {
            UIUtils.showToast(R.string.please_selector_report_case);
        } else {
            mPresenter.reportCommunity(mTweetId, mReportCase);
        }
    }

    @Override
    public void reportCommunitySucceed() {
        finish();
    }
}

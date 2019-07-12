package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.PostEvaluationAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.PostEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.fragment.PostAllEvaluationFragment;
import com.work.guaishouxingqiu.aboutball.my.presenter.PostEvaluationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 11:45
 * 更新时间: 2019/5/27 11:45
 * 描述:发布评论Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_POST_EVALUATION)
public class PostEvaluationActivity extends BaseActivity<PostEvaluationPresenter> implements PostEvaluationContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    /*  @BindView(R.id.rv_data)
      RecyclerView mRvData;
      @BindView(R.id.srl_refresh)
      SmartRefreshLayout mSrlRefresh;*/
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.include_bottom)
    View mIncldeBottm;
    private PostEvaluationAdapter mAdapter;
    private List<ResultInputEvaluationBean> mData;
    private long mRefereeId;
    private long mTeamId;
    // private int mFlag;
    private long mAgreeId;
    private PostAllEvaluationFragment mMyTeamFragment;
    private PostAllEvaluationFragment[] mFragments;
    private PostAllEvaluationFragment mOpponentFragment;
    private PostAllEvaluationFragment mRefereeFragment;
    private long mGuestTeamId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_evaluation;
    }

    @Override
    public void initPermission() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        //  mFlag = bundle.getInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, -1);
        mRefereeId = bundle.getLong(ARouterConfig.Key.REFEREE_ID, -1);
        mGuestTeamId = bundle.getLong(ARouterConfig.Key.OPPONENT_ID, -1);
        mTeamId = bundle.getLong(ARouterConfig.Key.TEAM_ID, -1);
        mAgreeId = bundle.getLong(ARouterConfig.Key.AGREE_ID, -1);
       /* if (mFlag == -1) {
            UIUtils.showToast(R.string.not_find_evaluation);
            finish();
            return;
        }*/
        super.initPermission();
    }

    @Override
    protected void initView() {
        UIUtils.setText(mTvCommit, R.string.please_send_evaluation);
        initTabView();
        initPager();
        /*switch (mFlag) {
            case Contast.InputEvaluationType.REFEREE:
                mTvCommit.setVisibility(View.VISIBLE);
                mTvCommit.setText(R.string.please_post_evaluation);
                UIUtils.setText(mTitleView.mTvCenter, R.string.referee_evaluation);
                break;
            case Contast.InputEvaluationType.OPPONENT:
                mTvCommit.setVisibility(View.VISIBLE);
                mTvCommit.setText(R.string.please_post_evaluation);
                UIUtils.setText(mTitleView.mTvCenter, R.string.opponent_evaluation);
                break;
            case Contast.InputEvaluationType.TEAMMATE:
                mTvCommit.setVisibility(View.GONE);
                UIUtils.setText(mTitleView.mTvCenter, R.string.team_evaluation);
                break;
        }

        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mAdapter = new PostEvaluationAdapter(mData, mFlag);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();*/

    }

    private void initPager() {
        Bundle myTeamBundle = new Bundle();
        myTeamBundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.TEAMMATE);
        myTeamBundle.putLong(ARouterConfig.Key.ID, mTeamId);
        mMyTeamFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION, myTeamBundle);

        Bundle opponentBundle = new Bundle();
        opponentBundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.OPPONENT);
        opponentBundle.putLong(ARouterConfig.Key.ID, mGuestTeamId);
        mOpponentFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION, opponentBundle);

        Bundle refereeBundle = new Bundle();
        refereeBundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.REFEREE);
        refereeBundle.putLong(ARouterConfig.Key.ID, mRefereeId);
        mRefereeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION, refereeBundle);
        mFragments = new PostAllEvaluationFragment[]{mMyTeamFragment, mOpponentFragment, mRefereeFragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments[i];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        };
        mBvpContent.setOffscreenPageLimit(mFragments.length);
        mBvpContent.setAdapter(pagerAdapter);

    }

    private void initTabView() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.evaluate_tab_array));
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45);
        }
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabContent.getTabAt(i)).select();

                if (mFragments[mBvpContent.getCurrentItem()].equals(mMyTeamFragment)) {
                    mIncldeBottm.setVisibility(View.GONE);
                } else {
                    mIncldeBottm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTabContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvpContent.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       /* mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh();
            switch (mFlag) {
                case Contast.InputEvaluationType.REFEREE:
                    mPresenter.loadRefereeEvaluation(mRefereeId);
                    break;
                case Contast.InputEvaluationType.OPPONENT:
                case Contast.InputEvaluationType.TEAMMATE:
                    mPresenter.loadTeamEvaluation(mTeamId);
                    break;
            }
        });*/
    }

    @Override
    protected PostEvaluationPresenter createPresenter() {
        return new PostEvaluationPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (mBvpContent.getChildCount() > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong(ARouterConfig.Key.AGREE_ID, mAgreeId);

            if (mFragments[mBvpContent.getCurrentItem()].equals(mMyTeamFragment)) {
                bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.TEAMMATE);
                bundle.putLong(ARouterConfig.Key.TEAM_ID, mTeamId);
            } else if (mFragments[mBvpContent.getCurrentItem()].equals(mOpponentFragment)) {
                bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.OPPONENT);
                bundle.putLong(ARouterConfig.Key.TEAM_ID, mGuestTeamId);
            } else if (mFragments[mBvpContent.getCurrentItem()].equals(mRefereeFragment)) {
                bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.REFEREE);
                bundle.putLong(ARouterConfig.Key.REFEREE_ID, mRefereeId);
            }
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_INPUT_EVALUATION, this, bundle);
        }
        /*Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, mFlag);
        bundle.putLong(ARouterConfig.Key.AGREE_ID, mAgreeId);
        if (mFlag == Contast.InputEvaluationType.REFEREE) {
            bundle.putLong(ARouterConfig.Key.REFEREE_ID, mRefereeId);
        } else if (mFlag == Contast.InputEvaluationType.OPPONENT) {
            bundle.putLong(ARouterConfig.Key.TEAM_ID, mTeamId);
        }
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_INPUT_EVALUATION, this, bundle);*/
    }

    @Override
    public void resultEvaluation(List<ResultInputEvaluationBean> data) {
       /* if (mData.size() > 0) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == RESULT_OK) {
            if (mBvpContent.getChildCount() > 0) {
                PostAllEvaluationFragment fragment = mFragments[mBvpContent.getCurrentItem()];
                fragment.refresh();
            }
        }
    }
}

package com.work.guaishouxingqiu.aboutball.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.RefereeDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.fragment.MyRefereeRecordFragment;
import com.work.guaishouxingqiu.aboutball.my.fragment.PostEvaluationFragment;
import com.work.guaishouxingqiu.aboutball.my.presenter.RefereeDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.RefereeListAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/28 10:12
 * 更新时间: 2019/5/28 10:12
 * 描述:裁判详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_REFEREE_DETAILS)
public class RefereeDetailsActivity extends BaseActivity<RefereeDetailsPresenter> implements RefereeDetailsContract.View {

    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.riv_head)
    RoundedImageView mRivHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_referee_gradle)
    TextView mTvRefereeGradle;
    @BindView(R.id.tv_record)
    TextView mTvRecord;
    @BindView(R.id.tv_evaluate)
    TextView mTvEvaluate;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private ResultRefereeBean mRefereeParcelable;
    private int mChooseCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_referee_details;
    }


    @Override
    public void initPermission() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            finish();
            UIUtils.showToast(R.string.not_find_this_referee);
            return;

        }
        mRefereeParcelable = bundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        mChooseCount = bundle.getInt(ARouterConfig.Key.COUNT, 0);
        if (mRefereeParcelable == null) {
            UIUtils.showToast(R.string.not_find_this_referee);
            finish();
            return;
        }
        super.initPermission();
    }

    private void notifyCommitStatus() {
        if (mRefereeParcelable.isInvite || mChooseCount >= RefereeListAdapter.MAX_INVITE_COUNT) {
            mTvCommit.setEnabled(false);
            mTvCommit.setBackgroundResource(R.drawable.shape_default_button);
        } else {
            mTvCommit.setEnabled(true);
            mTvCommit.setBackgroundResource(R.drawable.shape_click_button);
        }
    }

    @Override
    protected void initView() {
        mTvCommit.setText(R.string.invite_a_referee);
        notifyCommitStatus();
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.referee_details_tab_array));
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45);
        }

        MyRefereeRecordFragment refereeRecordFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_REFEREE_RECORD, ARouterConfig.Key.REFEREE_ID, mRefereeParcelable.refereeId);
       /* Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.REFEREE_ID, mRefereeParcelable.refereeId);
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.REFEREE);
        PostEvaluationFragment evaluateFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_EVALUATION, bundle);*/
        PostEvaluationFragment evaluateFragment  = mViewModel.getRefereeEvaluationFragment(mRefereeParcelable.refereeId,Contast.InputEvaluationType.REFEREE);
        Fragment[] fragments = {refereeRecordFragment, evaluateFragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        mBvpContent.setAdapter(pagerAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.loadRefereeDetails(mRefereeParcelable.refereeId);
    }

    @Override
    protected void initEvent() {
        mTabContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvpContent.setCurrentItem(DataUtils.checkData(tab).getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabContent.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected RefereeDetailsPresenter createPresenter() {
        return new RefereeDetailsPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        mRefereeParcelable.isInvite = !mRefereeParcelable.isInvite;
        Intent intent = new Intent();
        intent.putExtra(ARouterConfig.Key.PARCELABLE, mRefereeParcelable);
        setResult(Activity.RESULT_OK, intent);
        notifyCommitStatus();
        UIUtils.showToast(R.string.invite_succeed);
        finish();
    }

    @Override
    public void resultRefereeDetails(ResultRefereeDetailsBean bean) {
        GlideManger.get().loadBannerImage(this, bean.photo, mRivHead);
        UIUtils.setText(mTvName, bean.name);
        UIUtils.setText(mTvRefereeGradle, UIUtils.getString(R.string.referee_class_s, bean.level));
        String recordHost = UIUtils.getString(R.string.count_s, String.valueOf(bean.matchCount));
        String recordContent = recordHost + "\n" + UIUtils.getString(R.string.law_enforcement_record);
        UIUtils.setText(mTvRecord, SpanUtils.getTextColor(R.color.color_4, 0, recordHost.length(), SpanUtils.getTextSize(14, 0, recordHost.length(), recordContent)));

        String evaluateHost = UIUtils.getString(R.string.strip_s, String.valueOf(bean.commentCount));
        String evaluateContent = evaluateHost + "\n" + UIUtils.getString(R.string.law_enforcement_evaluate);
        UIUtils.setText(mTvEvaluate, SpanUtils.getTextColor(R.color.color_4, 0, evaluateHost.length(), SpanUtils.getTextSize(14, 0, evaluateHost.length(), evaluateContent)));

    }
}

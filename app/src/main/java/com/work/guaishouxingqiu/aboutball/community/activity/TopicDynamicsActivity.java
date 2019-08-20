package com.work.guaishouxingqiu.aboutball.community.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;
import com.work.guaishouxingqiu.aboutball.community.fragment.TopicDynamicsFragment;
import com.work.guaishouxingqiu.aboutball.community.presenter.TopicDynamicsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.SellingPointsEvent;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/20 13:47
 * 更新时间: 2019/6/20 13:47
 * 描述:话题动态Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TOPIC_DYNAMICS)
public class TopicDynamicsActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_banner)
    ImageView mIvBanner;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private ResultRecommendHotBean mIntentBean;
    private static final int REQUEST_CODE_PUBLISH_DYNAMIC = 155;
    private Fragment[] mFragments;
    private TopicDynamicsFragment mDynamicsFragment;
    private TopicDynamicsFragment mNewFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_dynamics;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_topic);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initSellingPoint() {
        DataUtils.addSellingPoint(TopicDynamicsActivity.this, SellingPointsEvent.Key.A0407);
    }

    @Override
    protected void initView() {
        GlideManger.get().loadBannerImage(this, mIntentBean.imageUrl, mIvBanner);
        UIUtils.setText(mTvContent, mIntentBean.description);
        UIUtils.setText(mTitleView.mTvCenter, mIntentBean.topicTitle);
        LogUtils.w("initView--topic", mIntentBean.description);
        initTab();
        initPager();

    }


    private void initTab() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.topic_dynamics_tab_array));
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45);
        }
    }

    private void initPager() {
        mDynamicsFragment = mViewModel.getTopicFragment(Contast.Topic.RECOMMENDED, mIntentBean.topicId);
        mNewFragment = mViewModel.getTopicFragment(Contast.Topic.NEW, mIntentBean.topicId);
        mFragments = new Fragment[]{mDynamicsFragment, mNewFragment};
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
        mBvpContent.setAdapter(pagerAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
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
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                mViewModel.clickBackForResult();
            }
        });
        mNewFragment.setOnUpdateDataChangListener(new TopicDynamicsFragment.OnUpdateDataChangListener() {
            @Override
            public void onDataChang(ResultCommunityDataBean bean) {
                mDynamicsFragment.autoRefresh(bean);
            }
        });
        mDynamicsFragment.setOnUpdateDataChangListener(new TopicDynamicsFragment.OnUpdateDataChangListener() {
            @Override
            public void onDataChang(ResultCommunityDataBean bean) {
                mNewFragment.autoRefresh(bean);
            }
        });
    }

    @Override
    protected TopicDynamicsPresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.iv_add_topic, R.id.iv_banner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_topic:
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_DYNAMIC_EDIT, this, TopicDynamicsActivity.REQUEST_CODE_PUBLISH_DYNAMIC);
                break;
            case R.id.iv_banner:
                mViewModel.startActivityToPreview(0, DataUtils.getOnePreviewData(mIntentBean.imageUrl));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TopicDynamicsActivity.REQUEST_CODE_PUBLISH_DYNAMIC:
                    TopicDynamicsFragment fragment = (TopicDynamicsFragment) mFragments[mBvpContent.getCurrentItem()];
                    fragment.refreshData();
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        mViewModel.clickBackForResult();
    }
}

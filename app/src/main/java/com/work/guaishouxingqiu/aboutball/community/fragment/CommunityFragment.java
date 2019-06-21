package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:39
 * 更新时间: 2019/3/6 15:39
 * 描述: 社区Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY)
public class CommunityFragment extends DelayedFragment<CommunityPresenter> implements CommunityContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private CommunityAttentionFragment mAttentionFragment;
    private CommunityRecommendFragment mRecommendFragment;
    private CommunityNewFragment mNewFragment;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmenyt_community;
    }

    @Override
    protected void initDelayedView() {
        initTabView();
        initPagerView();
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {
        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                mTabTitle.getTabAt(i).select();
             /*   switch (i) {
                    case 0:
                        mAttentionFragment.autoRefresh();
                        break;
                    case 1:
                        mRecommendFragment.autoRefresh();
                        break;
                    case 2:
                        mNewFragment.autoRefresh();
                        break;
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mAttentionFragment.setOnUpdateCommunity(new CommunityAttentionFragment.OnUpdateCommunity() {
            @Override
            public void updateRecommended(ResultCommunityDataBean bean) {
                mRecommendFragment.autoRefresh(bean);
            }

            @Override
            public void updateNew(ResultCommunityDataBean bean) {
                mNewFragment.autoRefresh(bean);
            }


        });
        mRecommendFragment.setOnUpdateCommunity(new CommunityRecommendFragment.OnUpdateCommunity() {
            @Override
            public void updateAttention(ResultCommunityDataBean bean) {
                mAttentionFragment.autoRefresh(bean);
            }

            @Override
            public void updateNew(ResultCommunityDataBean bean) {
                mNewFragment.autoRefresh(bean);
            }

        });
        mNewFragment.setOnUpdateCommunity(new CommunityNewFragment.OnUpdateCommunity() {
            @Override
            public void updateAttention(ResultCommunityDataBean bean) {
                mAttentionFragment.autoRefresh(bean);
            }

            @Override
            public void updateRecommend(ResultCommunityDataBean bean) {
                mRecommendFragment.autoRefresh(bean);
            }


        });
    }

    @Override
    protected void initView() {
        /*initTabView();
        initPagerView();*/
    }

    private void initTabView() {
        String[] tabArray = getResources().getStringArray(R.array.community_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            UIUtils.setBaseCustomTabLayout(mTabTitle, tabArray[i], i == 0, 45, 18);
            // mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]), i == 0);
        }
    }

    private void initPagerView() {
        mAttentionFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_ATTENTION);
        mRecommendFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_RECOMMEND);
        mNewFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_NEWS);
        Fragment[] fragments = {mAttentionFragment, mRecommendFragment, mNewFragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        mBvpContent.setOffscreenPageLimit(fragments.length);
        mBvpContent.setAdapter(pagerAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
       /* mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                mTabTitle.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });*/
    }

    @Override
    protected CommunityPresenter createPresenter() {
        return new CommunityPresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }
}

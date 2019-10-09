package com.ifeell.app.aboutball.community.fragment;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.contract.CommunityContract;
import com.ifeell.app.aboutball.community.presenter.CommunityPresenter;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

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
    private Fragment[] mFragments;

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
                switch (tab.getPosition()) {
                    case 0:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0401);
                        break;
                    case 1:
                        break;
                    case 2:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0402);
                        break;
                    default:
                        break;
                }
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
                if ((mFragments[i].equals(mAttentionFragment) && !UserManger.get().isLogin())) {
                    mAttentionFragment.autoRefresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mAttentionFragment.setOnUpdateCommunity(new CommunityAttentionFragment.OnUpdateCommunity() {
            @Override
            public void updateRecommended(ResultCommunityDataBean bean) {
                mRecommendFragment.notifyData(bean);
            }

            @Override
            public void updateNew(ResultCommunityDataBean bean) {
                mNewFragment.notifyData(bean);
            }


        });
        mRecommendFragment.setOnUpdateCommunity(new CommunityRecommendFragment.OnUpdateCommunity() {
            @Override
            public void updateAttention(ResultCommunityDataBean bean) {
                mAttentionFragment.notifyData(bean);
            }

            @Override
            public void updateNew(ResultCommunityDataBean bean) {
                mNewFragment.notifyData(bean);
            }

        });
        mNewFragment.setOnUpdateCommunity(new CommunityNewFragment.OnUpdateCommunity() {
            @Override
            public void updateAttention(ResultCommunityDataBean bean) {
                mAttentionFragment.notifyData(bean);
            }

            @Override
            public void updateRecommend(ResultCommunityDataBean bean) {
                mRecommendFragment.notifyData(bean);
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
            UIUtils.setBaseCustomTabLayout(mTabTitle, tabArray[i], i == 1, 45, 18);
            // mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]), i == 0);
        }
    }

    private void initPagerView() {
        mAttentionFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_ATTENTION);
        mRecommendFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_RECOMMEND);
        mNewFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY_NEWS);
        mFragments = new Fragment[]{mAttentionFragment, mNewFragment ,mRecommendFragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
        mBvpContent.setCurrentItem(1, true);
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

package com.ifeell.app.aboutball.game.fragment;


import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.game.contract.GameContract;
import com.ifeell.app.aboutball.game.presenter.GamePresenter;
import com.ifeell.app.aboutball.home.fragment.HomeFragment;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:06
 * 更新时间: 2019/3/6 13:06
 * 描述: 比赛Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME)
public class GameFragment extends DelayedFragment<GamePresenter> implements GameContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initDelayedView() {
        registerEventBus();
    }

    @Override
    protected void initDelayedData() {
        initTabData();
        initPagerData();
    }

    @Override
    protected void initDelayedEvent() {
        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvpContent.setCurrentItem(DataUtils.checkData(tab).getPosition(), true);
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
                DataUtils.checkData(mTabTitle.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterEventBus();
    }


    private void initTabData() {
        String[] tabArray = getResources().getStringArray(R.array.game_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            /*mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]));
            if (i == 0) {
                DataUtils.checkData(mTabTitle.getTabAt(i)).select();
            }*/
            UIUtils.setBaseCustomTabLayout(mTabTitle, tabArray[i], i == 0, 45, 18);
        }
    }

    private void initPagerData() {
        GameOfficialFragment mOfficialFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_OFFICIAL);
        GameFolkFragment mFolkFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_FOLK);
     //   GameTeachFragment mTeachFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_TEACH);
        Fragment[] fragments = new Fragment[]{mOfficialFragment, mFolkFragment};
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
        mBvpContent.setAdapter(pagerAdapter);
    }


    @Override
    protected GamePresenter createPresenter() {
        return new GamePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }

    @Subscribe
    public void selectorGamePager(HomeFragment.Message message) {
        if (mBvpContent == null) {
            return;
        }
        mBvpContent.setCurrentItem(message.mChildTabIndex);
    }
}

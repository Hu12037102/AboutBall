package com.work.guaishouxingqiu.aboutball.game.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.contract.GameContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GamePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:06
 * 更新时间: 2019/3/6 13:06
 * 描述: 比赛Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME)
public class GameFragment extends BaseFragment<GamePresenter> implements GameContract.View {
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
    protected void initView() {

    }

    @Override
    protected void initData() {
        initTabData();
        initPagerData();


    }

    private void initTabData() {
        String[] tabArray = getResources().getStringArray(R.array.game_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]));
            if (i == 0) {
                DataUtils.checkData(mTabTitle.getTabAt(i)).select();
            }
        }
    }

    private void initPagerData() {
        GameOfficialFragment mOfficialFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_OFFICIAL);
        GameFolkFragment mFolkFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_FOLK);
        GameTeachFragment mTeachFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_TEACH);
        Fragment[] fragments = new Fragment[]{mOfficialFragment, mFolkFragment, mTeachFragment};
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
    protected void initEvent() {
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
    protected GamePresenter createPresenter() {
        return new GamePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }
}

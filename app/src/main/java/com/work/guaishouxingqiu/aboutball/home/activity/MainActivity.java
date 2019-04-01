package com.work.guaishouxingqiu.aboutball.home.activity;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.fragment.CommunityFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameFragment;
import com.work.guaishouxingqiu.aboutball.home.adapter.MainTabAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;
import com.work.guaishouxingqiu.aboutball.home.contract.MainContract;
import com.work.guaishouxingqiu.aboutball.home.fragment.HomeFragment;
import com.work.guaishouxingqiu.aboutball.home.presenter.MainPresenter;
import com.work.guaishouxingqiu.aboutball.my.fragment.MyFragment;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.venue.fragment.VenueFragment;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 主页面
 */
public class MainActivity extends PermissionActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.rv_main_tab)
    RecyclerView mRvMainTab;
    @BindView(R.id.fl_main_data)
    FrameLayout mFlMainData;
    private MainTabAdapter mTabAdapter;
    private HomeFragment mHomeFragment;
    private FragmentManager mManger;
    private GameFragment mGameFragment;
    private VenueFragment mVenueFragment;
    private CommunityFragment mCommunityFragment;
    private MyFragment mMyFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRvMainTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        initTime();
        mPresenter.loadMainTab();
        initFragment();

    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
       // calendar.set(Calendar.YEAR,2008);
        calendar.set(2008,2,0);
        Date date = new Date();


        LogUtils.w("initView--",calendar.get(Calendar.DAY_OF_MONTH)+"--");
    }

    @Override
    public void initPermission() {
        requestPermission(new OnPermissionsResult() {
                              @Override
                              public void onAllow(List<String> allowPermissions) {
                                  MainActivity.super.initPermission();
                              }

                              @Override
                              public void onNoAllow(List<String> noAllowPermissions) {
                                  Toasts.with().showToast(R.string.must_permission);
                                  initPermission();
                              }

                              @Override
                              public void onForbid(List<String> noForbidPermissions) {
                                  showForbidPermissionDialog();
                              }
                          }, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION);

    }

    private void initFragment() {

        mManger = getSupportFragmentManager();
        if (mManger != null) {
            FragmentTransaction transaction = mManger.beginTransaction();
            mHomeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_HOME);
            mGameFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME);
            mVenueFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_VENUE);
            mCommunityFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY);
            mMyFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY);
               /* mGameFragment = GameFragment.newInstance();
                mVenueFragment = VenueFragment.newInstance();
                mCommunityFragment = CommunityFragment.newInstance();
                mMyFragment = MyFragment.newInstance();*/
            transaction.add(R.id.fl_main_data, mHomeFragment);
            transaction.add(R.id.fl_main_data, mGameFragment);
            transaction.hide(mGameFragment);
            transaction.add(R.id.fl_main_data, mVenueFragment);
            transaction.hide(mVenueFragment);
            transaction.add(R.id.fl_main_data, mCommunityFragment);
            transaction.hide(mCommunityFragment);
            transaction.add(R.id.fl_main_data, mMyFragment);
            transaction.hide(mMyFragment);
            transaction.show(mHomeFragment);
            transaction.commitNow();
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void loadMainTabResult(@NonNull List<MainTabBean> data) {
        if (data.size() > 0) {
            if (mTabAdapter == null) {
                mTabAdapter = new MainTabAdapter(data);
                mRvMainTab.setAdapter(mTabAdapter);
                mTabAdapter.setOnCheckTabListener((view, position) -> {
                    FragmentTransaction transaction = mManger.beginTransaction();
                    switch (position) {
                        //首页
                        case 0:
                            transaction.show(mHomeFragment);
                            transaction.hide(mGameFragment);
                            transaction.hide(mVenueFragment);
                            transaction.hide(mCommunityFragment);
                            transaction.hide(mMyFragment);
                            break;
                        //比赛
                        case 1:
                            transaction.hide(mHomeFragment);
                            transaction.show(mGameFragment);
                            transaction.hide(mVenueFragment);
                            transaction.hide(mCommunityFragment);
                            transaction.hide(mMyFragment);
                            break;
                        case 2:
                            transaction.hide(mHomeFragment);
                            transaction.hide(mGameFragment);
                            transaction.show(mVenueFragment);
                            transaction.hide(mCommunityFragment);
                            transaction.hide(mMyFragment);
                            break;

                        case 3:
                            transaction.hide(mHomeFragment);
                            transaction.hide(mGameFragment);
                            transaction.hide(mVenueFragment);
                            transaction.show(mCommunityFragment);
                            transaction.hide(mMyFragment);
                            break;
                        case 4:
                            transaction.hide(mHomeFragment);
                            transaction.hide(mGameFragment);
                            transaction.hide(mVenueFragment);
                            transaction.hide(mCommunityFragment);
                            transaction.show(mMyFragment);
                            break;
                        default:
                            break;
                    }
                    transaction.commitNow();
                });
            } else {
                mTabAdapter.notifyDataSetChanged();
            }
        }
    }


}

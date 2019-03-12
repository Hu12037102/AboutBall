package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.contract.HomeContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.HomePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:04
 * 更新时间: 2019/3/6 13:04
 * 描述: 首页Fragment
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String[] homeTabArray = getResources().getStringArray(R.array.home_tab_array);
        for (int i = 0; i < homeTabArray.length; i++) {
            mTabTitle.addTab( mTabTitle.newTab().setText(homeTabArray[i]));
            if (i == 0) {
                DataUtils.checkData(mTabTitle.getTabAt(0)).select();
            }
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }
}

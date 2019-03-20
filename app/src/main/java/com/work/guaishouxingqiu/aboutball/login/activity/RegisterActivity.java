package com.work.guaishouxingqiu.aboutball.login.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterCodeContract;
import com.work.guaishouxingqiu.aboutball.login.contract.RegisterContract;
import com.work.guaishouxingqiu.aboutball.login.fragment.RegisterCodeFragment;
import com.work.guaishouxingqiu.aboutball.login.fragment.RegisterPasswordFragment;
import com.work.guaishouxingqiu.aboutball.login.fragment.RegisterPhoneFragment;
import com.work.guaishouxingqiu.aboutball.login.presenter.RegisterPresenter;
import com.work.guaishouxingqiu.aboutball.other.ActivityManger;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:50
 * 更新时间: 2019/3/11 11:50
 * 描述: 注册Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    private RegisterPhoneFragment mPhoneFragment;
    private List<Fragment> mFragmentData;
    private RegisterCodeFragment mCodeFragment;
    private RequestRegisterBean mRequestBean;
    private RegisterPasswordFragment mPasswordFragment;
    private int mCurrentPager = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mPhoneFragment = RegisterPhoneFragment.newInstance();
        mCodeFragment = RegisterCodeFragment.newInstance();
        mPasswordFragment = RegisterPasswordFragment.newInstance();
        mFragmentData = new ArrayList<>();
        mFragmentData.add(mPhoneFragment);
        mFragmentData.add(mCodeFragment);
        mFragmentData.add(mPasswordFragment);
    }

    @Override
    protected void initData() {
        mRequestBean = new RequestRegisterBean();
        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private int mChildCount = 0;

            @Override
            public Fragment getItem(int i) {
                return mFragmentData.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentData == null ? 0 : mFragmentData.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                mChildCount = getCount();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                if (mChildCount > 0) {
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }
        };
        mBvpContent.setAdapter(mPagerAdapter);
    }

    @Override
    protected void initEvent() {
        mPhoneFragment.setOnNextClickListener(phoneNumber -> {
                    mBvpContent.setCurrentItem(mFragmentData.indexOf(mCodeFragment), true);
                    mRequestBean.phone = phoneNumber;
                }
        );
        mCodeFragment.setOnMessageCodeInputResult(messageCode -> {
            mRequestBean.verificationCode = messageCode;
            mBvpContent.setCurrentItem(mFragmentData.indexOf(mPasswordFragment), true);
        });
        mPasswordFragment.setOnInputPasswordResult(password -> {
            mRequestBean.password = password;
            mPresenter.register(mRequestBean);
        });
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPager = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTitleView.setOnBackViewClickListener(view -> {
            if (mCurrentPager == 0) {
                finish();
            } else {
                if (mBvpContent.getCurrentItem() == mFragmentData.indexOf(mCodeFragment)) {
                    mCodeFragment.clearData();
                } else if (mBvpContent.getCurrentItem() == mFragmentData.indexOf(mPasswordFragment)) {
                    mPasswordFragment.clearData();
                }
                mBvpContent.setCurrentItem(mCurrentPager - 1);

            }
        });
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void onBackPressed() {
        if (mCurrentPager == 0) {
            super.onBackPressed();
        } else {
            if (mBvpContent.getCurrentItem() == mFragmentData.indexOf(mCodeFragment)) {
                mCodeFragment.clearData();
            } else if (mBvpContent.getCurrentItem() == mFragmentData.indexOf(mPasswordFragment)) {
                mPasswordFragment.clearData();
            }

            mBvpContent.setCurrentItem(mCurrentPager - 1);

        }
    }

    @Override
    public void registerResult(@NonNull BaseBean<RegisterResultBean> bean) {
        switch (bean.code) {
            case IApi.Code.SUCCEED:
                finish();
                break;
            default:
                break;
        }
    }
}

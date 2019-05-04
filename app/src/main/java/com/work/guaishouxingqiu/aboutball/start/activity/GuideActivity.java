package com.work.guaishouxingqiu.aboutball.start.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.start.adapter.GuidePagerAdapter;
import com.work.guaishouxingqiu.aboutball.start.contract.GuideContract;
import com.work.guaishouxingqiu.aboutball.start.presenter.GuidePresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.start.activity
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}引导页（第一次安装的时候）
 *
 * @author ：
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GUIDE)
public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View {
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.tv_experience)
    TextView mTvExperience;
    private List<Integer> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mData.add(R.mipmap.icon_guide_1);
        mData.add(R.mipmap.icon_guide_2);
        mData.add(R.mipmap.icon_guide_3);
        mData.add(R.mipmap.icon_guide_4);
        GuidePagerAdapter pagerAdapter = new GuidePagerAdapter(mData);
        mBvpContent.setOffscreenPageLimit(mData.size());
        mBvpContent.setAdapter(pagerAdapter);
    }

    @Override
    protected void initEvent() {
        mTvExperience.setOnClickListener(view -> {
            SharedPreferencesHelp sp = new SharedPreferencesHelp();
            sp.putObject(SharedPreferencesHelp.KEY_GUIDE_OPEN, true);
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MAIN);
            finish();
        });
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == mData.size() - 1) {
                    mTvExperience.setVisibility(View.VISIBLE);
                } else {
                    mTvExperience.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected GuidePresenter createPresenter() {
        return new GuidePresenter(this);
    }


}

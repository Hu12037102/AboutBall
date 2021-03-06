package com.ifeell.app.aboutball.splash.activity;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.other.SharedPreferencesHelp;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.splash.adapter.GuidePagerAdapter;
import com.ifeell.app.aboutball.splash.contract.GuideContract;
import com.ifeell.app.aboutball.splash.presenter.GuidePresenter;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MAIN);
            SharedPreferencesHelp sp = new SharedPreferencesHelp();
            sp.putObject(SharedPreferencesHelp.KEY_GUIDE_OPEN, true);
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

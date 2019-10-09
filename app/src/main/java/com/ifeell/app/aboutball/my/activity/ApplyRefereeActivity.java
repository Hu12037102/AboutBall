package com.ifeell.app.aboutball.my.activity;

import androidx.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.contract.ApplyRefereeContract;
import com.ifeell.app.aboutball.my.presenter.ApplyRefereePresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 10:57
 * 更新时间: 2019/5/6 10:57
 * 描述:成为裁判activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_APPLY_REFEREE)
public class ApplyRefereeActivity extends BaseActivity<ApplyRefereePresenter> implements ApplyRefereeContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_referee;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                mViewModel.clickBackForResult();
            }
        });
    }

    @Override
    protected ApplyRefereePresenter createPresenter() {
        return new ApplyRefereePresenter(this);
    }



    @OnClick(R.id.tv_apply)
    public void onViewClicked() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_APPLY_BECOME_REFEREE);
        mViewModel.clickBackForResult();
    }

    @Override
    public void onBackPressed() {
       mViewModel.clickBackForResult();
    }

}

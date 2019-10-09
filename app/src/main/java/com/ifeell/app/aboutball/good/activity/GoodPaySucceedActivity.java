package com.ifeell.app.aboutball.good.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.activity.TicketMallActivity;
import com.ifeell.app.aboutball.home.activity.TicketMallDetailsActivity;
import com.ifeell.app.aboutball.other.ActivityManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/23 13:42
 * 更新时间: 2019/9/23 13:42
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GOOD_PAY_SUCCEED)
public class GoodPaySucceedActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_content)
    ImageView mIvContent;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_click)
    TextView mTvClick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_succeed;
    }

    @Override
    protected void initView() {
        mTitleView.mTvSure.setVisibility(View.GONE);
        UIUtils.setText(mTvTitle, R.string.you_pay_succeed_please_my_tickets_read);
        mTvClick.setVisibility(View.VISIBLE);
        UIUtils.setText(mTvClick, R.string.back_my_ticket);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(view -> clickBack());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick(R.id.tv_click)
    public void onViewClicked() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_TICKETS);
        clickBack();
        ActivityManger.get().removeActivity(TicketMallDetailsActivity.class);
        ActivityManger.get().removeActivity(TicketMallActivity.class);

    }

    private void clickBack() {
        mViewModel.clickBackForResult();
       // ActivityManger.get().removeActivity(TicketMallActivity.class);
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }
}

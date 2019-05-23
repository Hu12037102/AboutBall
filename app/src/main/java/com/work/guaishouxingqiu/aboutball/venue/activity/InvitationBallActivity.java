package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueOrderBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.InvitationBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.InvitationBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 13:27
 * 更新时间: 2019/5/21 13:27
 * 描述:应邀约球Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_INVITATION_BALL)
public class InvitationBallActivity extends BaseActivity<InvitationBallPresenter> implements InvitationBallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    private ResultMyBallTeamBean mMyBallTeam;
    private ResultAboutBallDetailsBean mDetailsBean;
    private RequestVenueOrderBean mRequestOrderBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invitaion_ball;
    }

    @Override
    protected void initView() {
        mDetailsBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mDetailsBean == null) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        mTvCommit.setText(R.string.next);
        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);

        mRequestOrderBean = new RequestVenueOrderBean();
        mRequestOrderBean.calendarId = new long[]{mDetailsBean.calendarId};
        mRequestOrderBean.stadiumId = mDetailsBean.stadiumId;
        mRequestOrderBean.areaId = mDetailsBean.areaId;
        mRequestOrderBean.flag = 1;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {
        mItemTeam.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM,
                        InvitationBallActivity.this, ARouterConfig.Key.PARCELABLE, mMyBallTeam);
            }
        });
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(InvitationBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        mItemColor.mTvRight.setText(color);
                    });
                }
                if (!mColorDialog.isShowing() && !isFinishing()) {
                    mColorDialog.show();
                }
            }
        });
    }

    @Override
    protected InvitationBallPresenter createPresenter() {
        return new InvitationBallPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        mPresenter.createOrder(mRequestOrderBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
            mItemTeam.setContentText(mMyBallTeam.teamName);
        }
    }

    @Override
    public void resultOrderId(long result) {
        mViewModel.startActivityToOrderPay(result, Contast.PayOrderFlag.PAY_LAUNCHER_ORDER);
    }
}

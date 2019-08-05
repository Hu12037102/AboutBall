package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.CreateBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.CreateBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/5 14:17
 * 更新时间: 2019/8/5 14:17
 * 描述:创建约球
 */
@Route(path = ARouterConfig.Path.ACTIVITY_CREATE_BALL)
public class CreateBallActivity extends BaseActivity<CreateBallPresenter> implements CreateBallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.item_date)
    ItemView mItemDate;
    @BindView(R.id.item_time)
    ItemView mItemTime;
    @BindView(R.id.item_site)
    ItemView mItemSite;
    @BindView(R.id.acet_phone)
    AppCompatEditText mAcetPhone;
    private ResultMyBallTeamBean mMyBallTeam;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_ball;
    }

    @Override
    protected void initView() {
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);

        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);


    }

    @Override
    protected void initData() {
        if (UserManger.get().isLogin() && !DataUtils.isEmpty(UserManger.get().getPhone())) {
            mAcetPhone.setText(UserManger.get().getPhone());
            mAcetPhone.setSelection(DataUtils.checkData(mAcetPhone.getText()).length());
        }
    }

    @Override
    protected void initEvent() {
        mItemTeam.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM,
                        CreateBallActivity.this, ARouterConfig.Key.PARCELABLE, mMyBallTeam);
            }
        });
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(CreateBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        //mRequestBean.guestShirtColor = color;
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
    protected CreateBallPresenter createPresenter() {
        return new CreateBallPresenter(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    mItemTeam.setContentText(mMyBallTeam.teamName);
                    // mRequestBean.guestTeamId = mMyBallTeam.teamId;
                    break;
                default:
                    break;
            }
        }
    }
}

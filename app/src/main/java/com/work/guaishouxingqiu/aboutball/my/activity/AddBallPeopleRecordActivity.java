package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.AddBallPeopleRecordContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.AddBallPeopleRecordPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 17:28
 * 更新时间: 2019/6/4 17:28
 * 描述:添加球员记录Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ADD_BALL_PEOPLE_RECORD)
public class AddBallPeopleRecordActivity extends BaseActivity<AddBallPeopleRecordPresenter>
        implements AddBallPeopleRecordContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_player)
    ItemView mItemPlayer;
    @BindView(R.id.item_time)
    ItemView mItemTime;
    @BindView(R.id.item_behavior)
    ItemView mItemBehavior;
    @BindView(R.id.tv_save)
    TextView mTvSave;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_add_ball_people_record;
    }

    @Override
    protected void initView() {
        mItemPlayer.mTvRight.setHintTextColor(ContextCompat.getColor(this,R.color.colorFFA6A6A6));
        mItemPlayer.mTvRight.setHint(R.string.please_select_team_members);
        mItemTime.mTvRight.setHintTextColor(ContextCompat.getColor(this,R.color.colorFFA6A6A6));
        mItemTime.mTvRight.setHint(R.string.please_select_the_time);
        mItemBehavior.mTvRight.setHintTextColor(ContextCompat.getColor(this,R.color.colorFFA6A6A6));
        mItemBehavior.mTvRight.setHint(R.string.please_selector_team_behavior);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected AddBallPeopleRecordPresenter createPresenter() {
        return new AddBallPeopleRecordPresenter(this);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
    }
}

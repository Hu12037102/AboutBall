package com.work.guaishouxingqiu.aboutball.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestAddRecordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AddBallPeopleRecordContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.AddBallPeopleRecordPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    private ResultRefereeRecordBean mIntentBean;
    private List<String> mTeamData;
    private SingWheelDialog mTeamWheelDialog;
    private RequestAddRecordBean mRequestBean;
    private List<String> mPlayerData;
    private List<String> mTimeData;
    private SingWheelDialog mTimeDialog;
    private List<String> mBehaviorData;
    private SingWheelDialog mBehaviorDialog;
    private ResultMatchRefereeResultBean.ChildBean mEditIntentBean;

    private boolean mIsAddStatus;//是不是添加

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_add_ball_people_record;
    }

    @Override
    public void initPermission() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.please_selector_ball_team);
            finish();
            return;
        }
        mIsAddStatus = bundle.getBoolean(ARouterConfig.Key.IS_ADD, true);
        if (mIsAddStatus) {
            mIntentBean = bundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        } else {
            mEditIntentBean = bundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        }
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.please_selector_ball_team);
            finish();
            return;
        }

        super.initPermission();
    }

    @Override
    protected void initView() {
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);
        mItemPlayer.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemPlayer.mTvRight.setHint(R.string.please_select_team_members);
        mItemTime.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTime.mTvRight.setHint(R.string.please_select_the_time);
        mItemBehavior.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemBehavior.mTvRight.setHint(R.string.please_selector_team_behavior);

    }

    @Override
    protected void initData() {
        mRequestBean = new RequestAddRecordBean();
        mRequestBean.agreeId = mIntentBean.agreeId;
        mPlayerData = new ArrayList<>();
        mTeamData = new ArrayList<>();
        mTimeData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mTimeData.add(i + "");
        }
        mBehaviorData = new ArrayList<>();
        mBehaviorData.add("进球");
        mBehaviorData.add("行为");
        if (mIntentBean.hostTeamName != null) {
            mTeamData.add(mIntentBean.hostTeamName);
        }
        if (mIntentBean.guestTeamName != null) {
            mTeamData.add(mIntentBean.guestTeamName);
        }
        // mPresenter.loadMemberDetails(mHostTeamId);
    }

    @Override
    protected void initEvent() {
        mItemTeam.setOnItemClickListener(view -> {
            if (mTeamWheelDialog == null) {
                mTeamWheelDialog = new SingWheelDialog(this, mTeamData);
                mTeamWheelDialog.setTitle(R.string.players_team);
            }
            mTeamWheelDialog.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClickItem(@NonNull View view, int position) {
                    if (mTeamData.get(position).equals(mIntentBean.hostTeamName)) {
                        mRequestBean.teamId = mIntentBean.hostTeamId;
                        UIUtils.setText(mItemTeam.mTvRight, mIntentBean.hostTeamName);
                        mItemPlayer.setVisibility(View.VISIBLE);
                    } else if (mTeamData.get(position).equals(mIntentBean.guestTeamName)) {
                        mRequestBean.teamId = mIntentBean.guestTeamId;
                        UIUtils.setText(mItemTeam.mTvRight, mIntentBean.guestTeamName);
                        mItemPlayer.setVisibility(View.VISIBLE);
                    }
                    mItemPlayer.mTvRight.setText(null);
                    mRequestBean.playerId = 0;
                    mTeamWheelDialog.dismiss();
                }
            });
            if (!mTeamWheelDialog.isShowing()) {
                mTeamWheelDialog.show();
            }
        });
        mItemPlayer.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                mPresenter.loadMemberDetails(mRequestBean.teamId);
            }
        });
        mItemTime.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                if (mTimeDialog == null) {
                    mTimeDialog = new SingWheelDialog(AddBallPeopleRecordActivity.this, mTimeData);
                    mTimeDialog.setTitle(R.string.time_selection);
                }
                if (!mTimeDialog.isShowing()) {
                    mTimeDialog.show();
                }
                mTimeDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClickItem(@NonNull View view, int position) {
                        UIUtils.setText(mItemTime.mTvRight, mTimeData.get(position) + "'");
                        mRequestBean.duration = Integer.valueOf(mTimeData.get(position));
                        mTimeDialog.dismiss();
                    }
                });
            }
        });
        mItemBehavior.setOnItemClickListener(new ItemView.OnItemClickListener() {

            @Override
            public void onClickItem(View view) {
                if (mBehaviorDialog == null) {
                    mBehaviorDialog = new SingWheelDialog(AddBallPeopleRecordActivity.this, mBehaviorData);
                    mBehaviorDialog.setTitle(R.string.behavior_selector);
                }
                if (!mBehaviorDialog.isShowing()) {
                    mBehaviorDialog.show();
                }
                mBehaviorDialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClickItem(@NonNull View view, int position) {
                        UIUtils.setText(mItemBehavior.mTvRight, mBehaviorData.get(position));
                        mRequestBean.action = mBehaviorData.get(position);
                        mBehaviorDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    protected AddBallPeopleRecordPresenter createPresenter() {
        return new AddBallPeopleRecordPresenter(this);
    }

    private boolean isSelectorTeam() {
        if (mRequestBean.teamId <= 0) {
            UIUtils.showToast(R.string.please_selector_ball_team);
            return false;
        }
        return true;
    }

    private boolean isSelectorPlayer() {
        if (mRequestBean.playerId <= 0) {
            UIUtils.showToast(R.string.please_select_team_members);
            return false;
        }
        return true;
    }

    private boolean isSelectorTime() {
        if (DataUtils.isEmpty(DataUtils.getTextViewContent(mItemTime.mTvRight))) {
            UIUtils.showToast(R.string.please_select_the_time);
            return false;
        }
        return true;
    }

    private boolean isSelectorBehavior() {
        if (DataUtils.isEmpty(mRequestBean.action)) {
            UIUtils.showToast(R.string.please_selector_team_behavior);
            return false;
        }
        return true;
    }

    private boolean isSelectorAll() {
        return isSelectorTeam() && isSelectorPlayer() && isSelectorTime() && isSelectorTime() && isSelectorBehavior();
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        if (isSelectorAll()) {
            mPresenter.saveRefereePlayerRecord(mRequestBean);
        }
    }

    @Override
    public void resultMemberDetails(List<ResultTeamDetailsMemberBean> data) {
        mPlayerData.clear();
        for (ResultTeamDetailsMemberBean bean : data) {
            mPlayerData.add(bean.nickName);
        }
        SingWheelDialog playerDialog = new SingWheelDialog(this, mPlayerData);
        playerDialog.setTitle(R.string.players_choose);
        playerDialog.show();
        playerDialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                if (mPlayerData.contains(data.get(position).nickName)) {
                    mRequestBean.playerId = data.get(position).playerId;
                }
                UIUtils.setText(mItemPlayer.mTvRight, UIUtils.getString(R.string.how_match_player, data.get(position).number) + data.get(position).nickName);
                playerDialog.dismiss();
            }
        });
    }

    @Override
    public void resultSaveRecord() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}

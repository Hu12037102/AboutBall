package com.work.guaishouxingqiu.aboutball.my.activity;

import android.support.design.chip.ChipGroup;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestTeamMyDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamMyDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:31
 * 更新时间: 2019/4/28 15:31
 * 描述:对内个人信息Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_BALL_TEAM_MY_DETAILS)
public class BallTeamMyDetailsActivity extends BaseActivity<BallTeamMyDetailsPresenter> implements
        BallTeamMyDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_main)
    ItemView mItemMain;
    @BindView(R.id.item_substitution)
    ItemView mItemSubstitution;
    @BindView(R.id.vs_main)
    ViewStub mVsMain;
    @BindView(R.id.vs_substitution)
    ViewStub mVsSubstitution;
    @BindView(R.id.aet_number)
    AppCompatEditText mAetNumber;
    private boolean isCheckMain;
    private boolean isCheckSubstitution;
    private View mMainInflateView;
    private View mSubstitutionInflateView;
    private SparseArrayCompat<Boolean> mMainCheckSparse;
    private SparseArrayCompat<Boolean> mSubstitutionCheckSparse;
    private CheckBox mCbGoalkeeper1;
    private CheckBox mCbLeftDefender1;
    private CheckBox mCbCenterDefender1;
    private CheckBox mCbRightDefender1;
    private CheckBox mCbLeftWing1;
    private CheckBox mCbRightWing1;
    private CheckBox mCbShortLoin1;
    private CheckBox mCbLaterLoin1;
    private CheckBox mCbLeftCenterMidfield1;
    private CheckBox mCbRightCenterMidfield1;
    private CheckBox mCbLeftWingBack1;
    private CheckBox mCbRightWingBack1;
    private CheckBox mCbCenterForward1;
    private CheckBox mCbLeftForward1;
    private CheckBox mCbRightForward1;
    private CheckBox mCbLeftWings1;
    private CheckBox mCbRightWings1;
    private CheckBox mCbGoalkeeper2;
    private CheckBox mCbLeftDefender2;
    private CheckBox mCbCenterDefender2;
    private CheckBox mCbRightDefender2;
    private CheckBox mCbLeftWing2;
    private CheckBox mCbRightWing2;
    private CheckBox mCbShortLoin2;
    private CheckBox mCbLaterLoin2;
    private CheckBox mCbLeftCenterMidfield2;
    private CheckBox mCbRightCenterMidfield2;
    private CheckBox mCbLeftWingBack2;
    private CheckBox mCbRightWingBack2;
    private CheckBox mCbCenterForward2;
    private CheckBox mCbLeftForward2;
    private CheckBox mCbRightForward2;
    private CheckBox mCbLeftWings2;
    private CheckBox mCbRightWings2;
    private int mTag = 0;
    private ChipGroup mCgGoalkeeper1;
    private ChipGroup mCgDefender1;
    private ChipGroup mCgShortLoin1;
    private ChipGroup mCgCenterForward1;
    private List<ViewGroup> mMainData;
    private List<ViewGroup> mSubstitutionData;
    private List<CheckBox> mMainCheckData;
    private List<CheckBox> mSubstitutionCheckData;
    private static final int SUBSTITUTION_CHECK_MAX_COUNT = 3;
    private ResultTeamDetailsMemberBean mMyMemberBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_my_details;
    }

    @Override
    public void initPermission() {
        mMyMemberBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mMyMemberBean.playerId == -1) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {

        mItemMain.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemMain.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemMain.mTvRight.setHint(R.string.please_selector_location);
        mItemSubstitution.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemSubstitution.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemSubstitution.mTvRight.setHint(R.string.please_selector_the_backup);
        mAetNumber.setText(mMyMemberBean.number + "");
        mAetNumber.setSelection(DataUtils.checkData(mAetNumber.getText() == null ? 0 : mAetNumber.getText().length()));
        UIUtils.setText(mItemMain.mTvRight, mMyMemberBean.position);
    }

    @Override
    protected void initData() {
        String itemMainTitle = UIUtils.getString(R.string.main_location);
        mItemMain.setTitleText(SpanUtils.getTextColor(R.color.colorFFA6A6A6, 3, itemMainTitle.length(), itemMainTitle));
        String itemSubstitutionTitle = UIUtils.getString(R.string.the_backup);
        mItemSubstitution.setTitleText(SpanUtils.getTextColor(R.color.colorFFA6A6A6, 4, itemSubstitutionTitle.length(), itemSubstitutionTitle));
        mMainCheckSparse = new SparseArrayCompat<>();
        mSubstitutionCheckSparse = new SparseArrayCompat<>();
        mMainData = new ArrayList<>();
        mSubstitutionData = new ArrayList<>();
        mMainCheckData = new ArrayList<>();
        mSubstitutionCheckData = new ArrayList<>();

        initMainSubView();
        initSubstitutionSubView();
        initMainCheckStatus();
    }

    private void initMainCheckStatus() {
        if (!DataUtils.isEmpty(mMyMemberBean.position)) {

            for (int i = 0; i < mMainData.size(); i++) {
                ChipGroup cg = (ChipGroup) mMainData.get(i);
                for (int j = 0; j < cg.getChildCount(); j++) {
                    CheckBox checkBox = (CheckBox) cg.getChildAt(j);
                    if (checkBox.getText().toString().equals(mMyMemberBean.position)) {
                        checkBox.setChecked(true);
                        for (int h = 0; h < mMainCheckSparse.size(); h++) {
                            if (mMainCheckSparse.keyAt(h) == checkBox.getId()) {
                                mMainCheckSparse.put(mMainCheckSparse.keyAt(h), checkBox.isChecked());
                                mTag = 1;
                                judgeCheckStatus(checkBox, checkBox.isChecked());
                            }
                        }
                    }
                }
            }
        }
    }

    private void initMainSubView() {
        if (mMainInflateView == null) {
            mMainInflateView = mVsMain.inflate();
            mMainInflateView.setVisibility(View.GONE);
            mCgGoalkeeper1 = mMainInflateView.findViewById(R.id.cg_goalkeeper);
            mMainData.add(mCgGoalkeeper1);
            mCbGoalkeeper1 = mMainInflateView.findViewById(R.id.cb_goalkeeper);
            mMainCheckSparse.put(mCbGoalkeeper1.getId(), false);

            mCgDefender1 = mMainInflateView.findViewById(R.id.cg_defender);
            mMainData.add(mCgDefender1);
            mCbLeftDefender1 = mMainInflateView.findViewById(R.id.cb_left_defender);
            mMainCheckSparse.put(mCbLeftDefender1.getId(), false);
            mCbCenterDefender1 = mMainInflateView.findViewById(R.id.cb_center_defender);
            mMainCheckSparse.put(mCbCenterDefender1.getId(), false);
            mCbRightDefender1 = mMainInflateView.findViewById(R.id.cb_right_defender);
            mMainCheckSparse.put(mCbRightDefender1.getId(), false);
            mCbLeftWing1 = mMainInflateView.findViewById(R.id.cb_left_wing);
            mMainCheckSparse.put(mCbLeftWing1.getId(), false);
            mCbRightWing1 = mMainInflateView.findViewById(R.id.db_right_wing);
            mMainCheckSparse.put(mCbRightWing1.getId(), false);

            mCgShortLoin1 = mMainInflateView.findViewById(R.id.cg_short_loin);
            mMainData.add(mCgShortLoin1);
            mCbShortLoin1 = mMainInflateView.findViewById(R.id.cb_short_loin);
            mMainCheckSparse.put(mCbShortLoin1.getId(), false);
            mCbLaterLoin1 = mMainInflateView.findViewById(R.id.cb_later_loin);
            mMainCheckSparse.put(mCbLaterLoin1.getId(), false);
            mCbLeftCenterMidfield1 = mMainInflateView.findViewById(R.id.cb_left_center_midfield);
            mMainCheckSparse.put(mCbLeftCenterMidfield1.getId(), false);
            mCbRightCenterMidfield1 = mMainInflateView.findViewById(R.id.cb_right_center_midfield);
            mMainCheckSparse.put(mCbRightCenterMidfield1.getId(), false);
            mCbLeftWingBack1 = mMainInflateView.findViewById(R.id.cb_left_wing_back);
            mMainCheckSparse.put(mCbLeftWingBack1.getId(), false);
            mCbRightWingBack1 = mMainInflateView.findViewById(R.id.cb_right_wing_back);
            mMainCheckSparse.put(mCbRightWingBack1.getId(), false);

            mCgCenterForward1 = mMainInflateView.findViewById(R.id.cg_center_forward);
            mMainData.add(mCgCenterForward1);
            mCbCenterForward1 = mMainInflateView.findViewById(R.id.cb_center_forward);
            mMainCheckSparse.put(mCbCenterForward1.getId(), false);
            mCbLeftForward1 = mMainInflateView.findViewById(R.id.cb_left_forward);
            mMainCheckSparse.put(mCbLeftForward1.getId(), false);
            mCbRightForward1 = mMainInflateView.findViewById(R.id.cb_right_forward);
            mMainCheckSparse.put(mCbRightForward1.getId(), false);
            mCbLeftWings1 = mMainInflateView.findViewById(R.id.cb_left_wings);
            mMainCheckSparse.put(mCbLeftWings1.getId(), false);
            mCbRightWings1 = mMainInflateView.findViewById(R.id.cb_right_wings);
            mMainCheckSparse.put(mCbRightWings1.getId(), false);


            Log.w("chipGroup--", mCbGoalkeeper1.getId() + "--");
            //  mMainCheckSparse.put(mCbGoalkeeper.getId());
            // Log.w("chipGroup--", chipGroup.getChildAt(0).getId() + "---" + mCbGoalkeeper.getId());
        }
    }

    private void initSubstitutionSubView() {
        if (mSubstitutionInflateView == null) {
            mSubstitutionInflateView = mVsSubstitution.inflate();
            mSubstitutionInflateView.setVisibility(View.GONE);
            ChipGroup mCgGoalkeeper2 = mSubstitutionInflateView.findViewById(R.id.cg_goalkeeper);
            mSubstitutionData.add(mCgGoalkeeper2);
            mCbGoalkeeper2 = mSubstitutionInflateView.findViewById(R.id.cb_goalkeeper);
            mSubstitutionCheckSparse.put(mCbGoalkeeper2.getId(), false);

            ChipGroup mCgDefender2 = mSubstitutionInflateView.findViewById(R.id.cg_defender);
            mSubstitutionData.add(mCgDefender2);
            mCbLeftDefender2 = mSubstitutionInflateView.findViewById(R.id.cb_left_defender);
            mSubstitutionCheckSparse.put(mCbLeftDefender2.getId(), false);
            mCbCenterDefender2 = mSubstitutionInflateView.findViewById(R.id.cb_center_defender);
            mSubstitutionCheckSparse.put(mCbCenterDefender2.getId(), false);
            mCbRightDefender2 = mSubstitutionInflateView.findViewById(R.id.cb_right_defender);
            mSubstitutionCheckSparse.put(mCbRightDefender2.getId(), false);
            mCbLeftWing2 = mSubstitutionInflateView.findViewById(R.id.cb_left_wing);
            mSubstitutionCheckSparse.put(mCbLeftWing2.getId(), false);
            mCbRightWing2 = mSubstitutionInflateView.findViewById(R.id.db_right_wing);
            mSubstitutionCheckSparse.put(mCbRightWing2.getId(), false);

            ChipGroup mCgShortLoin2 = mSubstitutionInflateView.findViewById(R.id.cg_short_loin);
            mSubstitutionData.add(mCgShortLoin2);
            mCbShortLoin2 = mSubstitutionInflateView.findViewById(R.id.cb_short_loin);
            mSubstitutionCheckSparse.put(mCbShortLoin2.getId(), false);
            mCbLaterLoin2 = mSubstitutionInflateView.findViewById(R.id.cb_later_loin);
            mSubstitutionCheckSparse.put(mCbLaterLoin2.getId(), false);
            mCbLeftCenterMidfield2 = mSubstitutionInflateView.findViewById(R.id.cb_left_center_midfield);
            mSubstitutionCheckSparse.put(mCbLeftCenterMidfield2.getId(), false);
            mCbRightCenterMidfield2 = mSubstitutionInflateView.findViewById(R.id.cb_right_center_midfield);
            mSubstitutionCheckSparse.put(mCbRightCenterMidfield2.getId(), false);
            mCbLeftWingBack2 = mSubstitutionInflateView.findViewById(R.id.cb_left_wing_back);
            mSubstitutionCheckSparse.put(mCbLeftWingBack2.getId(), false);
            mCbRightWingBack2 = mSubstitutionInflateView.findViewById(R.id.cb_right_wing_back);
            mSubstitutionCheckSparse.put(mCbRightWingBack2.getId(), false);

            ChipGroup mCgCenterForward2 = mSubstitutionInflateView.findViewById(R.id.cg_center_forward);
            mSubstitutionData.add(mCgCenterForward2);
            mCbCenterForward2 = mSubstitutionInflateView.findViewById(R.id.cb_center_forward);
            mSubstitutionCheckSparse.put(mCbCenterForward2.getId(), false);
            mCbLeftForward2 = mSubstitutionInflateView.findViewById(R.id.cb_left_forward);
            mSubstitutionCheckSparse.put(mCbLeftForward2.getId(), false);
            mCbRightForward2 = mSubstitutionInflateView.findViewById(R.id.cb_right_forward);
            mSubstitutionCheckSparse.put(mCbRightForward2.getId(), false);
            mCbLeftWings2 = mSubstitutionInflateView.findViewById(R.id.cb_left_wings);
            mSubstitutionCheckSparse.put(mCbLeftWings2.getId(), false);
            mCbRightWings2 = mSubstitutionInflateView.findViewById(R.id.cb_right_wings);
            mSubstitutionCheckSparse.put(mCbRightWings2.getId(), false);


            Log.w("chipGroup---", mCbGoalkeeper2.getId() + "--");
        }
    }


    @Override
    protected void initEvent() {
        mItemMain.setOnItemClickListener(view -> {
            clickItemMain();
        });
        mItemSubstitution.setOnItemClickListener(view -> {
            clickItemSubstitution();
        });

        mCbGoalkeeper1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbGoalkeeper1, mCbGoalkeeper1.isChecked());
        });
        mCbLeftDefender1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftDefender1, mCbLeftDefender1.isChecked());
        });
        mCbCenterDefender1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbCenterDefender1, mCbCenterDefender1.isChecked());
        });
        mCbRightDefender1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightDefender1, mCbRightDefender1.isChecked());
        });
        mCbLeftWing1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftWing1, mCbLeftWing1.isChecked());
        });
        mCbRightWing1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightWing1, mCbRightWing1.isChecked());
        });
        mCbShortLoin1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbShortLoin1, mCbShortLoin1.isChecked());
        });
        mCbLaterLoin1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLaterLoin1, mCbLaterLoin1.isChecked());

        });
        mCbLeftCenterMidfield1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftCenterMidfield1, mCbLeftCenterMidfield1.isChecked());
        });
        mCbRightCenterMidfield1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightCenterMidfield1, mCbRightCenterMidfield1.isChecked());
        });
        mCbLeftWingBack1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftWingBack1, mCbLeftWingBack1.isChecked());
        });
        mCbRightWingBack1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightWingBack1, mCbRightWingBack1.isChecked());
        });
        mCbCenterForward1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbCenterForward1, mCbCenterForward1.isChecked());
        });
        mCbLeftForward1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftForward1, mCbLeftForward1.isChecked());
        });
        mCbRightForward1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightForward1, mCbRightForward1.isChecked());
        });
        mCbLeftWings1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbLeftWings1, mCbLeftWings1.isChecked());
        });
        mCbRightWings1.setOnClickListener(v -> {
            mTag = 1;
            judgeCheckStatus(mCbRightWings1, mCbRightWings1.isChecked());
        });

        mCbGoalkeeper2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbGoalkeeper2, mCbGoalkeeper2.isChecked());
        });
        mCbLeftDefender2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftDefender2, mCbLeftDefender2.isChecked());
        });
        mCbCenterDefender2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbCenterDefender2, mCbCenterDefender2.isChecked());
        });
        mCbRightDefender2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightDefender2, mCbRightDefender2.isChecked());
        });
        mCbLeftWing2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftWing2, mCbLeftWing2.isChecked());
        });
        mCbRightWing2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightWing2, mCbRightWing2.isChecked());
        });
        mCbShortLoin2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbShortLoin2, mCbShortLoin2.isChecked());
        });
        mCbLaterLoin2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLaterLoin2, mCbLaterLoin2.isChecked());
        });
        mCbLeftCenterMidfield2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftCenterMidfield2, mCbLeftCenterMidfield2.isChecked());
        });
        mCbRightCenterMidfield2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightCenterMidfield2, mCbRightCenterMidfield2.isChecked());
        });
        mCbLeftWingBack2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftWingBack2, mCbLeftWingBack2.isChecked());
        });
        mCbRightWingBack2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightWingBack2, mCbRightWingBack2.isChecked());
        });
        mCbCenterForward2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbCenterForward2, mCbCenterForward2.isChecked());
        });
        mCbLeftForward2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftForward2, mCbLeftForward2.isChecked());
        });
        mCbRightForward2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightForward2, mCbRightForward2.isChecked());
        });
        mCbLeftWings2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbLeftWings2, mCbLeftWings2.isChecked());
        });
        mCbRightWings2.setOnClickListener(v -> {
            mTag = 2;
            judgeCheckStatus(mCbRightWings2, mCbRightWings2.isChecked());
        });
    }

    private void clickItemMain() {
        isCheckMain = !isCheckMain;
        mItemMain.setContentIcon(0, 0, isCheckMain ? R.mipmap.icon_item_top : R.mipmap.icon_item_bottom, 0);
        mMainInflateView.setVisibility(isCheckMain ? View.VISIBLE : View.GONE);
    }


    private void clickItemSubstitution() {
        isCheckSubstitution = !isCheckSubstitution;
        mItemSubstitution.setContentIcon(0, 0, isCheckSubstitution ? R.mipmap.icon_item_top : R.mipmap.icon_item_bottom, 0);
        mSubstitutionInflateView.setVisibility(isCheckSubstitution ? View.VISIBLE : View.GONE);
    }


    @Override
    protected BallTeamMyDetailsPresenter createPresenter() {
        return new BallTeamMyDetailsPresenter(this);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        if (isInputNumber() && isInputMainLocation() && isInputSubstitutionLocation()) {
            RequestTeamMyDetailsBean bean = new RequestTeamMyDetailsBean();
            bean.number = DataUtils.getEditDetails(mAetNumber);
            bean.alternate = getLocationArray(mSubstitutionCheckData);
            bean.playerId = mMyMemberBean.playerId;
            bean.position = getCheckContent(mMainCheckData);
            mPresenter.saveMyDetails(bean);
        }
    }

    public String getCheckContent(List<CheckBox> checkData) {
        String content = "";
        for (int i = 0; i < checkData.size(); i++) {
            if (checkData.get(i).isChecked()) {
                content = content.concat(checkData.get(i).getText().toString());
                if (checkData.size() > 1 && i != checkData.size() - 1) {
                    content = content + " ";
                }
            }
        }
        return DataUtils.isEmpty(content) ? null : content;
    }

    private void judgeCheckStatus(CheckBox checkBox, boolean isChecked) {
        if (mTag == 1) {
            if (mMainCheckData.size() > 0) {
                if (!mMainCheckData.contains(checkBox)) {
                    mMainCheckData.get(0).setEnabled(true);
                    mMainCheckData.get(0).setChecked(false);
                    mMainCheckData.get(0).setBackgroundResource(R.drawable.selector_venue_count_view);
                    mMainCheckData.get(0).setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_blue_white_color));
                    for (int h = 0; h < mSubstitutionData.size(); h++) {
                        ViewGroup viewGroup = mSubstitutionData.get(h);
                        setUnCheckViewGroup(viewGroup, mMainCheckData.get(0).getId(), mMainCheckData.get(0).isChecked());
                    }
                    mMainCheckData.clear();
                    mMainCheckData.add(checkBox);
                }

            } else {
                mMainCheckData.add(checkBox);
            }
            for (int i = 0; i < mMainData.size(); i++) {
                ViewGroup viewGroup = mMainData.get(i);
                setCheckViewGroup(viewGroup, isChecked, checkBox);
            }
            for (int h = 0; h < mSubstitutionData.size(); h++) {
                ViewGroup viewGroup = mSubstitutionData.get(h);
                setUnCheckViewGroup(viewGroup, checkBox.getId(), isChecked);
            }

            mItemMain.setContentText(getCheckContent(mMainCheckData));

        } else if (mTag == 2) {
            if (mSubstitutionCheckData.size() < SUBSTITUTION_CHECK_MAX_COUNT && !mSubstitutionCheckData.contains(checkBox)) {
                mSubstitutionCheckData.add(checkBox);
            } else {
                if (!mSubstitutionCheckData.contains(checkBox)) {
                    mSubstitutionCheckData.get(0).setEnabled(true);
                    mSubstitutionCheckData.get(0).setChecked(false);
                    mSubstitutionCheckData.get(0).setBackgroundResource(R.drawable.selector_venue_count_view);
                    mSubstitutionCheckData.get(0).setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_blue_white_color));
                    for (int h = 0; h < mMainData.size(); h++) {
                        ViewGroup viewGroup = mMainData.get(h);
                        setUnCheckViewGroup(viewGroup, mSubstitutionCheckData.get(0).getId(), mSubstitutionCheckData.get(0).isChecked());
                    }
                    mSubstitutionCheckData.remove(0);
                    mSubstitutionCheckData.add(checkBox);
                } else {
                    checkBox.setChecked(false);
                    mSubstitutionCheckData.remove(checkBox);
                }

            }


            for (int i = 0; i < mSubstitutionData.size(); i++) {
                ViewGroup viewGroup = mSubstitutionData.get(i);
                setCheckViewGroup(viewGroup, isChecked, checkBox);
            }
            for (int h = 0; h < mMainData.size(); h++) {
                ViewGroup viewGroup = mMainData.get(h);
                setUnCheckViewGroup(viewGroup, checkBox.getId(), isChecked);
            }
            mItemSubstitution.setContentText(getCheckContent(mSubstitutionCheckData));

        }


    }

    private void setCheckViewGroup(ViewGroup viewGroup, boolean isCheck, CheckBox checkView) {
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(j);
            if (checkBox.getId() == checkView.getId()) {
                checkView.setEnabled(true);
                checkView.setChecked(isCheck);
                checkView.setBackgroundResource(R.drawable.selector_venue_count_view);
                checkView.setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_blue_white_color));
            }
        }
    }


    private void setUnCheckViewGroup(ViewGroup viewGroup, int checkResId, boolean isCheck) {
        for (int k = 0; k < viewGroup.getChildCount(); k++) {
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(k);
            if (checkBox.getId() == checkResId) {
                if (isCheck) {
                    checkBox.setChecked(false);
                    checkBox.setEnabled(false);
                    checkBox.setBackgroundResource(R.drawable.selector_venue_count_view);
                    checkBox.setTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
                } else {
                    checkBox.setChecked(false);
                    checkBox.setEnabled(true);
                    checkBox.setBackgroundResource(R.drawable.selector_venue_count_view);
                    checkBox.setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_blue_white_color));
                }
            }
        }
    }

    private boolean isInputNumber() {
        if (DataUtils.isEmpty(DataUtils.getEditDetails(mAetNumber))) {
            UIUtils.showToast(R.string.please_enter_the_jersey_number);
            return false;
        }
        return true;
    }

    private boolean isInputMainLocation() {
        if (DataUtils.isEmpty(getCheckContent(mMainCheckData))) {
            UIUtils.showToast(R.string.please_selector_main_location);
            return false;
        }
        return true;
    }

    private boolean isInputSubstitutionLocation() {
        if (DataUtils.isEmpty(getCheckContent(mSubstitutionCheckData))) {
            UIUtils.showToast(R.string.please_selector_substitution_location);
            return false;
        }
        return true;
    }

    public String[] getLocationArray(List<CheckBox> checkData) {
        List<String> contentData = new ArrayList<>();

        for (int i = 0; i < checkData.size(); i++) {
            if (checkData.get(i).isChecked()) {
                contentData.add(checkData.get(i).getText().toString());
            }
        }
        String[] contentArray = new String[contentData.size()];
        return contentData.toArray(contentArray);
    }

    @Override
    public void resultMyDetailsSucceed() {
        finish();
    }
}
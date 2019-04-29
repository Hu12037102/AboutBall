package com.work.guaishouxingqiu.aboutball.my.activity;

import android.support.design.chip.ChipGroup;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamMyDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;

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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_my_details;
    }

    @Override
    protected void initView() {

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

        mCbGoalkeeper1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftDefender1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbCenterDefender1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightDefender1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWing1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWing1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbShortLoin1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLaterLoin1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);

        });
        mCbLeftCenterMidfield1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightCenterMidfield1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWingBack1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWingBack1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbCenterForward1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftForward1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightForward1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWings1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWings1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 1;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });

        mCbGoalkeeper2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftDefender2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbCenterDefender2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightDefender2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWing2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWing2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbShortLoin2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLaterLoin2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftCenterMidfield2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightCenterMidfield2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWingBack2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWingBack2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbCenterForward2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftForward2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightForward2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbLeftWings2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
        });
        mCbRightWings2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mTag = 2;
            judgeCheckStatus(buttonView.getId(), isChecked);
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
    }


    private void judgeCheckStatus(int checkResId, boolean isChecked) {
        if (mTag == 1) {
            for (int i = 0; i < mMainData.size(); i++) {
                ViewGroup viewGroup = mMainData.get(i);
                setCheckViewGroup(viewGroup, isChecked, checkResId);
            }
            for (int h = 0; h < mSubstitutionData.size(); h++) {
                ViewGroup viewGroup = mSubstitutionData.get(h);
                setUnCheckViewGroup(viewGroup, checkResId, isChecked);
            }

        } else if (mTag == 2) {
            for (int i = 0; i < mSubstitutionData.size(); i++) {
                ViewGroup viewGroup = mSubstitutionData.get(i);
                setCheckViewGroup(viewGroup, isChecked, checkResId);
            }
            for (int h = 0; h < mMainData.size(); h++) {
                ViewGroup viewGroup = mMainData.get(h);
                setUnCheckViewGroup(viewGroup, checkResId, isChecked);
            }

        }

    }

    private void setCheckViewGroup(ViewGroup viewGroup, boolean isCheck, int checkResId) {
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(j);
            if (checkBox.getId() == checkResId) {
                checkBox.setEnabled(true);
                checkBox.setChecked(isCheck);
                checkBox.setBackgroundResource(R.drawable.selector_venue_count_view);
                checkBox.setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_blue_white_color));

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
}
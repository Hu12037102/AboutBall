package com.work.guaishouxingqiu.aboutball.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.MatchRefereeResultAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestActionRecordsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MatchRefereeResultPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:39
 * 更新时间: 2019/6/4 13:39
 * 描述:赛况记录Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MATCH_REFEREE_RESULT)
public class MatchRefereeResultActivity extends BaseActivity<MatchRefereeResultPresenter> implements
        MatchRefereeResultContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.item_host_case)
    ItemView mItemHostCase;
    @BindView(R.id.item_guest_case)
    ItemView mItemGuestCase;
    @BindView(R.id.ll_not_join)
    LinearLayout mLlNotJoin;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private View mHeadView;
    private MatchRefereeResultAdapter mAdapter;
    private List<ResultMatchRefereeResultBean> mData;
    private ResultRefereeRecordBean mIntentBean;
    private static final String NOT_DATA_VIEW_CONTENT = "缺席";
    private ItemView mHeadItemHostCase;
    private ItemView mHeadItemGuestCase;
    private List<String> mScoreData;
    private RequestActionRecordsBean mRequestBean;
    private ItemView mHeadItemHostScore;
    private ItemView mHeadItemGuestScore;
    private boolean isAbsent;//是不是缺席

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_result;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_match_result);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mTvCommit.setText(R.string.complete);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mRequestBean = new RequestActionRecordsBean();
        mRequestBean.agreeId = mIntentBean.agreeId;
        initHeadView();
        initAdapter();
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mAdapter = new MatchRefereeResultAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
    }

    private void initHeadView() {
        mScoreData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mScoreData.add(i + "");
        }
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_match_referee_result_view, (ViewGroup) getWindow().getDecorView(), false);

        mHeadItemHostCase = mHeadView.findViewById(R.id.item_host_case);
        mHeadItemHostCase.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mHeadItemHostCase.mTvRight.setHint(R.string.please_select_your_presence);
        UIUtils.setText(mHeadItemHostCase.mTvLeft, mIntentBean.hostTeamName);


        mHeadItemGuestCase = mHeadView.findViewById(R.id.item_guest_case);
        mHeadItemGuestCase.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mHeadItemGuestCase.mTvRight.setHint(R.string.please_select_your_presence);
        UIUtils.setText(mHeadItemGuestCase.mTvLeft, mIntentBean.guestTeamName);


        mHeadItemHostCase.setOnItemClickListener(view -> {
            clickSituationItem(mHeadItemHostCase);

        });
        mHeadItemGuestCase.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickSituationItem(mHeadItemGuestCase);

            }
        });

        mHeadItemHostScore = mHeadView.findViewById(R.id.item_host_score);
        mHeadItemHostScore.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mHeadItemHostScore.mTvRight.setHint(R.string.please_selector_score);
        UIUtils.setText(mHeadItemHostScore.mTvLeft, mIntentBean.hostTeamName);
        mHeadItemHostScore.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickScoreDialog(mHeadItemHostScore);
            }
        });

        mHeadItemGuestScore = mHeadView.findViewById(R.id.item_guest_score);
        mHeadItemGuestScore.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mHeadItemGuestScore.mTvRight.setHint(R.string.please_selector_score);
        UIUtils.setText(mHeadItemGuestScore.mTvLeft, mIntentBean.guestTeamName);
        mHeadItemGuestScore.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickScoreDialog(mHeadItemGuestScore);
            }
        });

        TextView mTvRecord = mHeadView.findViewById(R.id.tv_add_record);
        mTvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityToAddRecordForResult( null);
            }
        });
    }

    private void startActivityToAddRecordForResult( ResultMatchRefereeResultBean.ChildBean bean) {
        Bundle bundle = new Bundle();

        bundle.putParcelable(ARouterConfig.Key.PARCELABLE, mIntentBean);
        bundle.putParcelable(ARouterConfig.Key.PARCELABLE_EDIT, bean);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ADD_BALL_PEOPLE_RECORD, this, bundle);
    }

    private void clickScoreDialog(ItemView itemView) {
        SingWheelDialog singWheelDialog = new SingWheelDialog(this, mScoreData);
        singWheelDialog.setTitle(R.string.score_situation);
        singWheelDialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                UIUtils.setText(itemView.mTvRight, mScoreData.get(position));
                LogUtils.w("clickScoreDialog--", itemView.getId() + "--" + R.id.item_host_case);
                switch (itemView.getId()) {
                    case R.id.item_host_score:
                        mRequestBean.hostScore = Integer.valueOf(mScoreData.get(position));
                        break;
                    case R.id.item_guest_score:
                        mRequestBean.guestScore = Integer.valueOf(mScoreData.get(position));
                        break;
                    default:
                        break;
                }
            }
        });
        singWheelDialog.show();
    }

    private void clickSituationItem(ItemView itemView) {
        String[] situationArray = getResources().getStringArray(R.array.present_situation_array);
        SingWheelDialog singWheelDialog = new SingWheelDialog(this, Arrays.asList(situationArray));
        singWheelDialog.setTitle(R.string.present_situation);
        singWheelDialog.setOnItemClickListener((view, position) -> {
            UIUtils.setText(itemView.mTvRight, situationArray[position]);
            switch (itemView.getId()) {
                case R.id.item_host_case:
                    UIUtils.setText(mItemHostCase.mTvRight, situationArray[position]);
                    UIUtils.setText(mHeadItemHostCase.mTvRight, situationArray[position]);
                    mRequestBean.hostArrived = situationArray[position];
                    break;
                case R.id.item_guest_case:
                    UIUtils.setText(mItemGuestCase.mTvRight, situationArray[position]);
                    UIUtils.setText(mHeadItemGuestCase.mTvRight, situationArray[position]);
                    mRequestBean.guestArrived = situationArray[position];
                    break;
                default:
                    break;
            }
            setContentViewVisibility();
        });
        singWheelDialog.show();
    }


    private void setContentViewVisibility() {
        if (DataUtils.getTextViewContent(mItemHostCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)
                || DataUtils.getTextViewContent(mItemGuestCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)) {
            mLlNotJoin.setVisibility(View.VISIBLE);
            isAbsent = true;
        } else {
            mLlNotJoin.setVisibility(View.GONE);
            isAbsent = false;
        }
        if (DataUtils.getTextViewContent(mHeadItemHostCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)
                || DataUtils.getTextViewContent(mHeadItemGuestCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)) {
            mLlNotJoin.setVisibility(View.VISIBLE);
            isAbsent = true;
        } else {
            mLlNotJoin.setVisibility(View.GONE);
            isAbsent = false;
        }
    }

    @Override
    protected void initData() {
        mPresenter.loadRecordDetails(mIntentBean.agreeId);
        mSrlRefresh.autoRefresh();
        mItemHostCase.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemHostCase.mTvRight.setHint(R.string.please_select_your_presence);
        UIUtils.setText(mItemHostCase.mTvLeft, mIntentBean.hostTeamName);

        UIUtils.setText(mItemGuestCase.mTvLeft, mIntentBean.guestTeamName);
        mItemGuestCase.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemGuestCase.mTvRight.setHint(R.string.please_select_your_presence);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadMatchRecord(mIntentBean.agreeId/*67*/);
            refreshLayout.finishRefresh();
        });
        mItemHostCase.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickSituationItem(mItemHostCase);

            }
        });
        mItemGuestCase.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickSituationItem(mItemGuestCase);

            }
        });
        mAdapter.setOnEditClickListener(new MatchRefereeResultAdapter.OnEditClickListener() {
            @Override
            public void clickEdit(View view, ResultMatchRefereeResultBean.ChildBean bean) {
                startActivityToAddRecordForResult( bean);
            }
        });
    }

    @Override
    protected MatchRefereeResultPresenter createPresenter() {
        return new MatchRefereeResultPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (isAbsent) {
            if (isSelectorSituation()) {
                mPresenter.goActionRecord(mRequestBean);
            }
        } else {
            if (isSelectorSituation() && isSelectorScore()) {
                mPresenter.goActionRecord(mRequestBean);
            }
        }

    }

    @Override
    public void resultMatchRecord(List<ResultMatchRefereeResultBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultActionRecord() {
        finish();
    }

    @Override
    public void resultRecordDetails(ResultRefereeRecordDetailsBean bean) {
        UIUtils.setText(mItemHostCase.mTvRight, bean.hostArrived);
        UIUtils.setText(mItemGuestCase.mTvRight, bean.guestArrived);
        UIUtils.setText(mHeadItemHostCase.mTvRight, bean.hostArrived);
        UIUtils.setText(mHeadItemGuestCase.mTvRight, bean.guestArrived);
        UIUtils.setText(mHeadItemHostScore.mTvRight, bean.hostScore + "");
        UIUtils.setText(mHeadItemGuestScore.mTvRight, bean.guestScore + "");
        setContentViewVisibility();
        mRequestBean.hostArrived = bean.hostArrived;
        mRequestBean.guestArrived = bean.guestArrived;
        mRequestBean.hostScore = bean.hostScore;
        mRequestBean.guestScore = bean.guestScore;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    mSrlRefresh.autoRefresh();
                    break;
                default:
                    break;
            }
        }
    }

    private boolean isSelectorSituation() {
        if (DataUtils.isEmpty(mRequestBean.hostArrived) || DataUtils.isEmpty(mRequestBean.guestArrived)) {
            UIUtils.showToast(R.string.please_select_your_presence);
            return false;
        }
        return true;
    }

    private boolean isSelectorScore() {
        if (DataUtils.isEmpty(DataUtils.getTextViewContent(mHeadItemHostScore.mTvRight))
                || DataUtils.isEmpty(DataUtils.getTextViewContent(mHeadItemGuestScore.mTvRight))) {
            UIUtils.showToast(R.string.please_selector_score);
            return false;
        }
        return true;
    }
}

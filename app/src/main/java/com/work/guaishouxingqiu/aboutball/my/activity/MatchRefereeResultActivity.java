package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.MatchRefereeResultAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MatchRefereeResultPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    public static final String NOT_DATA_VIEW_CONTENT = "缺席";
    private ItemView mHeadItemHostCase;
    private ItemView mHeadItemGuestCase;
    private List<String> mScoreData;

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
        mRvData.setLayoutManager(new LinearLayoutManager(this));
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
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_match_referee_result_view, mRvData, false);

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

        ItemView mItemHostScore = mHeadView.findViewById(R.id.item_host_score);
        mItemHostScore.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemHostScore.mTvRight.setHint(R.string.please_selector_score);
        UIUtils.setText(mItemHostScore.mTvLeft, mIntentBean.hostTeamName);
        mItemHostScore.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickScoreDialog(mItemHostScore);
            }
        });

        ItemView mItemGuestScore = mHeadView.findViewById(R.id.item_guest_score);
        mItemGuestScore.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemGuestScore.mTvRight.setHint(R.string.please_selector_score);
        UIUtils.setText(mItemGuestScore.mTvLeft, mIntentBean.guestTeamName);
        mItemGuestScore.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                clickScoreDialog(mItemGuestScore);
            }
        });

        TextView mTvRecord = mHeadView.findViewById(R.id.tv_add_record);
        mTvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void startActivityToAddRecordForResult() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARouterConfig.Key.PARCELABLE, mIntentBean);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ADD_BALL_PEOPLE_RECORD, this, bundle);
    }

    private void clickScoreDialog(ItemView itemView) {
        SingWheelDialog singWheelDialog = new SingWheelDialog(this, mScoreData);
        singWheelDialog.setTitle(R.string.score_situation);
        singWheelDialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                UIUtils.setText(itemView.mTvRight, mScoreData.get(position));
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
                    break;
                case R.id.item_guest_case:
                    UIUtils.setText(mItemGuestCase.mTvRight, situationArray[position]);
                    UIUtils.setText(mHeadItemGuestCase.mTvRight, situationArray[position]);
                    break;
                default:
                    break;
            }
            if (DataUtils.getTextViewContent(mItemHostCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)
                    || DataUtils.getTextViewContent(mItemGuestCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)) {
                mLlNotJoin.setVisibility(View.VISIBLE);
            } else {
                mLlNotJoin.setVisibility(View.GONE);
            }
            if (DataUtils.getTextViewContent(mHeadItemHostCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)
                    || DataUtils.getTextViewContent(mHeadItemGuestCase.mTvRight).equals(NOT_DATA_VIEW_CONTENT)) {
                mLlNotJoin.setVisibility(View.VISIBLE);
            } else {
                mLlNotJoin.setVisibility(View.GONE);
            }
        });
        singWheelDialog.show();
    }

    @Override
    protected void initData() {
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
    }

    @Override
    protected MatchRefereeResultPresenter createPresenter() {
        return new MatchRefereeResultPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {

    }

    @Override
    public void resultMatchRecord(List<ResultMatchRefereeResultBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}

package com.ifeell.app.aboutball.game.activity;

import android.app.Dialog;

import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.game.adapter.GameInfoGroupAdapter;
import com.ifeell.app.aboutball.game.adapter.GameInfoOtherAdapter;
import com.ifeell.app.aboutball.game.adapter.GameInfoScoreboardAdapter;
import com.ifeell.app.aboutball.game.bean.ResultGameFiltrateBean;
import com.ifeell.app.aboutball.game.bean.ResultGameGroupBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoOtherBean;
import com.ifeell.app.aboutball.game.bean.ResultGameInfoScoreboardBean;
import com.ifeell.app.aboutball.game.contract.GameInfoContract;
import com.ifeell.app.aboutball.game.presenter.GameInfoPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.SingWheelDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/25 11:34
 * 更新时间: 2019/6/25 11:34
 * 描述:比赛-数据列表
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GAME_INFO)
public class GameInfoActivity extends BaseActivity<GameInfoPresenter> implements GameInfoContract.View {
    @BindView(R.id.item_match)
    ItemView mItemMatch;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.rv_grouping)
    RecyclerView mRvGrouping;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.rv_other_data)
    RecyclerView mRvOtherData;
    @BindView(R.id.ll_other)
    LinearLayout mLlOther;
    @BindView(R.id.ll_scoreboard)
    LinearLayout mLlScoreboard;
    @BindView(R.id.tv_goals)
    TextView mTvGoals;
    private long mRequestGameId = -1;
    private Long mRequestGroupId ;
    private int mRequestAction = GameInfoActivity.ACTION_SHOT;
    private List<String> mDialogData;
    private List<ResultGameFiltrateBean> mFiltrateData;
    private List<ResultGameGroupBean> mGroupData;
    private GameInfoGroupAdapter mGroupAdapter;
    private GameInfoScoreboardAdapter mScoreboardAdapter;
    private List<ResultGameInfoScoreboardBean> mScoreboardData;
    public static final int ACTION_SHOT = 1;
    public static final int ACTION_ASSIST = 2;
    private List<ResultGameInfoOtherBean> mOtherData;
    private GameInfoOtherAdapter mOtherAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_info;
    }

    @Override
    protected void initView() {
        mItemMatch.setEnabled(false);
        mRvGrouping.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mRvData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvOtherData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void initData() {
        initTab();
        mGroupData = new ArrayList<>();
        mGroupAdapter = new GameInfoGroupAdapter(this, mGroupData);
        mRvGrouping.setAdapter(mGroupAdapter);

        mScoreboardData = new ArrayList<>();
        mScoreboardAdapter = new GameInfoScoreboardAdapter(mScoreboardData);
        mRvData.setAdapter(mScoreboardAdapter);
        mFiltrateData = new ArrayList<>();
        mPresenter.loadMatchFiltrateData();

        mOtherData = new ArrayList<>();
        mOtherAdapter = new GameInfoOtherAdapter(mOtherData);
        mRvOtherData.setAdapter(mOtherAdapter);
    }

    private void initTab() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.game_info_tab_array));
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45);
        }
    }

    @Override
    protected void initEvent() {
        mItemMatch.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {

                if (mFiltrateData.size() > 0) {
                    if (mDialogData == null) {
                        mDialogData = new ArrayList<>();
                    } else {
                        mDialogData.clear();
                    }
                    for (ResultGameFiltrateBean filtrateBean : mFiltrateData) {
                        mDialogData.add(filtrateBean.gameName);
                    }
                    SingWheelDialog filtrateDialog = new SingWheelDialog(GameInfoActivity.this, mDialogData);
                    filtrateDialog.setTitle(R.string.match_filtrate);

                    filtrateDialog.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onClickItem(@NonNull View view, int position) {
                            mRequestGameId = mFiltrateData.get(position).gameId;
                            mItemMatch.setContentText(mFiltrateData.get(position).gameName);

                            if (mTabContent.getSelectedTabPosition() == 0) {
                                mPresenter.loadMatchGroupData(mRequestGameId);
                            } else {
                                mPresenter.loadOtherData(mRequestGameId, mRequestAction);
                            }
                            filtrateDialog.dismiss();
                        }
                    });
                    filtrateDialog.setOnDialogShowOrDismissListener(new BaseDialog.OnDialogShowOrDismissListener() {
                        @Override
                        public void onShow(Dialog dialog) {
                            mItemMatch.mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_item_top, 0);
                        }

                        @Override
                        public void onDismiss(Dialog dialog) {
                            mItemMatch.mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_item_bottom, 0);
                        }
                    });
                    filtrateDialog.show();
                }
            }
        });
        mTabContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if (tabPosition == 0) {
                    mRvData.setVisibility(View.VISIBLE);
                    mRvOtherData.setVisibility(View.GONE);
                    mRvGrouping.setVisibility(View.VISIBLE);
                    mLlOther.setVisibility(View.GONE);
                    mLlScoreboard.setVisibility(View.VISIBLE);
                    if (mRequestGameId != -1) {
                        mPresenter.loadMatchGroupData(mRequestGameId);
                    }
                } else {

                    mRvData.setVisibility(View.GONE);
                    mRvOtherData.setVisibility(View.VISIBLE);
                    mRvGrouping.setVisibility(View.GONE);
                    mLlOther.setVisibility(View.VISIBLE);
                    mLlScoreboard.setVisibility(View.GONE);
                    if (tabPosition == 1) {
                        mRequestAction = ACTION_SHOT;
                        mTvGoals.setText(R.string.goals_scored);
                    } else if (tabPosition == 2) {
                        mRequestAction = ACTION_ASSIST;
                        mTvGoals.setText(R.string.assists_the_number);
                    }
                    if (mRequestGameId != -1) {
                        mPresenter.loadOtherData(mRequestGameId, mRequestAction);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mGroupAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mRequestGroupId = mGroupData.get(position).id;
                mPresenter.loadMatchScoreboardData(mRequestGameId, mRequestGroupId);
            }
        });
    }

    @Override
    protected GameInfoPresenter createPresenter() {
        return new GameInfoPresenter(this);
    }

    @Override
    public void resultMatchFiltrateData(List<ResultGameFiltrateBean> data) {
        if (mFiltrateData.size() > 0) {
            mFiltrateData.clear();
        }
        if (data != null && data.size() > 0) {
            mFiltrateData.addAll(data);
            ResultGameFiltrateBean bean = mFiltrateData.get(0);
            mItemMatch.setContentText(bean.gameName);
            mRequestGameId = bean.gameId;
            mPresenter.loadMatchGroupData(mRequestGameId);
            mItemMatch.setEnabled(true);
        }
    }

    @Override
    public void resultMatchGroupData(List<ResultGameGroupBean> data) {
        if (mGroupData.size() > 0) {
            mGroupData.clear();
        }
        if (data.size() > 0) {
            data.get(0).isCheck = true;
            mRequestGroupId = data.get(0).id;
            mGroupData.addAll(data);
            mRvGrouping.setVisibility(View.VISIBLE);
        } else {
            mRvGrouping.setVisibility(View.GONE);
            mRequestGroupId = null;
        }
        mGroupAdapter.notifyDataSetChanged();
        mPresenter.loadMatchScoreboardData(mRequestGameId, mRequestGroupId);
    }

    @Override
    public void resultMatchScoreboardDat(List<ResultGameInfoScoreboardBean> data) {
        if (mScoreboardData.size() > 0) {
            mScoreboardData.clear();
        }
        if (data.size() > 0) {
            mScoreboardData.addAll(data);
        }
        mScoreboardAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultMatchOtherData(List<ResultGameInfoOtherBean> data) {
        if (mOtherData.size() > 0) {
            mOtherData.clear();
        }
        if (data.size() > 0) {
            mOtherData.addAll(data);
        }
        mOtherAdapter.notifyDataSetChanged();
    }
}

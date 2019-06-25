package com.work.guaishouxingqiu.aboutball.game.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameInfoGroupAdapter;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameInfoScoreboardAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameFiltrateBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameGroupBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameInfoScoreboardBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameInfoContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameInfoPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.SingWheelDialog;

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
    private long mRequestGameId;
    private long mRequestGroupId;
    private List<String> mDialogData;
    private List<ResultGameFiltrateBean> mFiltrateData;
    private List<ResultGameGroupBean> mGroupData;
    private GameInfoGroupAdapter mGroupAdapter;
    private GameInfoScoreboardAdapter mScoreboardAdapter;
    private List<ResultGameInfoScoreboardBean> mScoreboardData;
    private boolean isOpenItemMatch;//是否已经打开赛事筛选

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_info;
    }

    @Override
    protected void initView() {
        mItemMatch.setEnabled(false);
        mRvGrouping.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvOtherData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        initTab();
        mScoreboardData = new ArrayList<>();
        mScoreboardAdapter = new GameInfoScoreboardAdapter(mScoreboardData);
        mRvData.setAdapter(mScoreboardAdapter);
        mFiltrateData = new ArrayList<>();
        mPresenter.loadMatchFiltrateData();
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
                mItemMatch.mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_item_top, 0);
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
                    filtrateDialog.show();
                    filtrateDialog.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onClickItem(@NonNull View view, int position) {
                            mRequestGameId = mFiltrateData.get(position).gameId;
                            mItemMatch.setContentText(mFiltrateData.get(position).gameName);
                            mPresenter.loadMatchGroupData(mRequestGameId);
                            mItemMatch.mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_item_bottom, 0);
                            filtrateDialog.dismiss();
                        }
                    });

                }
            }
        });
        mTabContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mRvData.setVisibility(View.VISIBLE);
                    mRvOtherData.setVisibility(View.GONE);
                } else {
                    mRvData.setVisibility(View.GONE);
                    mRvOtherData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        if (data != null && data.size() > 0) {
            mRvGrouping.setVisibility(View.VISIBLE);
            if (mGroupAdapter == null) {
                mGroupData = new ArrayList<>();
                mGroupData.addAll(data);
                mGroupAdapter = new GameInfoGroupAdapter(this, mGroupData);
                mRvGrouping.setAdapter(mGroupAdapter);
            } else {
                if (mGroupData.size() > 0) {
                    mGroupData.clear();
                }
                mGroupData.addAll(data);
                mGroupAdapter.notifyDataSetChanged();
            }
            if (mGroupData.size() > 0) {
                mRequestGroupId = mGroupData.get(0).id;
            }
        } else {
            mRequestGroupId = 0;
            mRvGrouping.setVisibility(View.GONE);
        }
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
}

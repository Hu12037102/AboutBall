package com.ifeell.app.aboutball.my.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.adapter.MyGameRecordAdapter;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultMyGameRecordBean;
import com.ifeell.app.aboutball.my.contract.TeamMatchResultContract;
import com.ifeell.app.aboutball.my.presenter.TeamMatchResultPresenter;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况fragment
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TEAM_MATCH_RESULT)
public class TeamMatchResultActivity extends BaseActivity<TeamMatchResultPresenter> implements
        TeamMatchResultContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_vs)
    TextView tvVs;
    @BindView(R.id.civ_left)
    CircleImageView mCivLeft;
    @BindView(R.id.tv_host)
    TextView mTvHost;
    @BindView(R.id.civ_right)
    CircleImageView mCivRight;
    @BindView(R.id.tv_guest)
    TextView mTvGuest;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    private ResultBallDetailsBean.MatchBean mIntentBean;
    private List<ResultMyGameRecordBean> mData;
    private MyGameRecordAdapter mResultAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_match_result;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_match);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mTvStart.setVisibility(View.GONE);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        UIUtils.setText(mTvHost, mIntentBean.hostTeamName);
        UIUtils.setText(mTvGuest, mIntentBean.guestTeamName);
        GlideManger.get().loadLogoImage(this, mIntentBean.hostTeamLogo, mCivLeft);
        GlideManger.get().loadLogoImage(this, mIntentBean.guestTeamLogo, mCivRight);

    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        //mResultAdapter = new GameDataAdapter(mData);
        mResultAdapter = new MyGameRecordAdapter(mData);
        mRvData.setAdapter(mResultAdapter);
        mSrlRefresh.autoRefresh();
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        //  mPresenter.loadGameResultDetails(mIntentBean.matchId);
        mPresenter.loadMyGameResultDetails(mIntentBean.agreeId);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        mResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onNotDataClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected TeamMatchResultPresenter createPresenter() {
        return new TeamMatchResultPresenter(this);
    }


    @Override
    public void resultMyGameResultDetails(List<ResultMyGameRecordBean> data) {
        mData.clear();

        if (data.size() > 0) {
            mData.addAll(data);
        }
        if (mData.size() > 0) {
            mTvStart.setVisibility(View.VISIBLE);
        }else {
            mTvStart.setVisibility(View.GONE);
        }

        mResultAdapter.notifyDataSetChanged();
    }
}

package com.ifeell.app.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.venue.adapter.SelectorBallTeamAdapter;
import com.ifeell.app.aboutball.venue.bean.ResultMyBallTeamBean;
import com.ifeell.app.aboutball.venue.contract.SelectorBallTeamContract;
import com.ifeell.app.aboutball.venue.presenter.SelectorBallTeamPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/5 14:31
 * 更新时间: 2019/5/5 14:31
 * 描述:选择球队Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM)
public class SelectorBallTeamActivity extends BaseActivity<SelectorBallTeamPresenter> implements SelectorBallTeamContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;


    private SelectorBallTeamAdapter mAdapter;
    private List<ResultMyBallTeamBean> mData;
    private ResultMyBallTeamBean mMyBallTeamBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selector_ball_team;
    }

    @Override
    protected void initView() {
        mMyBallTeamBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
       /* if (mMyBallTeamBean != null) {
            mRlBottom.setVisibility(View.VISIBLE);
        }*/
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new SelectorBallTeamAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                mPresenter.start();
            }

            @Override
            public void onNotDataClick(View view) {
                mPresenter.start();
            }

            @Override
            public void onItemClick(View view, int position) {
                if (mAdapter.getCheckTeam() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(ARouterConfig.Key.PARCELABLE, mAdapter.getCheckTeam());
                    setResult(RESULT_OK, intent);
                }
                finish();
                // mRlBottom.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected SelectorBallTeamPresenter createPresenter() {
        return new SelectorBallTeamPresenter(this);
    }

    @OnClick(R.id.iv_create)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_create:
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM, this);
                break;
            default:
                break;
        }
    }

    @Override
    public void resultMyBallTeam(List<ResultMyBallTeamBean> data) {
        mData.clear();
        Iterator<ResultMyBallTeamBean> iterator = data.iterator();
        while (iterator.hasNext()) {
            ResultMyBallTeamBean bean = iterator.next();
            if (bean.isLeader != 1) {
                iterator.remove();
            }
        }
        mData.addAll(data);
        for (ResultMyBallTeamBean bean : mData) {
            if (mMyBallTeamBean != null && bean.teamId == mMyBallTeamBean.teamId) {
                bean.isCheck = true;
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mData.size() == 0) {
            mViewModel.showCreateTeamDialog();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    mPresenter.start();
                    break;
            }
        }
    }
}

package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.MyBallTeamAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyBallTeamContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyBallTeamPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 10:11
 * 更新时间: 2019/4/24 10:11
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_BALL_TEAM)
public class MyBallTeamActivity extends BaseActivity<MyBallTeamPresenter> implements MyBallTeamContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    private MyBallTeamAdapter mAdapter;
    private List<ResultMyBallBean> mData;
    private static final int REQUEST_CODE = 123;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new MyBallTeamAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS, ARouterConfig.Key.PARCELABLE, mData.get(position));
            }
        });
    }


    @Override
    protected MyBallTeamPresenter createPresenter() {
        return new MyBallTeamPresenter(this);
    }


    @OnClick(R.id.iv_add)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                clickAddBallTeam();
                break;
        }
    }

    private void clickAddBallTeam() {
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM, this, REQUEST_CODE);
    }

    @Override
    public void resultMyBallTeam(List<ResultMyBallBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE) {
                    mPresenter.start();
                }
                break;
            default:
                break;
        }
    }
}

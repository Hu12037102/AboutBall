package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.RefereeListAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.LauncherBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.LauncherBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 14:22
 * 更新时间: 2019/4/23 14:22
 * 描述:发起约球页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_LAUNCHER_BALL)
public class LauncherBallActivity extends BaseActivity<LauncherBallPresenter> implements LauncherBallContract.View {

    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.rv_referee)
    RecyclerView mRvReferee;
    private RefereeListAdapter mRefereeAdapter;
    private List<ResultRefereeBean> mRefereeData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher_ball;
    }

    @Override
    protected void initView() {
        mRvReferee.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
            return;
        }
        long mStadiumId = bundle.getLong(ARouterConfig.Key.STADIUM_ID, -1);
        long mCalendarId = bundle.getLong(ARouterConfig.Key.CALENDAR_ID, -1);
        if (mStadiumId == -1 || mCalendarId == -1) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
        }


    }

    @Override
    protected void initData() {
        mRefereeData = new ArrayList<>();
        mRefereeAdapter = new RefereeListAdapter(mRefereeData);
        mRvReferee.setAdapter(mRefereeAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(LauncherBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        mItemColor.mTvRight.setText(color);
                    });
                }
                if (!mColorDialog.isShowing() && !isFinishing()) {
                    mColorDialog.show();
                }
            }
        });
    }

    @Override
    protected LauncherBallPresenter createPresenter() {
        return new LauncherBallPresenter(this);
    }


    @Override
    public void resultRefereeList(List<ResultRefereeBean> data) {
        mRefereeData.addAll(data);
        mRefereeAdapter.notifyDataSetChanged();
    }
}

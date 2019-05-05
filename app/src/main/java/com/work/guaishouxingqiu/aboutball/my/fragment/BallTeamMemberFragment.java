package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.BallTeamMemberAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMemberContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamMemberPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.ShareDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingPopupWindows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:23
 * 更新时间: 2019/4/26 9:23
 * 描述:球队成员fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_BALL_TEAM_MEMBER)
public class BallTeamMemberFragment extends BaseFragment<BallTeamMemberPresenter> implements BallTeamMemberContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private BallTeamMemberAdapter mAdapter;
    private List<ResultTeamDetailsMemberBean> mData;
    private ResultMyBallBean mBallBean;
    private SingPopupWindows mDeleteWindows;
    private long mPlayerId = -1;

    public void setOnPlayIdResult(BallTeamMemberFragment.onPlayIdResult onPlayIdResult) {
        this.onPlayIdResult = onPlayIdResult;
    }

    private onPlayIdResult onPlayIdResult;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ball_team_member;
    }

    @Override
    protected void initView() {
        mBallBean = mBundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new BallTeamMemberAdapter(mData);
        mAdapter.setOnItemLongClickListener(this::showDeletePhone);
        mRvData.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(this::loadRefresh);
    }


    private void showDeletePhone(View view, int position) {
        ResultTeamDetailsMemberBean bean = mData.get(position);
        if (bean == null || bean.isLeader == Contast.LEADER || !myIsLeader()) {
            return;
        }
        if (mDeleteWindows == null) {
            mDeleteWindows = new SingPopupWindows(mContext);
            mDeleteWindows.setContent(R.string.delete_team_member);
            mDeleteWindows.setContentDrawableRes(R.mipmap.icon_delete, 0, 0, 0);
        }
        mDeleteWindows.setPopupWindowsItemClickListener(v -> {
            mPresenter.deleteMember(mBallBean.teamId, bean.playerId, position);
            mDeleteWindows.dismiss();
        });
        if (mDeleteWindows != null && !mDeleteWindows.isShowing()) {
            mDeleteWindows.showAsDropDown(view, view.getMeasuredWidth() / 2 - mDeleteWindows.getWindow().getWidth() / 2, -view.getMeasuredHeight() / 2);
        }

    }


    private void loadRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.loadMemberDetails(mBallBean.teamId);
    }

    @Override
    protected BallTeamMemberPresenter createPresenter() {
        return new BallTeamMemberPresenter(this);
    }


    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_left:
                clickEditMyDetails();
                break;
            case R.id.tv_bottom_right:
                clickShareFriend();
                break;
        }
    }

    private void clickEditMyDetails() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_MY_DETAILS, ARouterConfig.Key.PLAYER_ID, mPlayerId);
    }

    private void clickShareFriend() {
        ShareDialog shareDialog = new ShareDialog(mContext);
        shareDialog.show();


    }

    @Override
    public void resultMemberDetails(List<ResultTeamDetailsMemberBean> data) {
        mData.clear();
        mData.addAll(data);
        resultMyPlayId(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultDeleteMember(int position) {
        mData.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, mData.size() - 1);
    }

    private void resultMyPlayId(List<ResultTeamDetailsMemberBean> data) {
        for (ResultTeamDetailsMemberBean bean : data) {
            if (bean != null && bean.isMe == 1) {
                mPlayerId = bean.playerId;
                if (onPlayIdResult != null) {
                    onPlayIdResult.resultMyPlayId(bean.playerId);
                }
            }
        }
    }

    public interface onPlayIdResult {
        void resultMyPlayId(long myPlayId);
    }

    private boolean myIsLeader() {
        if (mData != null && mData.size() > 0) {
            for (ResultTeamDetailsMemberBean bean : mData) {
                if (bean.isMe == 1 && bean.isLeader == Contast.LEADER) {
                    return true;
                }
            }
        }
        return false;
    }
}

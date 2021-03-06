package com.ifeell.app.aboutball.my.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.commonality.fragment.LoginOrShareFragment;
import com.ifeell.app.aboutball.my.adapter.BallTeamMemberAdapter;
import com.ifeell.app.aboutball.my.bean.ResultMyBallBean;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.ifeell.app.aboutball.my.contract.BallTeamMemberContract;
import com.ifeell.app.aboutball.my.presenter.BallTeamMemberPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.SpanUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.SingPopupWindows;

import java.util.ArrayList;
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
public class BallTeamMemberFragment extends LoginOrShareFragment<BallTeamMemberPresenter> implements BallTeamMemberContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.tv_bottom_left)
    TextView mTvBottomLeft;
    private BallTeamMemberAdapter mAdapter;
    private List<ResultTeamDetailsMemberBean> mData;
    private ResultMyBallBean mBallBean;
    private SingPopupWindows mDeleteWindows;
    private long mPlayerId = -1;
    private ResultTeamDetailsMemberBean mMyMemberBean;

    public void setOnPlayIdResult(BallTeamMemberFragment.onPlayIdResult onPlayIdResult) {
        this.onPlayIdResult = onPlayIdResult;
    }

    private onPlayIdResult onPlayIdResult;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ball_team_member;
    }

    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected void initView() {
        mTvBottomLeft.setEnabled(false);
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
            clickShowDeleteDialog(bean, position);
            mDeleteWindows.dismiss();
        });
        if (mDeleteWindows != null && !mDeleteWindows.isShowing()) {
            mDeleteWindows.showAsDropDown(view, view.getMeasuredWidth() / 2 - mDeleteWindows.getWindow().getWidth() / 2, -view.getMeasuredHeight() / 2);
        }

    }

    private void clickShowDeleteDialog(ResultTeamDetailsMemberBean bean, int position) {
        String body = UIUtils.getString(R.string.your_sure_delete_member_body, bean.nickName);
        String host = getString(R.string.your_sure_delete_member_host);
        HintDialog hintDialog = new HintDialog.Builder(mContext)
                .setTitle(R.string.hint)
                .setBody(SpanUtils.getTextColor(R.color.color_4, host.length(), host.length() + bean.nickName.length(), body))
                .setShowSingButton(false)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mPresenter.deleteMember(mBallBean.teamId, bean.playerId, position);
                hintDialog.dismiss();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                hintDialog.dismiss();
            }
        });
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
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_MY_DETAILS, ARouterConfig.Key.PARCELABLE, mMyMemberBean);
    }

    private void clickShareFriend() {
     /*   ShareDialog shareDialog = new ShareDialog(mContext);
        shareDialog.show();
        shareDialog.setWeichatClicklistener(v -> {
            shareWebToWeiChat(DataUtils.resultShareBallTeam(IApiService.H5.SHARE_BALL_TEAM + mBallBean.teamId));
            shareDialog.dismiss();
        });*/

        showShareDialog(DataUtils.resultShareBallTeam(mBallBean.teamId));

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
                mMyMemberBean = bean;
                if (onPlayIdResult != null) {
                    onPlayIdResult.resultMyPlayId(bean.playerId);
                }
            }
        }
        mTvBottomLeft.setEnabled(true);
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

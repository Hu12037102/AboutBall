package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameCommentAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.RequestGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameCommentPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.weight.InputMessageDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:54
 * 更新时间: 2019/3/25 9:54
 * 描述:比赛评论Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_COMMENT)
public class GameCommentFragment extends DelayedFragment<GameCommentPresenter> implements
        GameCommentContract.View {
    @BindView(R.id.iv_send_message)
    ImageView mIvShareMessage;
    @BindView(R.id.tv_input_message)
    TextView mTvInputMessage;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private InputMessageDialog mSendMessageDialog;
    private static final int WHAT = 100;
    private List<ResultGameCommentBean> mData;
    private GameCommentAdapter mAdapter;
    private ResultGameSimpleBean mBean;
    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case WHAT:
                mPresenter.isRefresh = true;
                mPresenter.loadCommentData(mBean.matchId);
                break;
        }
        return true;
    });

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_comment;
    }

    @Override
    protected void initDelayedView() {
        mIvShareMessage.setVisibility(View.GONE);
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new GameCommentAdapter(mData);
        View headView = LayoutInflater.from(mRvData.getContext()).inflate(R.layout.item_game_comment_head_view, mRvData, false);
        TextView mTvHint = headView.findViewById(R.id.tv_hint);
        String data = getString(R.string.game_send_comment_hint);
        mTvHint.setText(SpanUtils.getTextDrawable(R.mipmap.icon_systematic_notification,0,1,data));
        mAdapter.addHeadView(headView);
        mAdapter.setNotDataView(R.mipmap.icon_not_data_message);
        mAdapter.setNotDataContentRes(R.string.not_message);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(false, refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(true, refreshLayout);
            }
        });
    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        mHandler.removeMessages(WHAT, null);
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.loadCommentData(mBean.matchId);
    }


    @Override
    protected GameCommentPresenter createPresenter() {
        return new GameCommentPresenter(this);
    }


    @OnClick({R.id.iv_send_message, R.id.tv_input_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_send_message:
                break;
            case R.id.tv_input_message:
                clickInputMessage();
                break;
        }
    }

    private void clickInputMessage() {
        if (mSendMessageDialog == null) {
            mSendMessageDialog = new InputMessageDialog(getContext());
            mSendMessageDialog.setOnInputMessageListener(text -> {
                RequestGameCommentBean bean = new RequestGameCommentBean();
                bean.matchId = mBean.matchId;
                bean.commentContent = text;
                mPresenter.sendCommentMessage(bean);
            });
        }
        if (!mSendMessageDialog.isShowing()) {
            mSendMessageDialog.show();
        }
    }

    @Override
    public void resultCommentData(List<ResultGameCommentBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
        mHandler.sendEmptyMessageDelayed(WHAT, 5000);
    }

    @Override
    public void resultCommentMessage() {
        mSendMessageDialog.clearEditData();
        mHandler.removeMessages(WHAT, null);
        mPresenter.loadCommentData(mBean.matchId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeMessages(WHAT, null);
    }
}

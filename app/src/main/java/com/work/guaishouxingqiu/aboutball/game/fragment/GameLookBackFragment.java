package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameLookBackHeadAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameLiveDetailsBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameLookBackContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameLookBackPresenter;
import com.work.guaishouxingqiu.aboutball.home.adapter.RecommendedAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:06
 * 更新时间: 2019/6/24 11:06
 * 描述:比赛-回顾-fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_LOOK_BACK)
public class GameLookBackFragment extends BaseFragment<GameLookBackPresenter> implements GameLookBackContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private ResultGameSimpleBean mBean;
    private View mHeadView;
    private RecyclerView mRvHead;
    private RecommendedAdapter mAdapter;
    private GameLookBackHeadAdapter mHeadAdapter;
    private List<ResultGameLiveDetailsBean.MatchVideoBean> mHeadData;
    private List<ResultNewsBean> mData;
    private String mVideoPath;

    public void setOnClickLookBackListener(OnClickLookBackListener onClickLookBackListener) {
        this.onClickLookBackListener = onClickLookBackListener;
    }

    private OnClickLookBackListener onClickLookBackListener;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_look_back;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        initHeadView();
    }
    public void playCollectionVideo(){
        if (onClickLookBackListener!=null && mVideoPath!=null){
            onClickLookBackListener.playCollectionVideo(mVideoPath);
        }
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_game_head_look_back_view, mRvData, false);
        mRvHead = mHeadView.findViewById(R.id.rv_head);
        mRvHead.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mHeadData = new ArrayList<>();
        mHeadAdapter = new GameLookBackHeadAdapter(mContext, mHeadData);
        mRvHead.setAdapter(mHeadAdapter);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData,true);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initPermission() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        if (mBean == null) {
            UIUtils.showToast(R.string.not_find_this_match);
            getBaseActivity().finish();
            return;
        }
        super.initPermission();
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        mPresenter.loadLiveDetails(mBean.matchId);
        //  mPresenter.loadLiveDetails(131);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        mHeadAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                if (onClickLookBackListener != null) {
                    onClickLookBackListener.clickCollection(mHeadData.get(position).videoUrl);
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
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
                ResultNewsBean bean = mData.get(position);
                if (!bean.isRead) {
                    DataUtils.putNewsKey(bean.newsId);
                    mAdapter.notifyDataSetChanged();
                }
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, bean.newsId);
            }
        });
    }

    @Override
    protected GameLookBackPresenter createPresenter() {
        return new GameLookBackPresenter(this);
    }


    @Override
    public void resultLiveDetails(ResultGameLiveDetailsBean bean) {
        if (mHeadData.size() > 0) {
            mHeadData.clear();
        }
        if (bean.matchVideoList != null && bean.matchVideoList.size() > 0) {
            mHeadData.addAll(bean.matchVideoList);
        }
        if (mHeadData.size() > 0 && onClickLookBackListener != null) {
            onClickLookBackListener.resultCollectionCount(mHeadData.size());
            onClickLookBackListener.playCollectionVideo(mHeadData.get(0).videoUrl);
        }
        if (mData.size() > 0) {
            mData.clear();
        }
        if (bean.newsList != null && bean.newsList.size() > 0) {
            mData.addAll(bean.newsList);
        }
        mHeadAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();

    }

    public interface OnClickLookBackListener {
        void clickCollection(String videoUrl);

        void resultCollectionCount(int count);

        void playCollectionVideo(String videoPath);
    }
}

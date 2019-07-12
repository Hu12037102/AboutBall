package com.work.guaishouxingqiu.aboutball.game.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameCollectionAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCollectionContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameCollectionPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:22
 * 更新时间: 2019/3/25 10:22
 * 描述:比赛-集锦fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_COLLECTION)
public class GameCollectionFragment extends DelayedFragment<GameCollectionPresenter> implements
        GameCollectionContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private ResultGameSimpleBean mBean;
    private GameCollectionAdapter mAdapter;
    private List<ResultGameCollectionBean> mData;
    private GridLayoutManager gridLayoutManager;
    private String mVideoUrl;

    public void playCollectionVideo() {
        if (onCollectionClickListener != null) {
            onCollectionClickListener.resultVideo(mVideoUrl);
        }
    }

    public void setOnCollectionClickListener(OnCollectionClickListener onCollectionClickListener) {
        this.onCollectionClickListener = onCollectionClickListener;
    }

    private OnCollectionClickListener onCollectionClickListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_collection;
    }

    @Override
    protected void initDelayedView() {
      /*  mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mData = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(gridLayoutManager);*/
    }

    @Override
    protected void initPermission() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        if (mBean == null) {
            DataUtils.checkData(getActivity()).finish();
            UIUtils.showToast(R.string.game_id_error);
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        super.initView();
        gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new GameCollectionAdapter(mData);
        mAdapter.setHasStableIds(true);
        //    mRvData.setHasFixedSize(true);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadData(mBean.matchId);
            refreshLayout.finishRefresh();
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onItemClick(View view, int position) {
                if (onCollectionClickListener != null) {
                    onCollectionClickListener.clickCollection(mData.get(position).videoUrl);
                }
            }
        });
    }


    @Override
    protected void initDelayedData() {
        /*mAdapter = new GameCollectionAdapter(mData);
        mAdapter.setHasStableIds(true);
        //    mRvData.setHasFixedSize(true);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();*/
    }

    @Override
    protected void initDelayedEvent() {
    /*    mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadData(mBean.matchId);
            refreshLayout.finishRefresh();
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                if (onCollectionClickListener != null) {
                    onCollectionClickListener.clickCollection(mData.get(position).videoUrl);
                }
            }
        });*/
    }


    @Override
    protected GameCollectionPresenter createPresenter() {
        return new GameCollectionPresenter(this);
    }

    @Override
    public void resultData(List<ResultGameCollectionBean> data) {
        if (onCollectionClickListener != null) {
            if (data.size() > 0) {
                mVideoUrl = data.get(0).videoUrl;
                onCollectionClickListener.resultVideosCount(data.size());
            } else {
                onCollectionClickListener.resultVideosCount(0);
            }
        }
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSrlRefresh.setNoMoreData(true);
    }

    public interface OnCollectionClickListener {
        //点击集锦列表item
        void clickCollection(String videoUrl);

        //点击观看集锦
        void resultVideo(String videoUrl);

        void resultVideosCount(int count);

    }
}

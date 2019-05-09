package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.RefereeRecordAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyRefereeRecordChildContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyRefereeRecordChildPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:06
 * 更新时间: 2019/5/9 10:06
 * 描述:我的裁判记录fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_REFEREE_RECORD)
public class MyRefereeRecordFragment extends BaseFragment<MyRefereeRecordChildPresenter> implements MyRefereeRecordChildContract.View {
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    private List<ResultRefereeRecordBean> mData;
    private RefereeRecordAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_referee_record;
    }

    @Override
    protected void initView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new RefereeRecordAdapter(mData);
        mRvContent.setAdapter(mAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MyRefereeRecordChildPresenter createPresenter() {
        return new MyRefereeRecordChildPresenter(this);
    }


    @Override
    public void resultMyRefereeRecord(List<ResultRefereeRecordBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.addFootView(UIUtils.loadNotMoreView(mRvContent));
        mAdapter.notifyItemChanged(0, mData.size());
    }
}

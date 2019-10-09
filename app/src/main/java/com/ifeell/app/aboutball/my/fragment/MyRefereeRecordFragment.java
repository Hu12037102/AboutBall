package com.ifeell.app.aboutball.my.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.my.adapter.RefereeRecordAdapter;
import com.ifeell.app.aboutball.my.bean.ResultRefereeRecordBean;
import com.ifeell.app.aboutball.my.contract.MyRefereeRecordChildContract;
import com.ifeell.app.aboutball.my.presenter.MyRefereeRecordChildPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private long mRefereeId;

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
        mRefereeId = mBundle.getLong(ARouterConfig.Key.REFEREE_ID, -1);
        mData = new ArrayList<>();
        mAdapter = new RefereeRecordAdapter(mData, mRefereeId == -1);
        mRvContent.setAdapter(mAdapter);
        //如果裁判id==-1说明是用户本身
        if (mRefereeId == -1) {
            mPresenter.start();
        } else {
            mPresenter.loadRefereeRecord(mRefereeId);
        }
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
        if (data.size() > 0) {
            mData.addAll(data);
            mAdapter.addFootView(UIUtils.loadNotMoreView(mRvContent));
        }
        mAdapter.notifyDataSetChanged();
    }
}

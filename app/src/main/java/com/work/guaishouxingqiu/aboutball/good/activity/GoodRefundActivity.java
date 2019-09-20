package com.work.guaishouxingqiu.aboutball.good.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.good.adapter.GoodRefundCauseAdapter;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultGoodRefundDetailsBean;
import com.work.guaishouxingqiu.aboutball.good.contract.GoodRefundContract;
import com.work.guaishouxingqiu.aboutball.good.presenter.GoodRefundPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/20 10:55
 * 更新时间: 2019/9/20 10:55
 * 描述:申请退款Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GOOD_REFUND)
public class GoodRefundActivity extends BaseActivity<GoodRefundPresenter> implements GoodRefundContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_good_number)
    TextView mTvGoodNumber;
    @BindView(R.id.riv_content)
    RoundedImageView mRivContent;
    @BindView(R.id.cl_content)
    ConstraintLayout mClContent;
    @BindView(R.id.rv_cause)
    RecyclerView mRvCause;
    @BindView(R.id.acet_cause)
    AppCompatEditText mAcetCause;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.ll_edit)
    LinearLayout mLlEdit;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private long mOrderId;
    private List<String> mCauseData;
    private GoodRefundCauseAdapter mCauseAdapter;
    String mRequestReason;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_refund;
    }

    @Override
    public void initPermission() {
        mOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (mOrderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mRvCause.setLayoutManager(new LinearLayoutManager(this));
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                mViewModel.clickBackForResult();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mViewModel.clickBackForResult();
    }

    @Override
    protected SmartRefreshLayout getRefreshLayout() {
        return mSrlRefresh;
    }

    @Override
    protected void loadRefreshData(boolean isRefresh) {
        super.loadRefreshData(isRefresh);
        mPresenter.loadRefundDetail(mOrderId);
    }

    @OnClick(R.id.tv_refund)
    public void onClickView(View view) {
        if (mCauseAdapter != null) {
            if (mCauseAdapter.getCheckPosition() == -1) {
                UIUtils.showToast(R.string.please_selector_refund_causes);
                return;
            }
            if (mCauseAdapter.getCheckPosition() == mCauseAdapter.getItemCount() - 1
                    && DataUtils.isEmpty(DataUtils.getEditDetails(mAcetCause))) {
                UIUtils.showToast(R.string.please_input_refund_cause);
                return;
            } else {
                mRequestReason = DataUtils.getTextViewContent(mAcetCause);
            }
            if (!DataUtils.isEmpty(mRequestReason)) {
                mPresenter.applyRefund(mOrderId, mRequestReason);
            }
        }
    }

    @Override
    protected GoodRefundPresenter createPresenter() {
        return new GoodRefundPresenter(this);
    }

    @Override
    public void resultRefundDetails(ResultGoodRefundDetailsBean bean) {
        List<String> reasonData = Arrays.asList(bean.reason);
        if (reasonData.size() > 0) {
            mLlEdit.setVisibility(View.GONE);
            if (mCauseData == null) {
                mCauseData = new ArrayList<>();
                mCauseData.addAll(reasonData);
                mCauseData.add(reasonData.size(), UIUtils.getString(R.string.other));
                mCauseAdapter = new GoodRefundCauseAdapter(this, mCauseData);
                mRvCause.setAdapter(mCauseAdapter);
            } else {
                mCauseData.clear();
                mCauseData.addAll(reasonData);
                mCauseData.add(reasonData.size(), UIUtils.getString(R.string.other));
                mCauseAdapter.notifyDataSetChanged();
            }
            mCauseAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClickItem(@NonNull View view, int position) {
                    if (position == mCauseData.size() - 1) {
                        mLlEdit.setVisibility(View.VISIBLE);
                    } else {
                        mLlEdit.setVisibility(View.GONE);
                        mRequestReason = mCauseData.get(position);
                    }
                }
            });
        } else {
            mLlEdit.setVisibility(View.VISIBLE);
        }
        String orderNoContent = UIUtils.getString(R.string.order_no, bean.orderNo);
        UIUtils.setText(mTvGoodNumber, SpanUtils.getTextColor(R.color.color_4, orderNoContent.length() - bean.orderNo.length(), orderNoContent.length(), orderNoContent));
        GlideManger.get().loadBannerImage(this, bean.spuImage, mRivContent);
        UIUtils.setText(mTvContent, bean.spuTitle);
    }

    @Override
    public void resultRefundSucceed() {
        clickBackForResult();
    }

}

package com.ifeell.app.aboutball.good.activity;

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
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.good.adapter.GoodRefundCauseAdapter;
import com.ifeell.app.aboutball.good.bean.ResultGoodRefundDetailsBean;
import com.ifeell.app.aboutball.good.contract.GoodRefundContract;
import com.ifeell.app.aboutball.good.presenter.GoodRefundPresenter;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.SpanUtils;
import com.ifeell.app.aboutball.util.UIUtils;

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
        if (mCauseAdapter != null && mCauseAdapter.getItemCount() > 0) {
            if (mCauseAdapter.getCheckPosition() == -1) {
                UIUtils.showToast(R.string.please_selector_refund_causes);
                return;
            }
            if (mCauseAdapter.getCheckPosition() == mCauseAdapter.getItemCount() - 1) {
                if (DataUtils.isEmpty(DataUtils.getEditDetails(mAcetCause))) {
                    UIUtils.showToast(R.string.please_input_refund_cause);
                    return;
                } else {
                    mRequestReason = DataUtils.getTextViewContent(mAcetCause);
                }
            } else {
                mRequestReason = mCauseData.get(mCauseAdapter.getCheckPosition());
            }
        } else {
            if (DataUtils.isEmpty(DataUtils.getEditDetails(mAcetCause))) {
                UIUtils.showToast(R.string.please_input_refund_cause);
                return;
            } else {
                mRequestReason = DataUtils.getTextViewContent(mAcetCause);
            }
        }
        if (!DataUtils.isEmpty(mRequestReason)) {
            mPresenter.applyRefund(mOrderId, mRequestReason);
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

package com.work.guaishouxingqiu.aboutball.home.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.ItemView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseWebActivity;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.base.bean.ResultSureOrderDialogBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultDoorTicketDetailsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultGameTicketDetailsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultTicketMallBean;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.TicketMallDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseWebView;
import com.work.guaishouxingqiu.aboutball.weight.SureOrderDialog;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:08
 * 更新时间: 2019/9/3 11:08
 * 描述:购票详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TICKET_MALL_DETAILS)
public class TicketMallDetailsActivity extends BaseWebActivity<TicketMallDetailsPresenter> implements TicketMallDetailsContract.View {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.iv_tickets_obscuration)
    ImageView mIvTicketsObscuration;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.civ_host_logo)
    CircleImageView mCivHostLogo;
    @BindView(R.id.tv_host_name)
    TextView mTvHostName;
    @BindView(R.id.civ_guest_logo)
    CircleImageView mCivGuestLogo;
    @BindView(R.id.tv_guest_name)
    TextView mTvGuestName;
    @BindView(R.id.cl_game)
    ConstraintLayout mClGame;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_residue)
    TextView mTvResidue;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.vs_swim_content)
    View mVsSwimContent;
    @BindView(R.id.tv_information)
    TextView mTvInformation;
    private ResultTicketMallBean mIntentBean;
    @BindView(R.id.tv_cost)
    TextView mTvConstMoney;
    @BindView(R.id.include_bottom)
    View mIncludeBottom;
    @BindView(R.id.iv_content)
    ImageView mIvContent;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.item_standard)
    ItemView mItemStandard;
    @BindView(R.id.bwv_content)
    BaseWebView mBwvContent;
    private RequestSureOrderBean mRequestDialogBean;
    private SureOrderDialog mSureOrderDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_mall_details;
    }

    @Override
    protected void initStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        LogUtils.w("initStatusColor--", ScreenUtils.getStatuWindowsHeight(this) + "--");
        mIvBack.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.getStatuWindowsHeight(this) / 2 + ScreenUtils.dp2px(this, 20),
                ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20));
      /*  mIvTicketsObscuration.setPadding(0, ScreenUtils.getStatuWindowsHeight(this) + ScreenUtils.dp2px(this, 20),
                0, 0);*/
        ViewGroup.LayoutParams layoutParams = mIvTicketsObscuration.getLayoutParams();
        mIvTicketsObscuration.post(() -> {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = mIvTicketsObscuration.getMaxHeight() + ScreenUtils.getStatuWindowsHeight(TicketMallDetailsActivity.this);
            mIvTicketsObscuration.setLayoutParams(layoutParams);
        });

    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_tick_mall_message);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mTvCommit.setText(R.string.now_buy);
        mTvConstMoney.getPaint().setAntiAlias(true);
        mTvConstMoney.getPaint().setDither(true);
        mTvConstMoney.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        super.initView();
        mSrlRefresh.autoRefresh();
        // mInflateDoorView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void initData() {
        mRequestDialogBean = new RequestSureOrderBean(mIntentBean.spuId);
    }

    @Override
    protected void initEvent() {
        mIvBack.setOnClickListener(v -> mViewModel.clickBackForResult());
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        mItemStandard.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                showBuyTicketDialog();
            }
        });
        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBuyTicketDialog();
            }
        });
    }

    private void showBuyTicketDialog() {
        mPresenter.getSureOrderDialog(mRequestDialogBean);
    }

    private void loadData(boolean isRefresh) {
        if (isRefresh) {
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        mPresenter.isRefresh = isRefresh;
        //"内容类型: 1.比赛门票，2.泳票",
        if (mIntentBean.contentType == 1) {
            mPresenter.loadGameTicketDetails(mIntentBean.spuId);
        } else if (mIntentBean.contentType == 2) {
            mPresenter.loadDoorTicketDetails(mIntentBean.spuId);
        }
    }

    @Override
    public void onBackPressed() {
        mViewModel.clickBackForResult();
    }

    @Override
    protected WebView getWebView() {
        return mBwvContent;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
    }

    @Override
    protected TicketMallDetailsPresenter createPresenter() {
        return new TicketMallDetailsPresenter(this);
    }

    @Override
    public void resultSureOrderDialog(BaseBean<ResultSureOrderDialogBean> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mSureOrderDialog == null) {
                mSureOrderDialog = new SureOrderDialog(this, bean.result);
            }else {
                mSureOrderDialog.notifyData(bean.result);
            }
            if (!mSureOrderDialog.isShowing()) {
                mSureOrderDialog.show();
            }
            mSureOrderDialog.setOnSureOrderClickListener(new SureOrderDialog.OnSureOrderClickListener() {
                @Override
                public void onClickSureBuy(View view, String allValues, int num) {

                }

                @Override
                public void onClickItemNotify(View view, int position, String values, int num) {
                    mRequestDialogBean.params = values;
                    mRequestDialogBean.num = num;
                    mPresenter.getSureOrderDialog(mRequestDialogBean);
                }
            });
        }
    }

    /**
     * 比赛详情
     *
     * @param bean
     */
    @Override
    public void resultGameTicketDetails(@NonNull ResultGameTicketDetailsBean bean) {
        mIncludeBottom.setVisibility(View.VISIBLE);
        mClGame.setVisibility(View.VISIBLE);
        mTvCount.setVisibility(View.VISIBLE);
        UIUtils.setText(mTvMoney, DataUtils.getMoneyFormat(bean.price));
        UIUtils.setText(mTvConstMoney, DataUtils.getMoneyFormat(bean.costPrice));
        UIUtils.setText(mTvName, bean.title);
        UIUtils.setText(mTvAddress, bean.subTitle);
        UIUtils.setText(mTvInformation, bean.ticketNotice);
        GlideManger.get().loadImage(this, mIntentBean.image, mIvContent);
        UIUtils.setText(mTvTime, bean.startTime);
        UIUtils.setText(mTvHostName, bean.hostTeamName);
        UIUtils.setText(mTvGuestName, bean.guestTeamName);
        GlideManger.get().loadLogoImage(this, bean.hostLogoUrl, mCivHostLogo);
        GlideManger.get().loadLogoImage(this, bean.guestLogoUrl, mCivGuestLogo);
        UIUtils.setText(mTvResidue, UIUtils.getString(R.string.current_vote_surplus, bean.num));
    }

    /**
     * 游泳详情
     *
     * @param bean
     */
    @Override
    public void resultDoorTicketDetails(@NonNull ResultDoorTicketDetailsBean bean) {
        mVsSwimContent.setVisibility(View.VISIBLE);
        mIncludeBottom.setVisibility(View.VISIBLE);
        mTvCount.setVisibility(View.VISIBLE);
        mClGame.setVisibility(View.GONE);
        mTvResidue.setVisibility(View.GONE);
        GlideManger.get().loadImage(this, mIntentBean.image, mIvContent);
        UIUtils.setText(mTvMoney, bean.price);
        UIUtils.setText(mTvConstMoney, bean.costPrice);
        UIUtils.setText(mTvName, bean.title);
        UIUtils.setText(mTvAddress, bean.subTitle);
        loadEditData(bean.introduction);
        UIUtils.setText(mTvInformation, bean.ticketNotice);
        UIUtils.setText(mItemStandard.mTvRight, bean.specifications);
    }


}

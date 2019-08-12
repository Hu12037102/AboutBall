package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.PaySucceedActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.NotBookAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.RefereeListAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestCreateBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestLauncherBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultNotBookBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.LauncherBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.LauncherBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.SelectorColorDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/23 14:22
 * 更新时间: 2019/4/23 14:22
 * 描述:发起约球页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_LAUNCHER_BALL)
public class LauncherBallActivity extends BaseActivity<LauncherBallPresenter> implements LauncherBallContract.View {

    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.rv_referee)
    RecyclerView mRvReferee;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.rv_not_book)
    RecyclerView mRvNotBook;
    @BindView(R.id.ll_new_book)
    LinearLayout mLlNewBook;
    private RefereeListAdapter mRefereeAdapter;
    private List<ResultRefereeBean> mRefereeData;
    private ResultMyBallTeamBean mMyBallTeam;
    private long mCalendarId;
    private long mStadiumId;
    private long mAreaId;
    private static final int REQUEST_CODE_REFEREE_DETAILS = 45;
    private static final int REQUEST_CODE_ORDER_PAY = 46;
    private List<ResultNotBookBean> mNotBookData;
    private NotBookAdapter mNotBookAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher_ball;
    }

    @Override
    protected void initView() {
        mRvReferee.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
            return;
        }
        mStadiumId = bundle.getLong(ARouterConfig.Key.STADIUM_ID, -1);
        mCalendarId = bundle.getLong(ARouterConfig.Key.CALENDAR_ID, -1);
        mAreaId = bundle.getLong(ARouterConfig.Key.AREA_ID, -1);

        mNotBookData = bundle.getParcelableArrayList(ARouterConfig.Key.ARRAY_LIST_PARCELABLE);
        if (DataUtils.isEmptyList(mNotBookData)) {
            mLlNewBook.setVisibility(View.VISIBLE);
            mRvNotBook.setVisibility(View.GONE);
        } else {
            mLlNewBook.setVisibility(View.GONE);
            mRvNotBook.setVisibility(View.VISIBLE);
            mRvNotBook.setLayoutManager(new LinearLayoutManager(this));
            mNotBookAdapter = new NotBookAdapter(mNotBookData);
            mNotBookAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onNotNetClick(View view) {

                }

                @Override
                public void onNotDataClick(View view) {

                }

                @Override
                public void onItemClick(View view, int position) {
                    setNextStatus();
                }
            });
            mRvNotBook.setAdapter(mNotBookAdapter);
        }
        if (mStadiumId == -1 || mCalendarId == -1 || mAreaId == -1) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
        }
        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);

    }

    @Override
    protected void initData() {
        mRefereeData = new ArrayList<>();
        mRefereeAdapter = new RefereeListAdapter(mRefereeData);
        mRvReferee.setAdapter(mRefereeAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(LauncherBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        mItemColor.mTvRight.setText(color);
                        setNextStatus();
                    });
                }
                if (!mColorDialog.isShowing() && !isFinishing()) {
                    mColorDialog.show();
                }
            }
        });
        mItemTeam.setOnItemClickListener(view -> ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM,
                LauncherBallActivity.this, ARouterConfig.Key.PARCELABLE, mMyBallTeam));
        mRefereeAdapter.setOnCheckContentListener((view, position) -> setNextStatus());
        mRefereeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                // ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_REFEREE_DETAILS, LauncherBallActivity.this, ARouterConfig.Key.REFEREE_ID, mRefereeData.get(position).refereeId);
                mViewModel.startActivityToRefereeDetailsForResult(mRefereeData.get(position), mRefereeAdapter.getInviteCount(), REQUEST_CODE_REFEREE_DETAILS);
            }
        });
        mTvNext.setOnClickListener(v -> {
            if (DataUtils.isEmptyList(mNotBookData)) {
                RequestLauncherBallBean requestBean = new RequestLauncherBallBean();
                requestBean.calendarId = mCalendarId;
                requestBean.hostTeamId = mMyBallTeam.teamId;
                requestBean.refereeId = mRefereeAdapter.getSelectorInviteReferee();
                requestBean.hostShirtColor = mItemColor.mTvRight.getText().toString();
                mPresenter.launcherBall(requestBean);
            } else {
                ResultNotBookBean bean = mNotBookData.get(mNotBookAdapter.getSelectorPosition());
                RequestCreateBallBean editRequestBean = new RequestCreateBallBean();
                editRequestBean.agreeId = bean.agreeId;
                editRequestBean.calendarId = mCalendarId;
                editRequestBean.startTime = bean.startTime;
                editRequestBean.endTime = bean.endTime;
                editRequestBean.hostShirtColor = bean.hostShirtColor;
                editRequestBean.refereeId = new Long[]{};
                editRequestBean.hostTeamId = bean.hostTeamId;
                editRequestBean.phone = bean.phone;
                mPresenter.editAboutBall(editRequestBean);
            }

        });
    }

    @Override
    public void resultCreateBallOrderId(String orderId) {
        if (orderId != null) {
            mViewModel.startActivityToOrderPay(Long.valueOf(orderId), Contast.PayOrderFlag.PAY_LAUNCHER_ORDER);
        }
        mViewModel.clickBackForResult();

    }

    @Override
    protected LauncherBallPresenter createPresenter() {
        return new LauncherBallPresenter(this);
    }


    @Override
    public void resultRefereeList(List<ResultRefereeBean> data) {
        mRefereeData.addAll(data);
        mRefereeAdapter.notifyDataSetChanged();
    }

    @Override
    public void launcherBallSucceed(long orderId) {
        //发起约球成功后，生成订单
       /* RequestVenueOrderBean bean = new RequestVenueOrderBean();
        bean.areaId = mAreaId;
        bean.calendarId = new Long[]{mCalendarId};
        bean.flag = 1;
        bean.stadiumId = mStadiumId;
        mPresenter.createOrder(bean);*/
        mViewModel.startActivityToOrderPay(orderId, Contast.PayOrderFlag.PAY_LAUNCHER_ORDER, LauncherBallActivity.REQUEST_CODE_ORDER_PAY);
    }

    @Override
    public void resultOrderId(long orderId) {
        //ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, ARouterConfig.Key.ORDER_ID, orderId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      /*  if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
            mItemTeam.setContentText(mMyBallTeam.teamName);
            setNextStatus();
        }*/
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //选择球队
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    mItemTeam.setContentText(mMyBallTeam.teamName);
                    setNextStatus();
                    break;
                //裁判详情
                case LauncherBallActivity.REQUEST_CODE_REFEREE_DETAILS:
                    if (data == null) {
                        return;
                    }
                    ResultRefereeBean refereeBean = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    if (mRefereeData.size() > 0) {
                        for (int i = 0; i < mRefereeData.size(); i++) {
                            if (mRefereeData.get(i).refereeId == refereeBean.refereeId) {
                                mRefereeData.get(i).isInvite = refereeBean.isInvite;
                            }
                        }
                        mRefereeAdapter.notifyDataSetChanged();
                        setNextStatus();
                    }
                    break;
                //订单返回
                case LauncherBallActivity.REQUEST_CODE_ORDER_PAY:
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    private boolean isSelectorTeam() {
        return !(mMyBallTeam == null);
    }

    private boolean isSelectorColor() {
        return !DataUtils.isEmpty(mItemColor.mTvRight.getText());
    }

    public boolean isSelectorReferee() {
        /*  return mRefereeAdapter != null && mRefereeAdapter.isInviteReferee();*/
        return true;
    }

    private void setNextStatus() {
        if (DataUtils.isEmptyList(mNotBookData)) {
            if (isSelectorTeam() && isSelectorColor() && isSelectorReferee()) {
                mTvNext.setEnabled(true);
                mTvNext.setBackgroundResource(R.drawable.shape_click_button);
            } else {
                mTvNext.setEnabled(false);
                mTvNext.setBackgroundResource(R.drawable.shape_default_button);
            }
        } else {
            if (mNotBookAdapter.getSelectorPosition() != -1) {
                mTvNext.setEnabled(true);
                mTvNext.setBackgroundResource(R.drawable.shape_click_button);
            } else {
                mTvNext.setEnabled(false);
                mTvNext.setBackgroundResource(R.drawable.shape_default_button);
            }
        }
    }
}

package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ShareWebBean;
import com.work.guaishouxingqiu.aboutball.commonality.fragment.LoginOrShareFragment;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.login.activity.LoginActivity;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.my.activity.AttentionAndFansActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.MyMessageActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.bugtags.ui.view.rounded.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:56
 * 更新时间: 2019/3/6 15:56
 * 描述:我的Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY)
public class MyFragment extends LoginOrShareFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.civ_my_head)
    CircleImageView mCivMyHead;
    @BindView(R.id.ll_head_group)
    LinearLayout mLlHeadGroup;
    @BindView(R.id.item_share_friend)
    ItemView mItemFriend;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_my_prize)
    ItemView mItemPrize;
    @BindView(R.id.item_about_ball)
    ItemView mItemBall;
    @BindView(R.id.item_order)
    ItemView mItemOrder;
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_message)
    ItemView mItemMessage;
    @BindView(R.id.item_dynamic)
    ItemView mItemDynamic;
    @BindView(R.id.item_as_referee)
    ItemView mItemAsReferee;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.iv_red_point)
    ImageView mIvRedPoint;
    private View view;
    private Integer mMyRefereeStatus;
    private TextView mHeadTvFocusFans;
    private static final int REQUEST_CODE_ATTENTION_AND_FANS = 93;
    private static final int REQUEST_CODE_USER_DYNAMIC = 94;//用户动态
    private static final int REQUEST_CODE_MY_MESSAGE = 97;//我的消息

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {
        mItemFriend.setOnItemClickListener(view -> {
            //Toasts.with().showToast(R.string.pleases_next_open);
            //ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_SHARE_FRIEND);
            clickShareFriend();
        });
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
            }

            @Override
            public void onSureClick(@NonNull View view) {
                clickSetting();
            }
        });
        mItemPrize.setOnItemClickListener(view -> {
            if (UserManger.get().isLogin()) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_PRIZE);
            } else {
                mViewModel.showLoginDialog();
            }
        });
        mItemBall.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_ABOUT_BALL);
            }
        });
        mItemOrder.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                //Toasts.with().showToast(R.string.pleases_next_open);
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_ORDER);
            }
        });
        mItemTeam.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_BALL_TEAM);
                //  Toasts.with().showToast(R.string.pleases_next_open);
            }
        });
        mItemMessage.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                Toasts.with().showToast(R.string.pleases_next_open);
            }
        });
        mItemDynamic.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                if (UserManger.get().isLogin()) {
                    // ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_DYNAMIC);
                    mViewModel.startActivityToUserDynamicForResult(MyFragment.this, UserManger.get().getUserId(), MyFragment.REQUEST_CODE_USER_DYNAMIC);
                } else {
                    mViewModel.showLoginDialog();
                }
                // Toasts.with().showToast(R.string.pleases_next_open);
            }
        });
        mItemAsReferee.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                if (UserManger.get().isLogin()) {
                    if (mMyRefereeStatus == null) {
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_APPLY_REFEREE);
                    } else if (mMyRefereeStatus == 1) {
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_REFEREE_RECORD);
                    } else {
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REFEREE_STATUS, ARouterConfig.Key.REFEREE_STATUS, mMyRefereeStatus.intValue());
                    }
                } else {
                    mViewModel.showLoginDialog(MyFragment.this);
                }
            }
        });
    }

    private void clickShareFriend() {
        ShareWebBean bean = new ShareWebBean();
        bean.webUrl = IApiService.H5.DOWNLOAD_APK;
        bean.title = UIUtils.getString(R.string.download_about_ball);
        bean.description = UIUtils.getString(R.string.you_friend_download_about_ball);
        showShareDialog(bean);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initLoginView();


    }

    private void setRedPointStatus() {
        if (mViewModel.isShowRedPoint()) {
            mIvRedPoint.setVisibility(View.VISIBLE);
        } else {
            mIvRedPoint.setVisibility(View.GONE);
        }
    }

    private void initLoginView() {
        if (mLlHeadGroup.getChildCount() > 0) {
            mLlHeadGroup.removeAllViews();
        }
        View view;
        UserBean userBean = UserManger.get().getUser();
        if (UserManger.get().isLogin()) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_login_my_head_view, null);
            TextView mTvName = view.findViewById(R.id.tv_name);
            mHeadTvFocusFans = view.findViewById(R.id.tv_focus_fans);
            mTvName.setText(DataUtils.isEmpty(userBean.nickName) ? userBean.phone : userBean.nickName);
            // mHeadTvFocusFans.setText(getString(R.string.focus_and_fans, UserManger.get().getFollowCount() + "", UserManger.get().getFansCount() + ""));
            UIUtils.setText(mHeadTvFocusFans, userBean.refereeLevel);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_no_login_my_head_view, null);
            mItemAsReferee.setVisibility(View.VISIBLE);
            mItemAsReferee.setTitleText(R.string.my_as_the_referee);
        }
        GlideManger.get().loadHeadImage(mContext, userBean.headerImg, mCivMyHead);
        mLlHeadGroup.addView(view);

        if (UserManger.get().getRefereeStatus() == 1) {
            mItemAsReferee.setTitleText(R.string.my_referee_record);
        } else {
            mItemAsReferee.setTitleText(R.string.my_as_the_referee);
        }
        mMyRefereeStatus = UserManger.get().getRefereeStatus() == -1 ? null : UserManger.get().getRefereeStatus();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mContext != null && UserManger.get().isLogin()) {
            mPresenter.judgeRefereeStatus();
            mPresenter.loadFansAndFocus();
            setRedPointStatus();
        }
    }

    @Override
    protected void initEvent() {
    }

    @Override
    public void resultFansFocus(ResultFansFocusBean bean) {
       /* if (mHeadTvFocusFans != null) {
            mHeadTvFocusFans.setText(getString(R.string.focus_and_fans, bean.followCount + "", bean.fansCount + ""));
        }*/
    }

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this);
    }


    @OnClick({R.id.item_about_ball, R.id.item_order, R.id.item_team, R.id.item_message,
            R.id.item_dynamic, R.id.item_as_referee, R.id.rl_my_head, R.id.tv_attention, R.id.tv_fans, R.id.tv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_about_ball:
                break;
            case R.id.item_order:
                break;
            case R.id.item_team:
                break;
            case R.id.item_message:
                break;
            case R.id.item_dynamic:
                break;
            case R.id.item_as_referee:
                break;

            case R.id.rl_my_head:
                clickHead();
                break;
            case R.id.tv_attention:
                mViewModel.startActivityToAttentionAndFans(this, AttentionAndFansActivity.ATTENTION_ID, MyFragment.REQUEST_CODE_ATTENTION_AND_FANS);
                break;
            case R.id.tv_fans:
                mViewModel.startActivityToAttentionAndFans(this, AttentionAndFansActivity.FANS_ID, MyFragment.REQUEST_CODE_ATTENTION_AND_FANS);
                break;
            case R.id.tv_message:
                ARouterIntent.startActivityForResult(this, MyMessageActivity.class, MyFragment.REQUEST_CODE_MY_MESSAGE);
                break;
            default:
                break;
        }
    }

    private void clickSetting() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_SETTING);
    }

    private void clickHead() {
        if (!UserManger.get().isLogin()) {
            $startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);

        } else {
            $startActivity(ARouterConfig.Path.ACTIVITY_MY_DETAILS);
        }
    }

    /**
     * 审核裁判状态
     *
     * @param status 0：审核中;1：已通过（用户确认);2：未通过;3：已通过（后台审核）
     */
    @Override
    public void resultRefereeStatus(Integer status) {
        mMyRefereeStatus = status;
        mItemAsReferee.setVisibility(View.VISIBLE);
        if (status != null) {
            if (status == Contast.RefereeStatus.REFEREE_1) {
                mItemAsReferee.setTitleText(R.string.my_referee_record);
            }
            UserManger.get().putRefereeStatus(status);
        } else {
            UserManger.get().putRefereeStatus(-1);
            mItemAsReferee.setTitleText(R.string.my_as_the_referee);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LoginActivity.REQUEST_CODE_LOGIN:
                    if (UserManger.get().isLogin()) {
                        mPresenter.judgeRefereeStatus();
                    }
                    break;
                case MyFragment.REQUEST_CODE_MY_MESSAGE:
                    setRedPointStatus();
                    break;
                default:
                    break;
            }
        }
    }
}

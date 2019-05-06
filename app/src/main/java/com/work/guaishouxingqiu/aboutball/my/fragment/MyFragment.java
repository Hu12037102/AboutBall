package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

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
public class MyFragment extends DelayedFragment<MyPresenter> implements MyContract.View {
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
    private View view;

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
            Toasts.with().showToast(R.string.pleases_next_open);
            //ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_SHARE_FRIEND);
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
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_PRIZE);
        });
        mItemBall.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {

            }
        });
        mItemOrder.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                Toasts.with().showToast(R.string.pleases_next_open);
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
                Toasts.with().showToast(R.string.pleases_next_open);
            }
        });
        mItemAsReferee.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_APPLY_REFEREE);
            }
        });
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

    private void initLoginView() {
        if (mLlHeadGroup.getChildCount() > 0) {
            mLlHeadGroup.removeAllViews();
        }
        View view;
        UserBean userBean = UserManger.get().getUser();
        if (UserManger.get().isLogin()) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_login_my_head_view, null);
            TextView mTvName = view.findViewById(R.id.tv_name);
            TextView mTvFocusFans = view.findViewById(R.id.tv_focus_fans);
            mTvName.setText(DataUtils.isEmpty(userBean.nickName) ? userBean.phone : userBean.nickName);
            mTvFocusFans.setText(getString(R.string.focus_and_fans, "0", "0"));
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_no_login_my_head_view, null);

        }
        GlideManger.get().loadHeadImage(mContext, userBean.headerImg, mCivMyHead);
        mLlHeadGroup.addView(view);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initLoginView();
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this);
    }


    @OnClick({R.id.item_about_ball, R.id.item_order, R.id.item_team, R.id.item_message,
            R.id.item_dynamic, R.id.item_as_referee, R.id.rl_my_head})
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
}

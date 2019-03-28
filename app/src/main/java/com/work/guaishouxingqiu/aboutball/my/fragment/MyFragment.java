package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

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
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.civ_my_head)
    CircleImageView mCivMyHead;
    @BindView(R.id.ll_head_group)
    LinearLayout mLlHeadGroup;
    private View view;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
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
        if (UserManger.get().isLogin()) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_login_my_head_view, null);
            TextView mTvName = view.findViewById(R.id.tv_name);
            TextView mTvFocusFans = view.findViewById(R.id.tv_focus_fans);
           UserBean userBean =  UserManger.get().getUser();
            mTvName.setText(DataUtils.isEmpty(userBean.nickName) ? userBean.phone : userBean.nickName);
            mTvFocusFans.setText(getString(R.string.focus_and_fans, "0", "0"));
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_no_login_my_head_view, null);

        }
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
            R.id.item_dynamic, R.id.item_as_referee, R.id.item_setting, R.id.rl_my_head})
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
            case R.id.item_setting:
                break;
            case R.id.rl_my_head:
                clickHead();
                break;
            default:
                break;
        }
    }

    private void clickHead() {
        if (!UserManger.get().isLogin()) {
            $startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);

        } else {
            $startActivity(ARouterConfig.Path.ACTIVITY_MY_DETAILS);
          //  $startActivity(ARouterConfig.Path.ACTIVITY_GAME_VIDEO);
        }
    }
}

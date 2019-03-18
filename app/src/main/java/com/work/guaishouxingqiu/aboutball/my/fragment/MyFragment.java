package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.contract.MyContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
        View view;
        if (UserManger.get().isLogin()) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_login_my_head_view, null);
            TextView mTvName = view.findViewById(R.id.tv_name);
            TextView mTvFocusFans = view.findViewById(R.id.tv_focus_fans);
            LogUtils.w("initData--",UserManger.get().getUserName());
            if (DataUtils.isEmpty(UserManger.get().getUserName())) {
                mTvName.setText(UserManger.get().getPhone());
                mTvFocusFans.setText(getString(R.string.focus_and_fans, "0", "0"));
            }
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_no_login_my_head_view, null);

        }
        mLlHeadGroup.addView(view);
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
        }
    }
}

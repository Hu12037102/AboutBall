package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AlterNameContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.AlterNamePresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:13
 * 更新时间: 2019/3/20 11:13
 * 描述:修改用户密码
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ALTER_NAME)
public class AlterNameActivity extends BaseActivity<AlterNamePresenter> implements AlterNameContract.View {
    @BindView(R.id.tiet_name)
    AppCompatEditText mTietName;
    @BindView(R.id.title_view)
    TitleView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_name;
    }

    @Override
    protected void initView() {
        String nickName = UserManger.get().getNickName();
        if (!DataUtils.isEmpty(nickName)) {
            mTietName.setText(nickName);
            mTietName.setSelection(nickName.length());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mTitleView.setOnSureViewClickListener(view -> {
            clickUpdateName();
        });
    }

    private void clickUpdateName() {
        String nameContent = DataUtils.checkData(mTietName.getText()).toString();
        if (DataUtils.isEmpty(nameContent)) {
            Toasts.with().showToast(R.string.user_name_not_null);
        } else {
            RequestUpdateNameBean bean = new RequestUpdateNameBean();
            bean.nickName = nameContent;
            mPresenter.alterName(bean);
        }
    }

    @Override
    protected AlterNamePresenter createPresenter() {
        return new AlterNamePresenter(this);
    }


    @Override
    public void resultAlterName() {
        UserManger.get().putNickName(DataUtils.checkData(mTietName.getText()).toString());
        finish();
    }
}

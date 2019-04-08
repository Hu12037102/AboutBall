package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.EditMyAddressContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.EditMyAddressPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.AddressDialog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 13:59
 * 更新时间: 2019/4/8 13:59
 * 描述:编辑我的收获地址
 */
@Route(path = ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS)
public class EditMyAddressActivity extends BaseActivity<EditMyAddressPresenter> implements EditMyAddressContract.View {
    @BindView(R.id.acet_consignee)
    AppCompatEditText mAcetConsignee;
    @BindView(R.id.acet_phone)
    AppCompatEditText mAcetPhone;
    @BindView(R.id.item_address)
    ItemView mItemAddress;
    @BindView(R.id.acet_address)
    AppCompatEditText mAcetAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_my_address;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mItemAddress.mTvRight.setHint(R.string.please_selector_address);
        mAcetPhone.setText(UserManger.get().getPhone());

    }

    @Override
    protected void initEvent() {
        mItemAddress.setOnItemClickListener(view -> {
            AddressDialog addressDialog = new AddressDialog(EditMyAddressActivity.this);
            addressDialog.show();
            addressDialog.setOnResultAddressListener(address -> mItemAddress.mTvRight.setText(address));
        });
    }

    @Override
    protected EditMyAddressPresenter createPresenter() {
        return new EditMyAddressPresenter(this);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
    }

}

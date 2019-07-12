package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestEditAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestNewAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyAddress;
import com.work.guaishouxingqiu.aboutball.my.contract.EditMyAddressContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.EditMyAddressPresenter;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.AddressDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import butterknife.BindView;
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
    private int mAddressId = -1;

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
        mPresenter.getMyAddress();
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

    public boolean isInputAddressPeople() {
        if (DataUtils.isEmpty(DataUtils.checkData(mAcetConsignee.getText()))) {
            Toasts.with().showToast(R.string.please_consignee_name);
            return false;
        }
        return true;
    }

    public boolean isInputPhoneNumber() {
        if (DataUtils.isEmpty(DataUtils.checkData(mAcetPhone.getText()))) {
            Toasts.with().showToast(R.string.please_input_phone_number);
            return false;
        } else if (!DataUtils.isPhoneNumber(DataUtils.checkData(mAcetPhone.getText()).toString().trim())) {
            Toasts.with().showToast(R.string.please_sure_phone_number);
            return false;
        }
        return true;
    }

    public boolean isInputAddress() {
        if (DataUtils.isEmpty(mItemAddress.mTvRight.getText())) {
            Toasts.with().showToast(R.string.please_selector_address);
            return false;
        }
        return true;
    }

    public boolean isInputAddressDetails() {
        if (DataUtils.isEmpty(DataUtils.checkData(mAcetAddress.getText()))) {
            Toasts.with().showToast(R.string.please_input_address_data);
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        if (isInputAddressPeople() && isInputPhoneNumber()
                && isInputAddress() && isInputAddressDetails()) {

            if (mAddressId != -1) {
                RequestEditAddressBean bean = new RequestEditAddressBean();
                bean.userName = DataUtils.getEditDetails(mAcetConsignee);
                bean.telephone = DataUtils.getEditDetails(mAcetPhone);
                bean.address = mItemAddress.mTvRight.getText().toString();
                bean.detailAddress = DataUtils.getEditDetails(mAcetAddress);
                bean.addressId = mAddressId;
                mPresenter.saveEditAddress(bean);
            } else {
                RequestNewAddressBean bean = new RequestNewAddressBean();
                bean.userName = DataUtils.getEditDetails(mAcetConsignee);
                bean.telephone = DataUtils.getEditDetails(mAcetPhone);
                bean.address = mItemAddress.mTvRight.getText().toString();
                bean.detailAddress = DataUtils.getEditDetails(mAcetAddress);
                mPresenter.saveNewsAddress(bean);
            }

        }
    }


    @Override
    public void resultSaveAddress() {
        finish();
    }

    @Override
    public void resultMyAddress(ResultMyAddress address) {
        mAcetConsignee.setText(address.userName);
        mAcetConsignee.setSelection(address.userName == null ? 0 : address.userName.length());
        mAcetPhone.setText(address.telephone);
        mItemAddress.mTvRight.setText(address.address);
        mAcetAddress.setText(address.detailAddress);
        mAddressId = address.addressId;
    }
}

package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestEditAddressBean;
import com.ifeell.app.aboutball.my.bean.RequestNewAddressBean;
import com.ifeell.app.aboutball.my.bean.ResultMyAddress;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 13:59
 * 更新时间: 2019/4/8 13:59
 * 描述:编辑我的收获地址契约
 */
public interface EditMyAddressContract {
    interface View extends IBaseView {
        void resultSaveAddress();

        void resultMyAddress(ResultMyAddress address);
    }

    interface Presenter extends IBasePresenter {
        void saveNewsAddress(RequestNewAddressBean bean);

        void getMyAddress();

        void saveEditAddress(RequestEditAddressBean bean);
    }


}

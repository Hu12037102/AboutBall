package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeightBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateWeightBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:20
 * 更新时间: 2019/3/20 9:20
 * 描述:我的详情契约
 */
public interface MyDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultUpdateSex();

        void resultUpdateWeight();

        void resultUpdateHeight();

        void resultUpdateBirthday();

        void resultUpdateHeadPhoto(String photoUrl);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void updateSex(int sexType);

        void updateWeight(RequestUpdateWeightBean bean);

        void updateHeight(RequestUpdateHeightBean mUpdateHeightBean);

        void updateBirthday(RequestUpdateBirthdayBean bean);

        void updateHeadPhoto(RequestUpdateHeadPhotoBean bean);
    }
}

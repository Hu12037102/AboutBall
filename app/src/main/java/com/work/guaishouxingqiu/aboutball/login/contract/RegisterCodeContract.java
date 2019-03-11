package com.work.guaishouxingqiu.aboutball.login.contract;

import com.work.guaishouxingqiu.aboutball.login.bean.RegisterInputEditBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:52
 * 更新时间: 2019/3/11 13:52
 * 描述:注册输入验证码契约
 */
public interface RegisterCodeContract {
    interface View extends MessageContract.View {
        void resultEditData(List<RegisterInputEditBean> data);
    }

    interface Presenter extends MessageContract.Presenter {
    }
}

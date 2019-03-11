package com.work.guaishouxingqiu.aboutball.login.model;

import com.work.guaishouxingqiu.aboutball.login.bean.RegisterInputEditBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:54
 * 更新时间: 2019/3/11 13:54
 * 描述: 注册输入验证码契约
 */
public class RegisterCodeModel extends MessageModel {
    public interface OnResultEditData {
        void onResult(List<RegisterInputEditBean> data);
    }

    public void start(RegisterCodeModel.OnResultEditData onResultEditData) {
        List<RegisterInputEditBean> data = new ArrayList<>();
        RegisterInputEditBean bean1 = new RegisterInputEditBean();
        bean1.isCheck = true;
        data.add(bean1);
        RegisterInputEditBean bean2 = new RegisterInputEditBean();
        data.add(bean2);
        RegisterInputEditBean bean3 = new RegisterInputEditBean();
        data.add(bean3);
        RegisterInputEditBean bean4 = new RegisterInputEditBean();
        data.add(bean4);
        if (onResultEditData != null) {
            onResultEditData.onResult(data);
        }
    }
}

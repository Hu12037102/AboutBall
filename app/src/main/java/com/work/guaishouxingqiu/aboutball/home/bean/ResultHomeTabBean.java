package com.work.guaishouxingqiu.aboutball.home.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/14 17:29
 * 更新时间: 2019/3/14 17:29
 * 描述: 首页tab
 */
public class ResultHomeTabBean {
    public ResultHomeTabBean(String labelName) {
        this.labelName = labelName;
    }

    public int labelId;
    public String labelName;
    public List<ResultHomeTabBean.LabBean> homeLabelList;

    public static class LabBean {
        public int labelId;
        public String labelName;
    }
}

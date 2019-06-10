package com.work.guaishouxingqiu.aboutball.home.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/14 17:29
 * 更新时间: 2019/3/14 17:29
 * 描述: 首页tab
 */
public class ResultHomeTabBean {
    public ResultHomeTabBean(String parentLabelName) {
        this.parentLabelName = parentLabelName;
    }

    public int parentLabelId;
    public String parentLabelName;
    public List<ResultHomeTabBean.LabBean> homeLabelList;

    public static class LabBean {
        public int labelId;
        public String labelName;
    }
}

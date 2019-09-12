package com.work.guaishouxingqiu.aboutball.order.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:55
 * 更新时间: 2019/9/12 14:55
 * 描述:订单详情返回结果bean
 */
public class ResultOrderDetailsBean {
    public long id;
    public String title;
    public String subTitle;
    public double latitude;
    public double longitude;
    public double amount;
    public List<ModuleBean> moduleList;
    public String locationName;

    public static class ModuleBean {
        public String label;
        public List<ResultOrderDetailsBean.ModuleChildBean> paramValue;
    }

    public static class ModuleChildBean {
        public String name;
        public String value;
    }

}

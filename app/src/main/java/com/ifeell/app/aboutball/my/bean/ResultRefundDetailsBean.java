package com.ifeell.app.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:11
 * 更新时间: 2019/5/24 10:11
 * 描述:退款进度查询结果bean
 */
public class ResultRefundDetailsBean {
    public String orderNo;
    public String refundReason;
    public String telephone;
    public String orderTime;
    public String payTime;
    public float totalPrice;
    public float realPrice;
    public List<ResultRefundDetailsBean.RefundDetailList> orderRefundDetailList;

    public static class RefundDetailList {
        public String time;
        public String description;
    }
}

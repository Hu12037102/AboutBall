package com.work.guaishouxingqiu.aboutball.venue.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 16:22
 * 更新时间: 2019/4/22 16:22
 * 描述:
 */
public class ResultOrderDetailsBean {
    public long orderId;
    public String stadiumName;
    public String orderTime;
    public int stateId;
    public String stateName;
    public String address;
    public String orderNo;
    public String phoneNum;
    public String createOrderTime;
    public String payTime;
    public float totalPrice;
    public float realPrice;
    private List<OrderPeopleCountBean> orderDetailForOrders;

    public static class OrderPeopleCountBean {
        public String areaName;
        public String calendar;
        public float price;
    }

}

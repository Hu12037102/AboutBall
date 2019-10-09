package com.ifeell.app.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 13:10
 * 更新时间: 2019/5/14 13:10
 * 描述:我的訂單bean
 */
public class ResultMyOrderBean {
    public long orderId;
    public int orderType;
    public String stadiumName;
    public String photoUrl;
    public String address;
    public String latitude;
    public String longitude;
    public String orderTime;
    public int stateId;
    public String stateName;
    public List<DetailsOrder> orderDetailForOrders;
    public double totalPrice;
    public String phoneNum;
    public static class DetailsOrder {
        public String areaName;
        public String calendar;
        public double price;
    }
}

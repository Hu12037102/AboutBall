package com.work.guaishouxingqiu.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 13:53
 * 更新时间: 2019/9/11 13:53
 * 描述: 用户确认规格订单
 */
public class ResultConfirmOrderBean {
    public long spuId;
    public String params;
    public String title;
    public String price;
    public double unitPrice;
    public int num;
    public String phoneNumber;
    public List<Param> paramList;
    public static class Param{
        public String name;
        public String value;
    }
}

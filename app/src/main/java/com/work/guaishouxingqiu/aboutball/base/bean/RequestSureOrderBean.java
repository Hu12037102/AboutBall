package com.work.guaishouxingqiu.aboutball.base.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/10 11:40
 * 更新时间: 2019/9/10 11:40
 * 描述:
 */
public class RequestSureOrderBean {
    public RequestSureOrderBean(){}
    public RequestSureOrderBean(long spuId){
        this.spuId = spuId;
    }
    public long spuId;
    public String params;
    public int num = 1;
}

package com.work.guaishouxingqiu.aboutball.venue.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/18 18:05
 * 更新时间: 2019/4/18 18:05
 * 描述:包场返回bean
 */
public class ResultVenueBookBean {
    public long calendarId;
    public int areaId;
    public String startTime;
    public String endTime;
    public float price;
    public int stateId;//0不占用，1占用
    public int teamId;
    public boolean isCheck;
}

package com.work.guaishouxingqiu.aboutball.venue.bean;

import java.util.List;

import retrofit2.http.PUT;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 9:33
 * 更新时间: 2019/4/17 9:33
 * 描述:场馆详情bean
 */
public class ResultVenueDetailsBean {
    public long stadiumId;
    public String stadiumName;
    public String photoUrl;
    public String grade;
    public String address;
    public String telephone;
    public String service;
    public List<ResultVenueDetailsBean.AreaForDetailsList> areaForDetailList;
    public List<ResultAboutBallBean> offerListForStadium;

    /**
     * 场馆类别（7人场、9人场...）
     */
    public static class AreaForDetailsList {
        public long areaId;
        public String areaName;
        public String introduce;
        public String supporting;
        public List<ResultVenueDetailsBean.CalendarListForAreaList> calendarListForAreaList;
        public List<ResultVenueDetailsBean.OrderCommentForAreaList> orderCommentForAreaList;
    }

    /**
     * 日期
     */
    public static class CalendarListForAreaList {
        public String date;
        public String remainingTimes;
    }

    /**
     * 评论
     */
    public static class OrderCommentForAreaList {
        public String nickName;
        public String headerImg;
        public String commentTime;
        public String grade;
        public String commentContent;
    }

}
package com.ifeell.app.aboutball.venue.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 9:33
 * 更新时间: 2019/4/17 9:33
 * 描述:场馆详情bean
 */
public class ResultVenueDetailsBean implements Serializable {
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
    public static class AreaForDetailsList implements Serializable {
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
    public static class CalendarListForAreaList implements Serializable {
        public String date;
        public String remainingTimes;
    }


    public static class OrderCommentForAreaList implements Serializable {
        public int totalCount;
        public List<OrderCommentForAreaSimpleList> orderCommentForAreaSimpleList;
    }
    /**
     * 评论
     */
    public static class OrderCommentForAreaSimpleList implements Serializable {
        public String nickName;
        public String headerImg;
        public String commentTime;
        public String grade;
        public String commentContent;
        public String commentPic;
    }

}

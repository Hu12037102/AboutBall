package com.work.guaishouxingqiu.aboutball.home.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 13:34
 * 更新时间: 2019/3/13 13:34
 * 描述: 首页-推荐-返回数据
 */
public class ResultRecommendDataBean {
    public List<Banner> banner;
    public List<Match> match;
    public List<Stadium>stadium;
    public List<AgreeBallMatch> agreeBallMatch;

    public static class  Banner{
        public long newsId;
        public String title;
        public String coverUrl;
        public String releaseTime;
        public String onTop;
        public String commentCount;
    }
    public static class Match{
        public String matchName;
        public String gameName;
        public long matchId;
        public  long typeId;
        public String hostName;
        public String hostLogoUrl;
        public long hostScore;
        public String guestName;
        public String guestLogoUrl;
        public long guestScore;
        public String matchState;
        public long stateId;
        public String startTime;
        public String endTime;
        public String liveAddress;
        public String livePhoto;
        public String liveType;
    }
    public static class Stadium{
        public long stadiumId;
        public String stadiumName;
        public String distance;
    }
    public static class AgreeBallMatch{
        public String offerId;
        public String stadiumName;
        public String startTime;
        public String endTime;
    }
}

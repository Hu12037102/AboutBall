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
        public int matchId;
        public  int  typeId;
        public String hostName;
        public String hostLogoUrl;
        public int hostScore;
        public String guestName;
        public String guestLogoUrl;
        public int guestScore;
        public String matchState;
        public int stateId;
        public String startTime;
        public String endTime;
        public String liveAddress;
        public String livePhoto;
        public String liveType;
    }
    public static class Stadium{
        public int stadiumId;
        public String stadiumName;
        public String distance;
        public String photoUrl;
    }
    public static class AgreeBallMatch{
        public long agreeId;
        public String stadiumName;
        public String startTime;
        public String endTime;
        public String areaName;
        public String hostTeamLogo;
        public String hostTeamName;
        public int hasOpponent;
        public int hasReferee;
    }
}

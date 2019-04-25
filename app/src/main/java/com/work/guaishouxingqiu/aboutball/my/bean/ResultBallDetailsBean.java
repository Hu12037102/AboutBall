package com.work.guaishouxingqiu.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 17:42
 * 更新时间: 2019/4/25 17:42
 * 描述:球队详情bean
 */
public class ResultBallDetailsBean {
    public long teamId;
    public String teamLogo;
    public String teamName;
    public String teamType;
    public String playerCount;
    public String shirtColor;
    public List<MatchBean> matchForRecnetList;

    public static class MatchBean {
        public long matchId;
        public String startTime;
        public String endTime;
        public String hostTeamLogo;
        public String hostTeamName;
        public int hostScore;
        public String guestTeamLogo;
        public String guestTeamName;
        public int guestScore;
    }
}

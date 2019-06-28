package com.work.guaishouxingqiu.aboutball.game.bean;

import java.util.List;

import io.reactivex.internal.operators.parallel.ParallelFromPublisher;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 16:07
 * 更新时间: 2019/6/27 16:07
 * 描述: 比赛-赛程-bean
 */
public class ResultGameScheduleBean {
    public String date;
    public String previousDate;
    public String nextDate;
    public List<MatchList> matchList;

    public static class MatchList {
        public String gameName;
        public List<MatchScheduleForMatchList> matchScheduleForMatchList;
    }

    public static class MatchScheduleForMatchList {
        public long matchId;
        public String startTime;
        public String hostTeamLogo;
        public String hostTeamName;
        public String guestTeamLogo;
        public String guestTeamName;
        public int hostScore;
        public int guestScore;
        public String state;
        public int stateId;
    }
}

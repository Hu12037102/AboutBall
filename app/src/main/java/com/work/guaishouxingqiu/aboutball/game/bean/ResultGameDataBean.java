package com.work.guaishouxingqiu.aboutball.game.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 9:40
 * 更新时间: 2019/3/26 9:40
 * 描述:比赛赛况bean
 */
public class ResultGameDataBean {

    public int hostScore;
    public int guestScore;
    public List<Bean> matchBroadcastList;

    public static class Bean {
        public long broadcastId;
        public long matchId;
        public long userId;
        public String content;
        public String nickName;
        public String releaseTime;
    }
}

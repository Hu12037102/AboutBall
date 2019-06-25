package com.work.guaishouxingqiu.aboutball.game.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 11:41
 * 更新时间: 2019/6/24 11:41
 * 描述: 比赛详情数据赛况
 */
public class ResultGameDataResultBean {
    public ResultGameDataResultBean(){}
    public ResultGameDataResultBean(boolean isStart){
        this.isStart = isStart;
    }
    public String ids;
    public int time;
    public int teamType;
    public List<MatchSituation> matchSituationList;
    public boolean isStart;
    public static class MatchSituation{
        public long id;
        public int actionId;//1、进球；2、助攻；3、上场；4、下场；5、黄牌；6、红牌
        public String actionName;
        public long playerId;
        public String playerName;
    }
}

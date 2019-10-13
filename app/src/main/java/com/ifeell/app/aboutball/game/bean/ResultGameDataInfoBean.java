package com.ifeell.app.aboutball.game.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/24 13:55
 * 更新时间: 2019/6/24 13:55
 * 描述:比赛-数据-球队统计
 */
public class ResultGameDataInfoBean {
    public ResultGameDataInfoBean.Bean host;
    public ResultGameDataInfoBean.Bean guest;
    public static class Bean{
        public long id;
        public long matchId;
        public int teamType;
        public long teamId;
        public String teamName;
        public int  goal;
        public int ballPossession;
        public int passingCompletion;
        public int shoot;
        public int shootOnTarget;
        public int attack;
        public int dangerAttack;
        public int freeKick;
        public int cornerKick;
        public int offside;
        public int foul;
        public int yellowCard;
        public int redCard;

    }
}

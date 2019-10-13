package com.ifeell.app.aboutball.my.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/3 18:12
 * 更新时间: 2019/7/3 18:12
 * 描述:我的球队的赛况记录
 */
public class ResultMyGameRecordBean {
    public ResultMyGameRecordBean(boolean isStart){
        this.isStart = isStart;
    }
    public  boolean isStart;
    public int duration;
    public String action;
    public String nickName;
    public int isHostTeam;//0不是，1是
}

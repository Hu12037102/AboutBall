package com.work.guaishouxingqiu.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/10 10:26
 * 更新时间: 2019/6/10 10:26
 * 描述:赛况记录列表bean
 */
public class ResultMatchRefereeResultBean {
    public String teamAndAction;
    public List<ChildBean> agreeOutsForSimpleList;

    public static class ChildBean {
        public String headerImg;
        public String playerName;
        public int number;
        public int duration;
    }
}

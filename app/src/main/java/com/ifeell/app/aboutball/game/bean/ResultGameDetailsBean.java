package com.ifeell.app.aboutball.game.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 16:49
 * 更新时间: 2019/3/26 16:49
 * 描述:比赛数据Bean
 */
public class ResultGameDetailsBean {
    public List<ResultGameDetailsBean.Bean> matchData;

    public static class Bean {
        public String statsName;
        public float hostValue;
        public float guestValue;
        public int dataType = 1;// 0是小数，1是正整数
    }
}

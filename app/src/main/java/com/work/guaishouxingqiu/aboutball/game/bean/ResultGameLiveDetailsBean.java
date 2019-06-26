package com.work.guaishouxingqiu.aboutball.game.bean;

import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/26 16:05
 * 更新时间: 2019/6/26 16:05
 * 描述:赛事回顾bean
 */
public class ResultGameLiveDetailsBean {
    public List<MatchVideoBean> matchVideoList;
    public List<ResultNewsBean> newsList;
    public static class MatchVideoBean{
        public long videoId;
        public long matchId;
        public String description;
        public String photoUrl;
        public String videoUrl;
        public String ordinal;
    }

}

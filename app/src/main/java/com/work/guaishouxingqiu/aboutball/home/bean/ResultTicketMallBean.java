package com.work.guaishouxingqiu.aboutball.home.bean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 15:58
 * 更新时间: 2019/9/3 15:58
 * 描述:门票列表bean
 */
public class ResultTicketMallBean {
    public int contentType; //"内容类型: 1.比赛门票，2.泳票",
    public String guestLogoUrl;
    public String guestTeamName;
    public String hostLogoUrl;
    public String hostTeamName;
    public String image;
    public long matchId;
    public int saleStatus;//"商品状态：1购票,2已售完,3未开售",
    public long skuId;
    public String startTime;
    public String subTitle;
    public String title;
}

package com.work.guaishouxingqiu.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:09
 * 更新时间: 2019/4/11 17:09
 * 描述:
 */
public class ResultPrizeBean {
    public int hasAddress;
    public List<DataBean> prizeForSimples;

    public static class DataBean {
        public int recordId;
        public String prizeLevel;
        public String prizeName;
        public String prizeTime;
        public String expirationTime;
        public String source;
        public String getWay;
        public String prizeCode;
    }
}

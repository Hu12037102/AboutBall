package com.work.guaishouxingqiu.aboutball.venue.bean;

import java.util.List;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.bean
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评价bean
 *
 * @author ：
 */
public class ResultVenueEvaluateBean {
    public int totalCount;
    public int picCount;
    public int noPicCount;
    public List<ResultVenueEvaluateBean.EvaluateBean> orderCommentForAreaList;

    public static class EvaluateBean {
        public int totalCount;
        public List<ResultVenueEvaluateBean.ChildEvaluateBean> orderCommentForAreaSimpleList;

    }

    public static class ChildEvaluateBean {
        public String nickName;
        public String headerImg;
        public String commentTime;
        public float grade;
        public String commentPic;
        public String commentContent;
    }

}

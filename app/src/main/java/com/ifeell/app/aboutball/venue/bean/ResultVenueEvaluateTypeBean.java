package com.ifeell.app.aboutball.venue.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.bean
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}场馆评论类型bean
 *
 * @author ：
 */
public class ResultVenueEvaluateTypeBean {
    public int flag;
    public String content;
    public boolean isCheck;
    public int count;

    public static List<ResultVenueEvaluateTypeBean> getDefaultData() {
        List<ResultVenueEvaluateTypeBean> data = new ArrayList<>();
        ResultVenueEvaluateTypeBean allBean = new ResultVenueEvaluateTypeBean();
        allBean.content = "全部";
        allBean.flag = 0;
        allBean.isCheck = true;
        data.add(allBean);
        ResultVenueEvaluateTypeBean imageBean = new ResultVenueEvaluateTypeBean();
        imageBean.content = "有图";
        imageBean.flag = 1;
        data.add(imageBean);
        ResultVenueEvaluateTypeBean noImageBeanBean = new ResultVenueEvaluateTypeBean();
        noImageBeanBean.content = "无图";
        noImageBeanBean.flag = 2;
        data.add(noImageBeanBean);
        return data;
    }
}

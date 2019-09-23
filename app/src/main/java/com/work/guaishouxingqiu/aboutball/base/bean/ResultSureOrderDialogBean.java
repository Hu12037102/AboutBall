package com.work.guaishouxingqiu.aboutball.base.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/10 9:46
 * 更新时间: 2019/9/10 9:46
 * 描述: 确认订单dialogBean
 */
public class ResultSureOrderDialogBean {
    public String price;
    public List<SpecificationBean> specificationVOList;
    public int num;
    public String orderParam;
    public int maxNum;


    public class SpecificationBean {
        public String title;
        public int isMultipleChoices; //0、单选；1、多选
        public List<SpecificationChildBean> specificationValueVOList;

    }

    public class SpecificationChildBean {
        public String name;
        public String value;
        public int isChecked; //0、非选中；1、选中
        public int isInvalid; //0、可以点击选中；1、不可以点击选中
        public int stock;
    }

}

package com.work.guaishouxingqiu.aboutball.my.bean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 15:05
 * 更新时间: 2019/4/8 15:05
 * 描述:省市区地址bean
 */
public class AddressBean {
    public String code;
    public String name;
    public List<City> cityList;

    public static class City {
        public String code;
        public String name;
        public List<Area> areaList;
    }

   public static class Area {
        public String code;
        public String name;

    }
}

package com.work.guaishouxingqiu.aboutball.util;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 数据相关的工具类
 */
public class DataUtils {
    public static <T> T checkData(T t) {
        if (t == null) {
            throw new NullPointerException("data not is null !");
        }
        return t;
    }
}

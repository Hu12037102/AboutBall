package com.work.guaishouxingqiu.aboutball.base;

import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 11:55
 * 更新时间: 2019/3/8 11:55
 * 描述:
 */
public class BaseBean<T>{
    public int code;
    public String title;
    public String message;
    public String responseTime;
    public T result;
}

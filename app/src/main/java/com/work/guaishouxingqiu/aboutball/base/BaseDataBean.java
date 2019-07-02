package com.work.guaishouxingqiu.aboutball.base;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 18:29
 * 更新时间: 2019/3/21 18:29
 * 描述:
 */
public class BaseDataBean<T> {
    public int code;
    public String title;
    public String source;
    public String releaseTime;
    public String message;
    public T content;
    public T result;
    public int contentType;//1、视频
}

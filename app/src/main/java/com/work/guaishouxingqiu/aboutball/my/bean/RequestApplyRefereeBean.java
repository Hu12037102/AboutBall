package com.work.guaishouxingqiu.aboutball.my.bean;

import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 15:28
 * 更新时间: 2019/5/6 15:28
 * 描述:申请成为裁判
 */
public class RequestApplyRefereeBean {
    public int certificateLevel;
    public String certificatePhoto;
    public String name;
    public String photo;

    public boolean isHasData() {
        return (certificateLevel >1 ||
                !DataUtils.isEmpty(certificatePhoto) ||
                !DataUtils.isEmpty(name) ||
                !DataUtils.isEmpty(photo));
    }
}

package com.work.guaishouxingqiu.aboutball.base;

import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.http.RetrofitManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 10:53
 * 更新时间: 2019/3/6 10:53
 * 描述:
 */
public class BaseModel {

    protected RetrofitManger mRetrofitManger;

    public BaseModel() {
        if (mRetrofitManger == null) {
          //  mRetrofitManger = RetrofitManger.getDefault();
        }
    }

}

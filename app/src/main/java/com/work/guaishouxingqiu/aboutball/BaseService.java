package com.work.guaishouxingqiu.aboutball;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:06
 * 更新时间: 2019/6/14 16:06
 * 描述:
 */
public interface BaseService {
    @POST(IApiService.COMMUNITY_DYNAMIC_COMMENT)
    Observable<BaseBean<BaseDataBean<String>>> postDynamicComments(@Body RequestDynamicCommentsBean bean);
}
